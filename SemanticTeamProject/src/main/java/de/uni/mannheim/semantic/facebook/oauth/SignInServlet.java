package de.uni.mannheim.semantic.facebook.oauth;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.uni.mannheim.semantic.util.PropertiesUtils;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = -7453606094644144082L;
	private Logger logger = LogManager.getLogger(SignInServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Call Sign In Servlet");
		logger.info("Load Properties and configure OAuthentification");
		Properties prop = PropertiesUtils.load("general.properties");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthAppId(prop.getProperty("oauth.appId"))
				.setOAuthAppSecret(prop.getProperty("oauth.appSecret"))
				.setOAuthPermissions(prop.getProperty("oauth.permissions"));
		
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();
		request.getSession().setAttribute("facebook", facebook);
		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "")
				.append("/callback");
		String fURL = facebook.getOAuthAuthorizationURL(callbackURL.toString());
		logger.info("Redirect to callback Servlet");
		response.sendRedirect(fURL);

	}
}
