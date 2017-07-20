package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/20.
 */

public class ChangeReadStatusRequest {
    private String token;
    private ArrayList<Long> ids;
    private int object_type;//消息类型，1：消息；2：通知

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Long> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }

    public int getObject_type() {
        return object_type;
    }

    public void setObject_type(int object_type) {
        this.object_type = object_type;
    }
}
