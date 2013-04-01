package com.gpardy.web.gwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 5/26/11
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageJso extends JavaScriptObject{
    protected MessageJso(){}

    public final native String getType()/*-{
        return this.type;
    }-*/;

    public final native String getData()/*-{
        return this.data;
    }-*/;
}
