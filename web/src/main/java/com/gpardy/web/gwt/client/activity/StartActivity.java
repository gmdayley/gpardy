package com.gpardy.web.gwt.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.gpardy.web.gwt.client.ClientFactory;
import com.gpardy.web.gwt.client.place.StartPlace;
import com.gpardy.web.gwt.client.view.StartView;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartActivity  extends AbstractActivity implements StartView.Presenter {
    private ClientFactory clientFactory;

    public StartActivity(StartPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final StartView startView = clientFactory.getStartView();
        startView.setPresenter(this);

        containerWidget.setWidget(startView.asWidget());
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
