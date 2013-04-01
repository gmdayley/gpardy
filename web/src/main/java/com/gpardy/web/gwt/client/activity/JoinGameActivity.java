package com.gpardy.web.gwt.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.gpardy.web.gwt.client.ClientFactory;
import com.gpardy.web.gwt.client.place.JoinGamePlace;
import com.gpardy.web.gwt.client.view.JoinGameView;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinGameActivity extends AbstractActivity implements JoinGameView.Presenter {
    private ClientFactory clientFactory;

    public JoinGameActivity(JoinGamePlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final JoinGameView view = clientFactory.getJoinGameView();
        view.setPresenter(this);

        containerWidget.setWidget(view.asWidget());
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
