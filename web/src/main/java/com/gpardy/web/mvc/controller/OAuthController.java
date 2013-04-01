package com.gpardy.web.mvc.controller;

import com.google.appengine.api.datastore.*;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 7/29/11
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class OAuthController {
    private static final String SECRET_TOKEN_COOKIE = "Secret_Token";
    public static final String OAUTH_COOKIE = "gdata-oauth";

    private String oauthConsumerKey;
    private String oauthConsumerSecret;

    public OAuthController() {
        //TODO Inject this...
        DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("OAuth", "1");
        try {
            Entity entity = dataStoreService.get(key);
            oauthConsumerKey = (String) entity.getProperty("consumerKey");
            oauthConsumerSecret = (String) entity.getProperty("consumerSecret");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping(value = "/oauth/r")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException, OAuthException, URISyntaxException {
        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
        oauthParameters.setOAuthConsumerKey(oauthConsumerKey);
        oauthParameters.setOAuthConsumerSecret(oauthConsumerSecret);
        oauthParameters.setScope("https://spreadsheets.google.com/feeds/");

        URI uri = new URI(request.getScheme(), null, request.getServerName(), request.getServerPort(), "/oauth/a", null, null);

        System.out.println("uri.toString() = " + uri.toString());
        System.out.println("request.getScheme() = " + request.getScheme());
        System.out.println("request.getServerName() = " + request.getServerName());
        System.out.println("request.getServerPort() = " + request.getServerPort());
        System.out.println("request.getContextPath() = " + request.getContextPath());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());

//        oauthParameters.setOAuthCallback("http://g-pardy.appspot.com/oauth/a");
//        oauthParameters.setOAuthCallback("http://localhost:8080/oauth/a");
        oauthParameters.setOAuthCallback(uri.toString());

        GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());

        //Get Request Token
        oauthHelper.getUnauthorizedRequestToken(oauthParameters);

        //Store for later use ... after the user has authorized our application
        Cookie aCookie = new Cookie(SECRET_TOKEN_COOKIE, oauthParameters.getOAuthTokenSecret());
        aCookie.setPath("/");
        response.addCookie(aCookie);

        //Redirect to authorization url
        String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
        response.sendRedirect(approvalPageUrl);
    }

    @RequestMapping(value = "/oauth/a")
    public void access(HttpServletRequest request, HttpServletResponse response) throws IOException, OAuthException {
        //TODO - what if user denied access?

        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
        oauthParameters.setOAuthConsumerKey(oauthConsumerKey);
        oauthParameters.setOAuthConsumerSecret(oauthConsumerSecret);

        //Retrieve saved secret_token
        Cookie[] allCookies = request.getCookies();
        Cookie aSecretToken = null;
        if (allCookies != null) {
            for (Cookie candidate : allCookies) {
                if (SECRET_TOKEN_COOKIE.equals(candidate.getName())) {
                    aSecretToken = candidate;
                }
            }
        }
        //TODO - what if the cookie wasnt found?

        oauthParameters.setOAuthTokenSecret(aSecretToken.getValue());

        GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
        oauthHelper.getOAuthParametersFromCallback(request.getQueryString(), oauthParameters);

        //Upgrade request token to access token
        String accessToken = oauthHelper.getAccessToken(oauthParameters);

        //Store for permanant use
        Cookie oauthCookie = new Cookie(OAUTH_COOKIE, accessToken + " " + oauthParameters.getOAuthTokenSecret());
        oauthCookie.setPath("/");
        response.addCookie(oauthCookie);

        response.sendRedirect("/game#newgame:");
    }



}
