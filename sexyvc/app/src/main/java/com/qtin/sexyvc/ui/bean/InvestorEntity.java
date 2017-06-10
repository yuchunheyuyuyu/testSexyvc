package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/9.
 */

public class InvestorEntity{

    private String investor_avatar;
    private String investor_name;
    private String investor_title;
    private float investor_recommendation_number;

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_title() {
        return investor_title;
    }

    public void setInvestor_title(String investor_title) {
        this.investor_title = investor_title;
    }

    public float getInvestor_recommendation_number() {
        return investor_recommendation_number;
    }

    public void setInvestor_recommendation_number(float investor_recommendation_number) {
        this.investor_recommendation_number = investor_recommendation_number;
    }
}
