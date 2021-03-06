package de.uni.mannheim.semantic.facebook.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import facebook4j.Facebook;
import facebook4j.FacebookException;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;
    private Logger logger = LogManager.getLogger(CallbackServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	logger.info("Load OAuthAccesToken and redirect ton /index.jsp");
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
        	
            facebook.getOAuthAccessToken(oauthCode);
        } catch (FacebookException e) {
        }
    
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
