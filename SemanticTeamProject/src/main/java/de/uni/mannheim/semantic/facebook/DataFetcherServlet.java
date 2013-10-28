package de.uni.mannheim.semantic.facebook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;

/**
 * Servlet implementation class DataFetcherServlet
 */
@WebServlet("/DataFetcherServlet")
public class DataFetcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataFetcherServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		System.out.println("Call DataFetcherServlet with method: " + method);
		if ("fetchFacebookPerson".equals(method)) {
			fetchFacebookData(request, response);
		}
	}

	private void fetchFacebookData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");
		FacebookParser fbParser = new FacebookParser(facebook);
//		request.getSession().setAttribute("fbPerson",
//				fbParser.parseFacebookPerson().toJsonString());
//		response.sendRedirect(request.getContextPath() + "/");
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json
		// object to the output stream
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following,
		// it will return your json object
		out.print(fbParser.parseFacebookPerson().toJsonString());
		out.flush();
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
