package com.gpardy.web.gwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameJso extends JavaScriptObject{
    // Overlay types always have protected, zero-arg ctors
    protected GameJso() { }

    public final native String getId() /*-{ return this.id; }-*/; //todo is this being used?
    public final native String getName() /*-{ return this.gameName; }-*/;
    public final native RoundJso getRound(int round)/*-{ return this.rounds[round]; }-*/;
    public final native String getCode()/*-{ return this.code; }-*/;


    public final native void join(String name)/*-{
        this.scores[name] = 0;
    }-*/;

    public final native void updateScore(String name, int delta)/*-{
        this.scores[name] = this.scores[name] + delta;
    }-*/;

    public final native JsArray getScores()/*-{
        return this.scores;
    }-*/;
}
