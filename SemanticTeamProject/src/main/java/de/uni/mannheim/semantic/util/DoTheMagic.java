package de.uni.mannheim.semantic.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni.mannheim.semantic.GUIObjectContainer;
import de.uni.mannheim.semantic.facebook.FacebookParser;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
import de.uni.mannheim.semantic.model.CelPerson;
import de.uni.mannheim.semantic.model.FacebookPerson;
import de.uni.mannheim.semantic.model.Interest;
import facebook4j.Facebook;

public class DoTheMagic extends HttpServlet {
	private static final long serialVersionUID = 6305643034487441839L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");
		FacebookParser fbParser = new FacebookParser(facebook);
		fbParser.parseFacebookPerson();
		CelPerson cp;
		if (request.getParameter("celname") != null) {
			System.out.println("CELNAME" + request.getParameter("celname"));
			cp = CelebritiesFetcher.get().createCel(
					request.getParameter("celname").toString());
			for (Interest i : cp.getInterest()) {
				System.out.println(i.getName());
				for (String s : i.getGenre()) {
					System.out.println("___" + s);
				}
			}
		} else {
			cp = null;
		}

		FacebookPerson fbp = fbParser.parseFacebookPerson();
		for (Interest i : fbp.getInterest()) {
			System.out.println(i.getName());
			for (String s : i.getGenre()) {
				System.out.println("___" + s);
			}
		}
		GUIObjectContainer c = new GUIObjectContainer(fbp, cp,
				CelebritiesFetcher.get().getDummyCelebrities());

		request.getSession().setAttribute("c", c);
		response.sendRedirect(request.getContextPath() + "/");
	}
}
