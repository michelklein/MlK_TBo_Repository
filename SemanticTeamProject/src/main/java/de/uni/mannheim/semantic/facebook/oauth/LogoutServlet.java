package de.uni.mannheim.semantic.facebook.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 5357658337449255998L;
    private Logger logger = LogManager.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	logger.info("Logout current Session");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+ "/");
    }
}
