package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/12.
 */
public class FundFollowRequest {

    private String token;
    private ArrayList<Long> group_ids;
    private ArrayList<Long> object_ids;
    private int object_type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Long> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(ArrayList<Long> group_ids) {
        this.group_ids = group_ids;
    }

    public ArrayList<Long> getObject_ids() {
        return object_ids;
    }

    public void setObject_ids(ArrayList<Long> object_ids) {
        this.object_ids = object_ids;
    }

    public int getObject_type() {
        return object_type;
    }

    public void setObject_type(int object_type) {
        this.object_type = object_type;
    }
}
