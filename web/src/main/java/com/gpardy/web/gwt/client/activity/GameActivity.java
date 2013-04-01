package com.gpardy.web.gwt.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.SocketError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.place.shared.Place;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;
import com.gpardy.web.gwt.client.ClientFactory;
import com.gpardy.web.gwt.client.User;
import com.gpardy.web.gwt.client.jso.GameJso;
import com.gpardy.web.gwt.client.place.GamePlace;
import com.gpardy.web.gwt.client.view.GameView;
import com.gpardy.web.gwt.client.User;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends AbstractActivity implements GameView.Presenter {
    Logger logger = Logger.getLogger("GameActivity");

    private ClientFactory clientFactory;
    private String gameName;
    private String gameSpreadsheetKey;
    private String channelId;

    public GameActivity(GamePlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.gameName = place.getName();
        this.gameSpreadsheetKey = place.getKey();
    }

    @Override
    public String mayStop() {
        return "Are you sure want to quit this game?";
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final GameView gameView = clientFactory.getGameView();
        gameView.setPresenter(this);
        gameView.clearBoard();

        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "ajax/game/" + gameName + "/" + gameSpreadsheetKey);
        try {
            requestBuilder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    final GameJso gameJso = JsonUtils.safeEval(response.getText());
                    //Storage.getLocalStorageIfSupported().setItem(gameName, response.getText());

                    RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "channel/token/" + gameJso.getCode());
                    try{
                        rb.sendRequest(null, new RequestCallback() {
                            @Override
                            public void onResponseReceived(Request request, Response response) {
                                GameActivity.this.channelId = response.getText();
                                openChannel(GameActivity.this.channelId, gameView);
                                gameView.setGameData(gameJso, new JSONObject());
                            }

                            @Override
                            public void onError(Request request, Throwable exception) {
                                //To change body of implemented methods use File | Settings | File Templates.
                            }
                        });
                    }
                    catch (RequestException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    logger.log(Level.SEVERE, "Could not get game from spreadsheet");
                }
            });
        }
        catch (RequestException e) {
            logger.log(Level.SEVERE, "Could not get game from spreadsheet");
        }

        containerWidget.setWidget(gameView.asWidget());
    }

    private void openChannel(String token, final GameView gameView) {
        ChannelFactory.createChannel(token, new ChannelFactory.ChannelCreatedCallback() {
            @Override
            public void onChannelCreated(Channel channel) {
                channel.open(new SocketListener() {
                    @Override
                    public void onOpen() {
                        logger.log(Level.FINE, "Channel open:" + channelId);
                    }

                    @Override
                    public void onMessage(String s) {
                        //Buzz in
                        final User user = new User(s.substring(1));
                        if (s.startsWith("b")) {
                            gameView.showBuzzIn(user);
                        }
                        else if(s.startsWith("j")){
                            gameView.joinGame(user);
                        }
                        else if(s.startsWith("s")){
                            String[] values = s.substring(s.indexOf("s")+1).trim().split(":");
                            gameView.showQuestion(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                        }
                        else if(s.startsWith("t")){
//                            gameView.startTimer();
                        }

                    }

                    @Override
                    public void onError(SocketError socketError) {
//                        logger.log(Level.WARNING, "Channel error", socketError);
                        logger.log(Level.SEVERE, "Channel error: " + socketError.getDescription());
                    }

                    @Override
                    public void onClose() {
                        logger.fine("Channel closed");
                        //todo: prompt to reconnect?
                    }
                });
            }
        });

    }

    public void reconnect(){
        openChannel(channelId, clientFactory.getGameView());
    }

   @Override
    public void resetGame() {
        Storage.getLocalStorageIfSupported().removeItem(gameName);
        Storage.getLocalStorageIfSupported().removeItem(gameName + "-scores");
        goTo(new GamePlace(gameName, gameSpreadsheetKey));
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
