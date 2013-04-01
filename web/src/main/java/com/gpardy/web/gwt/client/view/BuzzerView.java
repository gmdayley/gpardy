package com.gpardy.web.gwt.client.view;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BuzzerView extends IsWidget{
    void setPresenter(Presenter presenter);

    public interface Presenter {
        void buzzIn();
    }
}
