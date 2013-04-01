package com.gpardy.web.gwt.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.gpardy.web.gwt.client.ClientFactory;
import com.gpardy.web.gwt.client.jso.SpreadsheetJso;
import com.gpardy.web.gwt.client.place.NewGamePlace;
import com.gpardy.web.gwt.client.view.LinkGoogleAccountView;
import com.gpardy.web.gwt.client.view.NewGameView;



/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/11/11
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewGameActivity extends AbstractActivity implements NewGameView.Presenter, LinkGoogleAccountView.Presenter {
    private ClientFactory clientFactory;

    public NewGameActivity(NewGamePlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final NewGameView newGameView = clientFactory.getNewGameView();
        final LinkGoogleAccountView linkGoogleAccountView = clientFactory.getLinkGoogleAccountView();

        newGameView.setPresenter(this);
        linkGoogleAccountView.setPresenter(this);

        containerWidget.setWidget(newGameView.asWidget());

        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "ajax/user/sslist");
        try {
            rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    switch(response.getStatusCode()) {
                        case 200:
                            JsArray<SpreadsheetJso> spreadsheets = JsonUtils.safeEval(response.getText());
                            newGameView.setSpreadsheets(spreadsheets);

                            break;
                        case 500:
                            //todo check error
                            containerWidget.setWidget(linkGoogleAccountView.asWidget());
                            break;
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert(exception.getMessage());
                    //todo - handle error
                }
            });
        }
        catch (RequestException e) {
             Window.alert(e.getMessage());
            //todo - handle error
        }

    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}