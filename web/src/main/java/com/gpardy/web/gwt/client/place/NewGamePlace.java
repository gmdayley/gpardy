package com.gpardy.web.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/11/11
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewGamePlace extends Place {

    public NewGamePlace() {}


    @Prefix("newgame")
    public static class Tokenizer implements PlaceTokenizer<NewGamePlace> {
        @Override
        public String getToken(NewGamePlace place) {
            return "";
        }

        @Override
        public NewGamePlace getPlace(String token) {
            return new NewGamePlace();
        }
    }
}
