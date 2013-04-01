package com.gpardy.model.messaging;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 5/25/11
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppMessage {
    private AppMessageType type;
    private String data;

    public AppMessage(AppMessageType type, String data) {
        this.type = type;
        this.data = data;
    }

    public AppMessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
