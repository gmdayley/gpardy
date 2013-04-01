package com.gpardy.web.gwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpreadsheetJso extends JavaScriptObject{
    protected SpreadsheetJso(){}

    public final native String getTitle()/*-{
        return this.title;
    }-*/;

    public final native String getKey()/*-{
        return this.key;
    }-*/;
}
