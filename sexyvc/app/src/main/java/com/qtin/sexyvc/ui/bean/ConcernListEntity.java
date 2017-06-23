package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/20.
 */

public class ConcernListEntity {

    private long contact_id;
    private String investor_avatar;
    private String investor_name;
    private String fund_name;

    private long investor_id;
    private long fund_id;
    private String title;
    private String intro;

    private String phone;
    private String email;
    private String wechat;
    private String backup_phone;
    private String backup_email;

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

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

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getBackup_phone() {
        return backup_phone;
    }

    public void setBackup_phone(String backup_phone) {
        this.backup_phone = backup_phone;
    }

    public String getBackup_email() {
        return backup_email;
    }

    public void setBackup_email(String backup_email) {
        this.backup_email = backup_email;
    }

    private int viewType;//是不是上面搜索历史那一栏

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     *  "contact_id": 5,
     "investor_avatar": "",
     "investor_name": "",
     "fund_name": "",

     "investor_id": 0,
     "fund_id": 0,
     "title": "",
     "intro": "",

     "phone": "",
     "email": "",
     "wechat": "",
     "backup_phone": "",
     "backup_email": ""
     *
     */
}
