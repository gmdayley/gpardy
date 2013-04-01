package com.gpardy.web.gwt.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.gpardy.web.gwt.client.view.*;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientFactoryImpl implements ClientFactory{

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);

    private final StartView startView = new StartViewImpl();
    private final GameView gameView = new GameViewImpl();
    private final NewGameView newGameView = new NewGameViewImpl();
    private final LinkGoogleAccountView linkGoogleAccountView = new LinkGoogleAccountViewImpl();
    private final JoinGameView joinGameView = new JoinGameViewImpl();
    private final BuzzerView buzzerView = new BuzzerViewImpl();


    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public GameView getGameView() {
        return gameView;
    }

    @Override
    public NewGameView getNewGameView() {
        return newGameView;
    }

    @Override
    public StartView getStartView() {
        return startView;
    }

    @Override
    public LinkGoogleAccountView getLinkGoogleAccountView() {
        return linkGoogleAccountView;
    }

    @Override
    public JoinGameView getJoinGameView() {
        return joinGameView;
    }

    @Override
    public BuzzerView getBuzzerView() {
        return buzzerView;
    }
}
