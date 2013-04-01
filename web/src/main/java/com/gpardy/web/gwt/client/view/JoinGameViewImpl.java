package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.gpardy.web.gwt.client.place.BuzzerPlace;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinGameViewImpl extends Composite implements JoinGameView{
    interface JoinGameViewImplUiBinder extends UiBinder<HTMLPanel, JoinGameViewImpl> {
    }

    private static JoinGameViewImplUiBinder ourUiBinder = GWT.create(JoinGameViewImplUiBinder.class);

    private Presenter presenter;
    @UiField
    TextBox userNameTextBox;
    @UiField
    TextBox gameCodeTextBox;
    @UiField
    Anchor joinAnchor;

    public JoinGameViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("gameCodeTextBox")
    public void clearCodeTextBox(ClickEvent event){
        gameCodeTextBox.setText("");
    }


    @UiHandler("userNameTextBox")
    public void clearUserNameTextBox(ClickEvent event){
        userNameTextBox.setText("");
    }

    @UiHandler("joinAnchor")
    public void joinGame(ClickEvent event){
        presenter.goTo(new BuzzerPlace(userNameTextBox.getText(), gameCodeTextBox.getText()));
    }
}