package com.qtin.sexyvc.ui.request;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/6.
 */

public class InvestorRequest {

    private String token;
    private int page;
    private int page_size;
    private ArrayList<Long> domains;
    private ArrayList<Long> stages;
    private String keyword;
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public ArrayList<Long> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<Long> domains) {
        this.domains = domains;
    }

    public ArrayList<Long> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Long> stages) {
        this.stages = stages;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
