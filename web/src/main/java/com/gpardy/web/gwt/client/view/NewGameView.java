package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.gpardy.web.gwt.client.jso.SpreadsheetJso;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/11/11
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NewGameView extends IsWidget {
    void setPresenter(Presenter presenter);
    void setSpreadsheets(JsArray<SpreadsheetJso> spreadsheets);

    public interface Presenter {
        void goTo(Place place);
    }
}
