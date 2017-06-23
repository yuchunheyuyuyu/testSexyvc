package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;
/**
 * Created by ls on 17/6/23.
 */
public class FollowRequest {
    private String token;
    private ArrayList<Long> group_ids;
    private ArrayList<Long> investor_ids;

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

    public ArrayList<Long> getInvestor_ids() {
        return investor_ids;
    }

    public void setInvestor_ids(ArrayList<Long> investor_ids) {
        this.investor_ids = investor_ids;
    }
}
