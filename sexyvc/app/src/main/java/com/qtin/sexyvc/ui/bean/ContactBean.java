package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.investor.bean.RoadShowBean;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/5.
 */

public class ContactBean {
    private long contact_id;
    private long fund_id;
    private long investor_id;
    private long u_id;

    private String investor_name;
    private String investor_avatar;
    private long investor_uid;
    private String fund_name;

    private String title;
    private String intro;
    private String comment;
    private String phone;

    private String email;
    private String wechat;
    private String backup_phone;
    private String backup_email;

    private String remark;
    private int status;
    private long create_time;

    private float score;
    private RoadShowBean road_show;

    private ArrayList<TagEntity> tags;

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public RoadShowBean getRoad_show() {
        return road_show;
    }

    public void setRoad_show(RoadShowBean road_show) {
        this.road_show = road_show;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }
}
/**
 "contact_id": 29,
 "fund_id": 38,
 "investor_id": 375,
 "u_id": 6,

 "investor_name": "谷承文",
 "investor_avatar": "image/2016/10/12/49eeb3cc50cbe39b98da7771bc71a7c4.jpg",
 "investor_uid": 0,
 "fund_name": "晨兴资本",

 "title": "投资经理",
 "intro": "",
 "comment": "",
 "phone": "",

 "email": "",
 "wechat": "",
 "backup_phone": "",
 "backup_email": "",

 "remark": "",
 "status": 1,
 "create_time": 1498556766

 */