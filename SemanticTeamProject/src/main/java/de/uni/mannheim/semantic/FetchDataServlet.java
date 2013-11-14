package de.uni.mannheim.semantic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.comparison.DatesComparison;
import de.uni.mannheim.semantic.comparison.InterestsComparator;
import de.uni.mannheim.semantic.comparison.LocationsComparator;
import de.uni.mannheim.semantic.facebook.FacebookParser;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.InterestCompareResult;
import de.uni.mannheim.semantic.model.Location;
import de.uni.mannheim.semantic.model.MatchingContainer;
import de.uni.mannheim.semantic.model.Person;
import facebook4j.Facebook;

/**
 * Servlet implementation class DataFetcherServlet
 */
@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LogManager.getLogger(FetchDataServlet.class
			.getName());
	private FetchGeoData geoFetcher = new FetchGeoData();
	private DatesComparison datesComparator = new DatesComparison();
	private LocationsComparator locationsComparator = new LocationsComparator();
	private InterestsComparator movieComparator = new InterestsComparator();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("op");
		String compMethod = request.getParameter("comp");
		logger.info("Call DataFetcherServlet with method: " + method);
		String json = null;
		if ("facebook".equals(method)) {
			Facebook facebook = (Facebook) request.getSession().getAttribute(
					"facebook");
			FacebookParser fbParser = new FacebookParser(facebook);
			Person fbPerson = fbParser.parseFacebookPerson();
			String latitude = request.getParameter("lati");
			String longitude = request.getParameter("longi");
			if (latitude != null && longitude != null) {
				Location location = geoFetcher.getLocation("Browser",longitude, latitude,
						Location.BROWSER_LOCATION);
				logger.info("Found Browser location and add to Facebook User");
				if (location != null) {
					fbPerson.getLocations().add(location);
				}
			}

			request.getSession().setAttribute("facebookUser", fbPerson);
			json = fbPerson.toJsonString();
		} else if ("celebrity".equals(method)) {

			Person celebrity = ((Person) request.getSession().getAttribute(
					"cel"));

			logger.info("Found celebrity. Starting comparison");

			if (compMethod.equals("none")) {
				String celebrityName = request.getParameter("name");
				logger.info("Create Celebrity Person for name: "
						+ celebrityName);
				celebrity = CelebritiesFetcher.get().createCel(celebrityName);
				request.getSession().setAttribute("cel", celebrity);

				MatchingContainer comp = new MatchingContainer(celebrity, null,
						null, null);
				request.getSession().setAttribute("comp", comp);
			} else if (compMethod.equals("date")) {
				Person fbPerson = (Person) request.getSession().getAttribute(
						"facebookUser");
				List<CompareResult> ageResult = datesComparator.compare(fbPerson.getDates(), celebrity.getDates());
				logger.info("Get compare results for category date");
				((MatchingContainer) request.getSession().getAttribute("comp"))
						.setAgeCompResult(ageResult);
			} else if (compMethod.equals("loc")) {
				Person fbPerson = (Person) request.getSession().getAttribute(
						"facebookUser");
				List<CompareResult> locationResults = locationsComparator
						.compare(fbPerson.getLocations(),
								celebrity.getLocations());
				logger.info("Get compare results for category locations");

				((MatchingContainer) request.getSession().getAttribute("comp"))
						.setLocationResults(locationResults);
			} else if (compMethod.equals("genre")) {
				Person fbPerson = (Person) request.getSession().getAttribute(
						"facebookUser");
				List<InterestCompareResult> movieR = movieComparator.compare(
						fbPerson.getInterest(), celebrity.getInterest());
				logger.info("Get compare results for category movies");

				((MatchingContainer) request.getSession().getAttribute("comp"))
						.setMovieResult(movieR);
			}

			((MatchingContainer) request.getSession().getAttribute("comp"))
					.calcTotal();

			json = ((MatchingContainer) request.getSession().getAttribute(
					"comp")).toJsonString();

		} else if ("celebrityList".equals(method)) {
			logger.info("Start loading celebrity names");
			// json = CelebritiesFetcher.get().getDummyCelebritiesAsJson();
			json = CelebritiesFetcher.get().getArtistsAsJson();
			logger.info("End loading celebrity names");
		}

		if (json != null) {
			logger.info("return response as json string");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
