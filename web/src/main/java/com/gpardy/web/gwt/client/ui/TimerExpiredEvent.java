package com.gpardy.web.gwt.client.ui;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 5/19/11
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimerExpiredEvent extends GwtEvent<TimerExpiredHandler>{
    public static Type<TimerExpiredHandler> TYPE = new Type<TimerExpiredHandler>();

    @Override
    public Type<TimerExpiredHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(TimerExpiredHandler handler) {
        handler.onCompleted();
    }
}
