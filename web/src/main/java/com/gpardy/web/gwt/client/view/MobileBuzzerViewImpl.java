package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 12/1/11
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileBuzzerViewImpl extends Composite implements BuzzerView{
    interface MobileBuzzerViewImplUiBinder extends UiBinder<HTMLPanel, MobileBuzzerViewImpl> {
    }

    private static MobileBuzzerViewImplUiBinder ourUiBinder = GWT.create(MobileBuzzerViewImplUiBinder.class);
    Presenter presenter;

    @UiField Image button;

    public MobileBuzzerViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }


    @UiHandler("button")
    public void buttonPressed(TouchStartEvent e){
        button.addStyleName("pressed");
        presenter.buzzIn();
    }

    @UiHandler("button")
    public void buttonReleased(TouchEndEvent e){
        button.removeStyleName("pressed");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}