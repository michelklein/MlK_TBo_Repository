package de.uni.mannheim.semantic.facebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        System.out.println(oauthCode);
//        facebook.setOAuthAccessToken(facebook.getOAuthAccessToken());
//        facebook.setOAuthAppId("575994579138307", "9d00e50135caad5b3809debac5f4622e");
        try {
        	
            facebook.getOAuthAccessToken(oauthCode);
        } catch (FacebookException e) {
            throw new ServletException(e);
        }
        
        Worker w = new Worker(facebook);
        
        response.sendRedirect(request.getContextPath() + "/");
    }
}
