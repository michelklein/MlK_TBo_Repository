package facebook4j.examples.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = -7453606094644144082L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId("575994579138307")
          .setOAuthAppSecret("9d00e50135caad5b3809debac5f4622e")
//          .setOAuthAccessToken("**************************************************")
          .setOAuthPermissions("email,read_insights,read_stream,user_about_me,user_activities,user_birthday,user_checkins,user_education_history,user_events,user_groups,user_hometown,user_interests,user_likes,user_location,user_notes");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
//        facebook.getStatuses().get(0).getMessage()
        request.getSession().setAttribute("facebook", facebook);
        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        System.out.println(callbackURL);
        
		        String fURL=facebook.getOAuthAuthorizationURL(callbackURL.toString());
  System.out.println(fURL);
//		response.sendRedirect("https://www.facebook.com/dialog/oauth?client_id="+clientID+"&client_secret="+clientSecret+"&redirect_uri="+callbackURL);
//		response.sendRedirect(callbackURL.toString());
		        response.sendRedirect(fURL);
    
    }
}
