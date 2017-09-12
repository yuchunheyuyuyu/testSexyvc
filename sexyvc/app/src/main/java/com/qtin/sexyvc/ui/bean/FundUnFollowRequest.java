package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/12.
 */
public class FundUnFollowRequest {

    private String token;
    private ArrayList<Long> group_ids;
    private ArrayList<Long> ids;
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
