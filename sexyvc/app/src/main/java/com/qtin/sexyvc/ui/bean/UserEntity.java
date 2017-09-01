package com.qtin.sexyvc.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
/**
 * Created by ls on 17/6/13.
 */
@Entity
public class UserEntity {
    @Id
    private String u_token;
    private long u_expire_time;
    private int bind_mobile;

    private String u_nickname;
    private int u_gender;
    private String u_avatar;
    private String u_signature;

    private String u_company;
    private String u_title;
    private String u_phone;
    private String u_email;

    private String u_backup_phone;
    private String u_backup_email;
    private int u_auth_state;
    private int u_auth_type;

    private int has_project;
    private String business_card;

    private int has_comment;
    private int has_roadshow;


    @Generated(hash = 349288142)
    public UserEntity(String u_token, long u_expire_time, int bind_mobile, String u_nickname,
            int u_gender, String u_avatar, String u_signature, String u_company, String u_title,
            String u_phone, String u_email, String u_backup_phone, String u_backup_email,
            int u_auth_state, int u_auth_type, int has_project, String business_card, int has_comment,
            int has_roadshow) {
        this.u_token = u_token;
        this.u_expire_time = u_expire_time;
        this.bind_mobile = bind_mobile;
        this.u_nickname = u_nickname;
        this.u_gender = u_gender;
        this.u_avatar = u_avatar;
        this.u_signature = u_signature;
        this.u_company = u_company;
        this.u_title = u_title;
        this.u_phone = u_phone;
        this.u_email = u_email;
        this.u_backup_phone = u_backup_phone;
        this.u_backup_email = u_backup_email;
        this.u_auth_state = u_auth_state;
        this.u_auth_type = u_auth_type;
        this.has_project = has_project;
        this.business_card = business_card;
        this.has_comment = has_comment;
        this.has_roadshow = has_roadshow;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }


    public String getU_token() {
        return u_token;
    }

    public void setU_token(String u_token) {
        this.u_token = u_token;
    }

    public long getU_expire_time() {
        return u_expire_time;
    }

    public void setU_expire_time(long u_expire_time) {
        this.u_expire_time = u_expire_time;
    }

    public int getBind_mobile() {
        return bind_mobile;
    }

    public void setBind_mobile(int bind_mobile) {
        this.bind_mobile = bind_mobile;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public int getU_gender() {
        return u_gender;
    }

    public void setU_gender(int u_gender) {
        this.u_gender = u_gender;
    }

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getU_signature() {
        return u_signature;
    }

    public void setU_signature(String u_signature) {
        this.u_signature = u_signature;
    }

    public String getU_company() {
        return u_company;
    }

    public void setU_company(String u_company) {
        this.u_company = u_company;
    }

    public String getU_title() {
        return u_title;
    }

    public void setU_title(String u_title) {
        this.u_title = u_title;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_backup_phone() {
        return u_backup_phone;
    }

    public void setU_backup_phone(String u_backup_phone) {
        this.u_backup_phone = u_backup_phone;
    }

    public String getU_backup_email() {
        return u_backup_email;
    }

    public void setU_backup_email(String u_backup_email) {
        this.u_backup_email = u_backup_email;
    }

    public int getU_auth_state() {
        return u_auth_state;
    }

    public void setU_auth_state(int u_auth_state) {
        this.u_auth_state = u_auth_state;
    }

    public int getU_auth_type() {
        return u_auth_type;
    }

    public void setU_auth_type(int u_auth_type) {
        this.u_auth_type = u_auth_type;
    }

    public int getHas_project() {
        return has_project;
    }

    public void setHas_project(int has_project) {
        this.has_project = has_project;
    }

    public String getBusiness_card() {
        return business_card;
    }

    public void setBusiness_card(String business_card) {
        this.business_card = business_card;
    }

    public int getHas_comment() {
        return has_comment;
    }

    public void setHas_comment(int has_comment) {
        this.has_comment = has_comment;
    }

    public int getHas_roadshow() {
        return has_roadshow;
    }

    public void setHas_roadshow(int has_roadshow) {
        this.has_roadshow = has_roadshow;
    }
}
/**
 "u_nickname": "凌晨四点的科比和我",
 "u_gender": 1,
 "u_avatar": "http://oli02rut3.bkt.clouddn.com/ff1121b8f2406e1327c177e7255a80cd1500122279.png",
 "u_signature": "虎扑7月5日讯 据著名NBA记者Adrian Wojnarowski报道，据联盟消息人士透露，勇士已经与自由球员欧米-卡斯比达成了一年口头协议。",

 "u_company": "滴滴约炮",
 "u_title": "创始人",
 "u_phone": "15605538903",
 "u_email": "1521223584@qq.com",

 "u_backup_phone": "15605538906",
 "u_backup_email": "156088773256@163.com",
 "u_auth_state": 2,
 "u_auth_type": 1,

 "has_project": 1,
 "business_card": "6b810c5c6144045ee763eda53ca29ce81499590647508.png",
 "u_token": "2ce88f7a9bcd083bc0c927251b11d579"
 */