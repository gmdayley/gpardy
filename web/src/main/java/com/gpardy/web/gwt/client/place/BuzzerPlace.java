package com.gpardy.web.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuzzerPlace  extends Place {
    private String userName;
    private String gameCode;

    public BuzzerPlace(String userName, String gameCode) {
        this.userName = userName;
        this.gameCode = gameCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getGameCode() {
        return gameCode;
    }

    @Prefix("buzzer")
    public static class Tokenizer implements PlaceTokenizer<BuzzerPlace> {
        @Override
        public String getToken(BuzzerPlace place) {
            return place.getUserName() + ":" + place.getGameCode();
        }

        @Override
        public BuzzerPlace getPlace(String token) {
            String[] values = token.split(":");
            return new BuzzerPlace(values[0], values[1]);
        }
    }
}