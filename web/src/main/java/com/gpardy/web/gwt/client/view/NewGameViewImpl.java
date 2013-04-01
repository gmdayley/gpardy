package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gpardy.web.gwt.client.jso.SpreadsheetJso;
import com.gpardy.web.gwt.client.place.GamePlace;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/11/11
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewGameViewImpl extends Composite implements NewGameView{
    interface NewGameViewImplUiBinder extends UiBinder<HTMLPanel, NewGameViewImpl> {}
    private static NewGameViewImplUiBinder ourUiBinder = GWT.create(NewGameViewImplUiBinder.class);

    private Presenter presenter;
    @UiField FlowPanel spreadsheetPanel;


    public NewGameViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @Override
    public void setSpreadsheets(final JsArray<SpreadsheetJso> spreadsheets) {
        spreadsheetPanel.clear();

        for(int i = 0; i < spreadsheets.length(); i++){
            final SpreadsheetJso spreadsheetJso = spreadsheets.get(i);
            Anchor a = new Anchor(spreadsheetJso.getTitle());
            a.addStyleName("menubutton");

            a.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    NewGameViewImpl.this.presenter.goTo(new GamePlace("NA", spreadsheetJso.getKey()));
                }
            });

            spreadsheetPanel.add(a);
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}