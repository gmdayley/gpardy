package com.gpardy.web.gwt.client.view;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.gpardy.web.gwt.client.User;
import com.gpardy.web.gwt.client.jso.GameJso;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GameView extends IsWidget{
    void setPresenter(Presenter presenter);
    void clearBoard();
    void setGameData(GameJso board, JSONObject scores);
    void showQuestion(int categoryIndex, int levelIndex);
    void showBuzzIn(User user);
    void joinGame(User user);

    public interface Presenter {
        void resetGame();
        void reconnect();
        void goTo(Place place);
    }
}
