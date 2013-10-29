package de.uni.mannheim.semantic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni.mannheim.semantic.facebook.FacebookParser;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
import de.uni.mannheim.semantic.model.CelPerson;
import facebook4j.Facebook;

/**
 * Servlet implementation class DataFetcherServlet
 */
@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			json = fbParser.parseFacebookPerson().toJsonString();
		} else if ("celebrity".equals(method)) {
			String celebrityName = request.getParameter("name");
			CelPerson celebrity = CelebritiesFetcher.get().getCelebrity(
					celebrityName);
			if (celebrity != null)
				json = celebrity.toJsonString();
		} else if ("celebrityList".equals(method)) {
			json = CelebritiesFetcher.get().getDummyCelebritiesAsJson();
		}

		if (json != null) {
			response.setContentType("application/json");
			// Get the printwriter object from response to write the required
			// json
			// object to the output stream
			PrintWriter out = response.getWriter();
			// Assuming your json object is **jsonObject**, perform the
			// following,
			// it will return your json object
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
