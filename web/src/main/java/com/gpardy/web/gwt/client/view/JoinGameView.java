package com.gpardy.web.gwt.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JoinGameView extends IsWidget {
    void setPresenter(Presenter presenter);

    public interface Presenter {
        void goTo(Place place);
    }
}
