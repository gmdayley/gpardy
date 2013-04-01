package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 11/26/11
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class OptionsPanel extends Composite {
    public static final String OPTION_TIMER_DURATION = "GPARDY_OPTION_TIMER_DURATION";
    public static final String OPTION_PLAY_SOUNDS = "GPARDY_OPTION_PLAY_SOUNDS";
    public static final int OPTION_TIMER_DURATION_DEFAULT = 20000;
    public static final boolean OPTION_PLAY_SOUNDS_DEFAULT = true;

    interface OptionsPanelUiBinder extends UiBinder<HTMLPanel, OptionsPanel> {}

    private static OptionsPanelUiBinder ourUiBinder = GWT.create(OptionsPanelUiBinder.class);
    @UiField CheckBox playSounds;
    @UiField TextBox timerDuration;

    public OptionsPanel() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);

        init();

        playSounds.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                storeOptionLocally(OPTION_PLAY_SOUNDS, playSounds.getValue().toString());
            }
        });

        timerDuration.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
                storeOptionLocally(OPTION_TIMER_DURATION, timerDuration.getText());
            }
        });
    }

    private void init(){
        if(Storage.isLocalStorageSupported()){
            //Restore value from local storage if supported
            String td = Storage.getLocalStorageIfSupported().getItem(OPTION_TIMER_DURATION);
            String duration = (td != null && td.length() > 0) ? td : Integer.toString(OPTION_TIMER_DURATION_DEFAULT);
            timerDuration.setText(duration);


            //Restore value from local storage if supported
            String ps = Storage.getLocalStorageIfSupported().getItem(OPTION_PLAY_SOUNDS);
            boolean sounds = (ps != null && ps.length() > 0) ? Boolean.parseBoolean(ps) : OPTION_PLAY_SOUNDS_DEFAULT;
            playSounds.setValue(sounds);
        }
        else{
            timerDuration.setText(Integer.toString(OPTION_TIMER_DURATION_DEFAULT));
            playSounds.setValue(OPTION_PLAY_SOUNDS_DEFAULT);
        }
    }

    private void storeOptionLocally(String key, String data) {
        if(Storage.isLocalStorageSupported()){
            Storage.getLocalStorageIfSupported().setItem(key, data);
        }
    }

    public int getTimerDuration(){
        //todo: handle exception
        return Integer.parseInt(timerDuration.getText());
    }

    public boolean playSounds(){
        return playSounds.getValue();
    }

    public void setTimerDuration(int millis){
        this.timerDuration.setText(Integer.toString(millis));
    }

    public void setPlaySounds(boolean playSounds){
        this.playSounds.setValue(playSounds);
    }
}