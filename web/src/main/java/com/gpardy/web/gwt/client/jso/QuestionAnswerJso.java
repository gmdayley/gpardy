package com.gpardy.web.gwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionAnswerJso extends JavaScriptObject{
    protected QuestionAnswerJso(){}
    public final native String getQuestionText() /*-{ return this.questionText; }-*/;
    public final native String getAnswerText() /*-{ return this.answerText; }-*/;
    public final native int getValue() /*-{ return this.value; }-*/;

    public final native boolean disabled() /*-{ return this.disabled; }-*/;
    public final native void disable() /*-{ this.disabled = true; }-*/;
}
