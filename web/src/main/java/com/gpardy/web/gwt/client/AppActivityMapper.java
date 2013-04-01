package com.gpardy.web.gwt.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.gpardy.web.gwt.client.activity.*;
import com.gpardy.web.gwt.client.place.*;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if(place instanceof StartPlace){
            return new StartActivity((StartPlace) place, clientFactory);
        }
        else if (place instanceof GamePlace)   {
            return new GameActivity((GamePlace) place, clientFactory);
        }
        else if(place instanceof NewGamePlace){
            return new NewGameActivity((NewGamePlace) place, clientFactory);
        }
        else if(place instanceof JoinGamePlace){
            return new JoinGameActivity((JoinGamePlace) place, clientFactory);
        }
        else if(place instanceof BuzzerPlace){
            return new BuzzerActivity((BuzzerPlace) place, clientFactory);
        }
        return null;
    }
}
