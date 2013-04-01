package com.gpardy.web.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class GamePlace extends Place {
    private String name;
    private String key;

    public GamePlace(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    @Prefix("game")
    public static class Tokenizer implements PlaceTokenizer<GamePlace> {
        @Override
        public String getToken(GamePlace place) {
            return place.getName() + ":" + place.getKey();
        }

        @Override
        public GamePlace getPlace(String token) {
            String[] values = token.split(":");
            return new GamePlace(values[0], values[1]);
        }
    }
}
