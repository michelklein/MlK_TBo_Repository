package de.uni.mannheim.semantic.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uni.mannheim.semantic.facebook.FBParser;
import de.uni.mannheim.semantic.gui.GUIObjectContainer;
import de.uni.mannheim.semantic.jena.CelebritiesFetcher;
import de.uni.mannheim.semantic.model.CelPerson;
import facebook4j.Facebook;
import facebook4j.FacebookException;

public class DoTheMagic extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        FBParser fbParser = new FBParser(facebook);
        CelPerson cp;
        if(request.getParameter("celname")!=null){
        	System.out.println("CELNAME"+request.getParameter("celname"));
         cp=CelebritiesFetcher.get().getCelebrity(request.getParameter("celname").toString());
        }else{
         cp=null;
        }
		GUIObjectContainer c = new GUIObjectContainer(fbParser.getP(),cp,CelebritiesFetcher.get().getDummyCelebrities());
        request.getSession().setAttribute("c", c);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
