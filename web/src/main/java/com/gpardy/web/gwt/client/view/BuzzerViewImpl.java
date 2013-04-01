package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/2/11
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuzzerViewImpl extends Composite implements BuzzerView{
    interface BuzzerViewImplUiBinder extends UiBinder<HTMLPanel, BuzzerViewImpl> {
    }

    private static BuzzerViewImplUiBinder ourUiBinder = GWT.create(BuzzerViewImplUiBinder.class);
    Presenter presenter;

    @UiField
    Image button;

    public BuzzerViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);


    }

    @UiHandler("button")
    public void buttonMPressed(MouseDownEvent e){
        button.addStyleName("pressed");
        presenter.buzzIn();
    }

    @UiHandler("button")
    public void buttonMReleased(MouseUpEvent e){
        button.removeStyleName("pressed");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}