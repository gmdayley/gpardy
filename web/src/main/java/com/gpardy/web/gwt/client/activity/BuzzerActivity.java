package com.gpardy.web.gwt.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.gpardy.web.gwt.client.ClientFactory;
import com.gpardy.web.gwt.client.place.BuzzerPlace;
import com.gpardy.web.gwt.client.view.BuzzerView;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuzzerActivity extends AbstractActivity implements BuzzerView.Presenter{
    private ClientFactory clientFactory;
    private BuzzerPlace place;

    public BuzzerActivity(BuzzerPlace place, ClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final BuzzerView view = clientFactory.getBuzzerView();
        view.setPresenter(this);

        containerWidget.setWidget(view.asWidget());

        //Join Game
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "channel/join/" + place.getGameCode() + "/" + place.getUserName());
        try {
            rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    switch (response.getStatusCode()) {
                        case 200:
                            return;
                        case 409:
                            Window.alert("Someone else is already using that name.");
                            break;
                        default:
                            Window.alert("Received an unexpected response from the server.");
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Failed to communicate with server");
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    @Override
    public void buzzIn() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "channel/buzzin/" + place.getGameCode() + "/" + place.getUserName());
        try {
            rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    switch (response.getStatusCode()) {
                        case 200:
                            return;
                        case 409:
                            Window.alert("Someone else is already using that name.");
                            break;
                        default:
                            Window.alert("Received an unexpected response from the server.");
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Failed to communicate with server");
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}