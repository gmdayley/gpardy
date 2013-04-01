package com.gpardy.model;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserSpreadsheet {
    private String title;
    private String key;

    public UserSpreadsheet(String title, String key) {
        this.title = title;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
