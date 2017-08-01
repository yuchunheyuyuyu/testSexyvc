package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/31.
 */

public class UnFollowContactRequest {
    private String token;
    private ArrayList<Long> contact_ids;
    private long group_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Long> getContact_ids() {
        return contact_ids;
    }

    public void setContact_ids(ArrayList<Long> contact_ids) {
        this.contact_ids = contact_ids;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }
}
