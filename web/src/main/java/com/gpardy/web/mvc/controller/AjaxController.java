package com.gpardy.web.mvc.controller;

import com.google.appengine.api.datastore.*;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import com.gpardy.model.UserSpreadsheet;
import com.gpardy.model.game.Category;
import com.gpardy.model.game.Game;
import com.gpardy.model.game.QuestionAnswer;
import com.gpardy.model.game.Round;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.jvm.hotspot.runtime.ia64.IA64Frame;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("/ajax")
@Controller
public class AjaxController {
    private String oauthConsumerSecret;
    private String oauthConsumerKey;

    public AjaxController() {
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

    @RequestMapping(value = "/user/sslist", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserSpreadsheet> getUserSpreadsheets(HttpServletRequest request, HttpServletResponse response) throws OAuthException {
        List<UserSpreadsheet> spreadsheetList = new ArrayList<UserSpreadsheet>();

        //Retrieve saved access token
        Cookie oauthToken = getOAuthToken(request);

        if (oauthToken != null) {
            String[] values = oauthToken.getValue().split(" ");

            GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
            oauthParameters.setOAuthConsumerKey(oauthConsumerKey);
            oauthParameters.setOAuthConsumerSecret(oauthConsumerSecret);
            oauthParameters.setOAuthToken(values[0]);
            oauthParameters.setOAuthTokenSecret(values[1]);

            try {
                SpreadsheetService service = new SpreadsheetService("g-pardy");
                service.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
                URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full?title=gpardy");
                SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
                List<SpreadsheetEntry> spreadsheets = feed.getEntries();

                for (SpreadsheetEntry entry : spreadsheets) {
                    spreadsheetList.add(new UserSpreadsheet(entry.getTitle().getPlainText(), entry.getKey()));
                }
            } catch (OAuthException e) {
                throw new OAuthException("application not authorized by user", e);
            } catch (ServiceException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (MalformedURLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else {
            throw new OAuthException("application not authorized by user");
        }

        return spreadsheetList;
    }


    @RequestMapping(value = "/game/{gameName}/{key}", method = RequestMethod.GET)
    public
    @ResponseBody
    Game getGame(HttpServletRequest request, @PathVariable String gameName, @PathVariable String key) throws IOException, ServiceException, OAuthException {
        Game game = null;
        Cookie oauthToken = getOAuthToken(request);

        if (oauthToken != null) {
            String[] values = oauthToken.getValue().split(" ");
            GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
            oauthParameters.setOAuthConsumerKey(oauthConsumerKey);
            oauthParameters.setOAuthConsumerSecret(oauthConsumerSecret);
            oauthParameters.setOAuthToken(values[0]);
            oauthParameters.setOAuthTokenSecret(values[1]);
            game = parseGameFromSpreadsheet(oauthParameters, gameName, key);

            String code = RandomStringUtils.random(5, "gpardy123456789");//randomAlphanumeric(6);
            game.setCode(code);
        }

        return game;
    }

    private Cookie getOAuthToken(HttpServletRequest request) {
        Cookie[] allCookies = request.getCookies();
        Cookie oauthToken = null;
        if (allCookies != null) {
            for (Cookie candidate : allCookies) {
                if (OAuthController.OAUTH_COOKIE.equals(candidate.getName())) {
                    oauthToken = candidate;
                }
            }
        }
        return oauthToken;
    }


    private Game parseGameFromSpreadsheet(GoogleOAuthParameters oAuthParameters, String gameName, String key) throws IOException, ServiceException, OAuthException {
        SpreadsheetService service = new SpreadsheetService("gpardy");
        service.setOAuthCredentials(oAuthParameters, new OAuthHmacSha1Signer());
        FeedURLFactory feedFactory = FeedURLFactory.getDefault();
        URL cellFeedUrl = feedFactory.getCellFeedUrl(key, "1", "private", "values");
        CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

        Game game = new Game(gameName);

        Round round1 = new Round("Round 1");

        List<Category> categories = new ArrayList<Category>();

        for (CellEntry cellEntry : cellFeed.getEntries()) {
            Cell cell = cellEntry.getCell();

            if (cell.getRow() == 1) {
                Category category = new Category(cell.getValue());
                categories.add(category);
            } else {
                String[] input = cell.getValue().trim().split("\\|");

                //TODO - add support for custom values for levels
                categories.get(cell.getCol() - 1).addQuestionAnswer(new QuestionAnswer(input[0], input[1], (cell.getRow() - 1) * 100));
            }
        }
        round1.setCategories(categories);
        game.addRound(round1);
        return game;
    }
}
