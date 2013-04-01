package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Application extends Composite {
    interface ApplicationUiBinder extends UiBinder<HTMLPanel, Application> {}
    private static ApplicationUiBinder ourUiBinder = GWT.create(ApplicationUiBinder.class);

    @UiField SimplePanel display;

    public Application() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        this.initWidget(rootElement);
    }

    public SimplePanel getDisplay() {
        return display;
    }
}