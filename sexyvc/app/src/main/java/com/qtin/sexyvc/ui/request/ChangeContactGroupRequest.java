package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/22.
 */

public class ChangeContactGroupRequest {
    private String token;
    private Long contact_id;
    private ArrayList<Long> group_ids;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public ArrayList<Long> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(ArrayList<Long> group_ids) {
        this.group_ids = group_ids;
    }
}
