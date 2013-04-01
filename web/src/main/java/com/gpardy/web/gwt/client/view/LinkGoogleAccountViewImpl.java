package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkGoogleAccountViewImpl extends Composite implements LinkGoogleAccountView{
    interface LinkGoogleAccountViewImplUiBinder extends UiBinder<HTMLPanel, LinkGoogleAccountViewImpl> {}

    private static LinkGoogleAccountViewImplUiBinder ourUiBinder = GWT.create(LinkGoogleAccountViewImplUiBinder.class);

    private Presenter presenter;

    public LinkGoogleAccountViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}