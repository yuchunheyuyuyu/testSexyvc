package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/13.
 */

public class ChangeFundGroupRequest {
    private String token;
    private int object_type;
    private long object_id;
    private ArrayList<Long> group_ids;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getObject_type() {
        return object_type;
    }

    public void setObject_type(int object_type) {
        this.object_type = object_type;
    }

    public long getObject_id() {
        return object_id;
    }

    public void setObject_id(long object_id) {
        this.object_id = object_id;
    }

    public ArrayList<Long> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(ArrayList<Long> group_ids) {
        this.group_ids = group_ids;
    }
}
