package com.gpardy.web.gwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryJso extends JavaScriptObject{
    protected CategoryJso(){}
    public final native String getName() /*-{ return this.name; }-*/;
    public final native JsArray<QuestionAnswerJso> getQuestionAnswers()/*-{return this.questionAnswers}-*/;


}
