package com.gpardy.web.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartPlace extends Place{
    public StartPlace() {}

    @Prefix("start")
    public static class Tokenizer implements PlaceTokenizer<StartPlace> {
        @Override
        public String getToken(StartPlace place) {
            return "";
        }

        @Override
        public StartPlace getPlace(String token) {
            return new StartPlace();
        }
    }
}
