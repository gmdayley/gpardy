package com.gpardy.web.gwt.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.gpardy.web.gwt.client.place.*;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
@WithTokenizers({StartPlace.Tokenizer.class,
        GamePlace.Tokenizer.class,
        NewGamePlace.Tokenizer.class,
        JoinGamePlace.Tokenizer.class,
        BuzzerPlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper{}