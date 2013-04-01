package com.gpardy.web.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinGamePlace extends Place{
    public JoinGamePlace() {}

    @Prefix("join")
    public static class Tokenizer implements PlaceTokenizer<JoinGamePlace> {
        @Override
        public String getToken(JoinGamePlace place) {
            return null;
        }

        @Override
        public JoinGamePlace getPlace(String token) {
            return new JoinGamePlace();
        }
    }
}
