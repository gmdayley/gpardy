package com.gpardy.web.gwt.client.ui;


import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 5/19/11
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionTimer extends Widget implements HasHandlers{

    private SimpleEventBus handlerManager;

    private Element innerDiv;
    private Element outerDiv;
    private TimerAnimation animation;

    public QuestionTimer() {
        outerDiv = DOM.createDiv();
        innerDiv = DOM.createDiv();

        outerDiv.setClassName("questionTimer");

        outerDiv.appendChild(innerDiv);
        setElement(outerDiv);

        handlerManager = new SimpleEventBus();
    }

    public void start(int duration){
        reset();

        // Add/Remove styles
        removeStyleName("expired");
        addStyleName("running");

        animation = new TimerAnimation();
        animation.run(duration);
    }

    public void stop(){
        // Remove style
        removeStyleName("running");
        if(animation != null){
            animation.cancel();
        }
    }


    public void reset(){
//        if(animation != null){
//            animation.cancel();
//        }

        DOM.setStyleAttribute(innerDiv, "width", "100%");
    }



    public HandlerRegistration addTimerExpiredHandler(TimerExpiredHandler handler){
        return handlerManager.addHandler(TimerExpiredEvent.TYPE, handler);
    }


    public class TimerAnimation extends Animation{
        @Override
        protected void onUpdate(double progress) {
            DOM.setStyleAttribute(innerDiv, "width", 100 - (100 * progress) + "%");
        }

        @Override
        protected void onCancel(){
            //Just ignore...
        }

        @Override
        protected void onComplete() {
            // Add/Remove styles
            removeStyleName("running");
            addStyleName("expired");

            // Fire event
            handlerManager.fireEvent(new TimerExpiredEvent());
        }


    }
}
