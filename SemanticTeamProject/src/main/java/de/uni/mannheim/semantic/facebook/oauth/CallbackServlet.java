package de.uni.mannheim.semantic.facebook.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni.mannheim.semantic.facebook.FBParser;
import de.uni.mannheim.semantic.gui.GUIObjectContainer;
import facebook4j.Facebook;
import facebook4j.FacebookException;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("callback");
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
        	
            facebook.getOAuthAccessToken(oauthCode);
        } catch (FacebookException e) {
            throw new ServletException(e);
        }
    
        FBParser fbParser = new FBParser(facebook);
        System.out.println(fbParser.getP().getFirstname());
        GUIObjectContainer c = new GUIObjectContainer(fbParser.getP());
        request.getSession().setAttribute("c", c);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
