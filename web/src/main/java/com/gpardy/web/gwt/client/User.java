package com.gpardy.web.gwt.client;

/**
* Created by IntelliJ IDEA.
* User: gmdayley
* Date: 12/1/11
* Time: 7:37 AM
* To change this template use File | Settings | File Templates.
*/
public class User {
    public final String name;
    public final String id;

    public User(String userId) {
        int idx = userId.indexOf(":");
        id = userId.substring(0, idx - 1);
        name = userId.substring(idx + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
