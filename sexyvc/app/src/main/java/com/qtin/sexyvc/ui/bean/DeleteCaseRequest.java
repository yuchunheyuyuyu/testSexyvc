package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/4.
 */

public class DeleteCaseRequest {
    private String token;
    private ArrayList<Long> case_ids;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Long> getCase_ids() {
        return case_ids;
    }

    public void setCase_ids(ArrayList<Long> case_ids) {
        this.case_ids = case_ids;
    }
}
