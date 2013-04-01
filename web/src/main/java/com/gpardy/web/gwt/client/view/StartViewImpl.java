package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gpardy.web.gwt.client.place.JoinGamePlace;
import com.gpardy.web.gwt.client.place.NewGamePlace;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartViewImpl extends Composite implements StartView {
    interface StartViewUiBinder extends UiBinder<HTMLPanel, StartViewImpl> {
    }

    private static StartViewUiBinder ourUiBinder = GWT.create(StartViewUiBinder.class);

    private Presenter presenter;

    @UiField
    Anchor joinGameAnchor;
    @UiField
    Anchor newGameAnchor;

    public StartViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("newGameAnchor")
    public void newGame(ClickEvent event) {
        presenter.goTo(new NewGamePlace());
    }

    @UiHandler("joinGameAnchor")
    public void joinGame(ClickEvent event) {
        presenter.goTo(new JoinGamePlace());
    }
}