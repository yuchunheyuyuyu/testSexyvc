package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/4.
 */
public class EditTypeRequest {
    private String token;
    private ArrayList<Long> type_ids;
    private String type_key;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Long> getType_ids() {
        return type_ids;
    }

    public void setType_ids(ArrayList<Long> type_ids) {
        this.type_ids = type_ids;
    }

    public String getType_key() {
        return type_key;
    }

    public void setType_key(String type_key) {
        this.type_key = type_key;
    }
}
