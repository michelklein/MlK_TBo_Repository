package de.uni.mannheim.semantic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni.mannheim.semantic.comparison.AgeComparator;
import de.uni.mannheim.semantic.comparison.InterestsComparator;
import de.uni.mannheim.semantic.comparison.LocationsComparator;
import de.uni.mannheim.semantic.facebook.FacebookParser;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
import de.uni.mannheim.semantic.model.CompareResult;
import de.uni.mannheim.semantic.model.Interest;
import de.uni.mannheim.semantic.model.MatchingContainer;
import de.uni.mannheim.semantic.model.Person;
import facebook4j.Facebook;

/**
 * Servlet implementation class DataFetcherServlet
 */
@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AgeComparator ageComparator = new AgeComparator();
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
		System.out.println("Call DataFetcherServlet with method: " + method);
		String json = null;
		if ("facebook".equals(method)) {
			Facebook facebook = (Facebook) request.getSession().getAttribute(
					"facebook");
			FacebookParser fbParser = new FacebookParser(facebook);
			Person fbPerson = fbParser.parseFacebookPerson();
			json = fbPerson.toJsonString();
			for (Interest i : fbPerson.getInterest()) {
				System.out.println(i.getName());
				for (String s : i.getGenre()) {
					System.out.println("___" + s);
				}
			}

		} else if ("celebrity".equals(method)) {
			String celebrityName = request.getParameter("name");
			Person celebrity = CelebritiesFetcher.get()
					.createCel(celebrityName);
			if (celebrity != null) {
				for (Interest i : celebrity.getInterest()) {
					System.out.println(i.getName());
					for (String s : i.getGenre()) {
						System.out.println("___" + s);
					}
				}

				Facebook facebook = (Facebook) request.getSession()
						.getAttribute("facebook");
				FacebookParser fbParser = new FacebookParser(facebook);
				Person fbPerson = fbParser.parseFacebookPerson();
				CompareResult ageResult = ageComparator.compare(
						fbPerson.getBirthdate(), celebrity.getBirthdate());

				List<CompareResult> locationResults = locationsComparator
						.compare(fbPerson.getLocations(),
								celebrity.getLocations());

				List<CompareResult> movieR = movieComparator.compare(
						fbPerson.getInterest(), celebrity.getInterest());

				MatchingContainer comp = new MatchingContainer(celebrity,
						ageResult, locationResults, movieR);
				json = comp.toJsonString();
			}
		} else if ("celebrityList".equals(method)) {
			json = CelebritiesFetcher.get().getDummyCelebritiesAsJson();
		}

		if (json != null) {
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
