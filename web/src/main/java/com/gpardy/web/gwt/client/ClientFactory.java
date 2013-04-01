package com.gpardy.web.gwt.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.gpardy.web.gwt.client.view.*;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    GameView getGameView();
    NewGameView getNewGameView();
    StartView getStartView();
    LinkGoogleAccountView getLinkGoogleAccountView();
    JoinGameView getJoinGameView();
    BuzzerView getBuzzerView();
}
