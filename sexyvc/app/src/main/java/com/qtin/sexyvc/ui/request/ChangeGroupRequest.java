package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/22.
 */

public class ChangeGroupRequest {
    private String token;
    private Long investor_id;
    private ArrayList<Long> group_ids;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(Long investor_id) {
        this.investor_id = investor_id;
    }

    public ArrayList<Long> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(ArrayList<Long> group_ids) {
        this.group_ids = group_ids;
    }
}
