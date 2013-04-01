package com.gpardy.web.gwt.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StartView extends IsWidget {
    void setPresenter(Presenter presenter);

    public interface Presenter {
        void goTo(Place place);
    }
}