package com.qtin.sexyvc.ui.request;

/**
 * Created by ls on 17/7/17.
 */

public class CreateInvestorRequest {
    private String token;
    private String investor_name;
    private String fund_name;
    private String title;

    private String investor_avatar;
    private String phone;
    private String email;

    private String remark;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
/**
 token 	用户Token 	是
 investor_name 	投资人名称 	是
 fund_name 	投资机构名称 	是
 title 	投资人职位 	是
 investor_avatar 	投资人头像 	否
 phone 	投资人电话 	否
 backup_phone 	投资人电话 	否
 email 	投资人邮箱 	否
 backup_email 	投资人邮箱 	否
 wechat 	投资人微信 	否
 intro 	投资人简介 	否
 remark 	投资人备注 	否

 */