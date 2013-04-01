package com.gpardy.web.gwt.client;

import com.gpardy.web.gwt.client.view.BuzzerView;
import com.gpardy.web.gwt.client.view.MobileBuzzerViewImpl;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 12/1/11
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientFactoryImplMobile extends ClientFactoryImpl {
    private final BuzzerView mobileBuzzerView = new MobileBuzzerViewImpl();

    @Override
    public BuzzerView getBuzzerView() {
        return mobileBuzzerView;
    }
}
