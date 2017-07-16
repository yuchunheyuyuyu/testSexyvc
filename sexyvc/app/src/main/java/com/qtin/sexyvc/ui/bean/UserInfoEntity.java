package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ls on 17/6/15.
 */
@Entity
public class UserInfoEntity implements Parcelable {
    @Id
    private String  token;

    private int has_project;

    private String business_card;
    private String u_nickname;
    private int u_gender;
    private String u_avatar;
    private String u_signature;

    private String u_phone;
    private String u_email;
    private String u_backup_phone;
    private String u_backup_email;

    private String u_company;
    private String u_title;

    private int u_auth_state;
    private int u_auth_type;//0未填写，1投资人，2创始人，3FA

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.has_project);
        dest.writeString(this.business_card);
        dest.writeString(this.u_nickname);
        dest.writeInt(this.u_gender);
        dest.writeString(this.u_avatar);
        dest.writeString(this.u_signature);
        dest.writeString(this.u_phone);
        dest.writeString(this.u_email);
        dest.writeString(this.u_backup_phone);
        dest.writeString(this.u_backup_email);
        dest.writeString(this.u_company);
        dest.writeString(this.u_title);
        dest.writeInt(this.u_auth_state);
        dest.writeInt(this.u_auth_type);
    }

    public UserInfoEntity() {
    }

    protected UserInfoEntity(Parcel in) {
        this.token = in.readString();
        this.has_project = in.readInt();
        this.business_card = in.readString();
        this.u_nickname = in.readString();
        this.u_gender = in.readInt();
        this.u_avatar = in.readString();
        this.u_signature = in.readString();
        this.u_phone = in.readString();
        this.u_email = in.readString();
        this.u_backup_phone = in.readString();
        this.u_backup_email = in.readString();
        this.u_company = in.readString();
        this.u_title = in.readString();
        this.u_auth_state = in.readInt();
        this.u_auth_type = in.readInt();
    }

    @Generated(hash = 1654280294)
    public UserInfoEntity(String token, int has_project, String business_card, String u_nickname, int u_gender, String u_avatar, String u_signature,
            String u_phone, String u_email, String u_backup_phone, String u_backup_email, String u_company, String u_title, int u_auth_state,
            int u_auth_type) {
        this.token = token;
        this.has_project = has_project;
        this.business_card = business_card;
        this.u_nickname = u_nickname;
        this.u_gender = u_gender;
        this.u_avatar = u_avatar;
        this.u_signature = u_signature;
        this.u_phone = u_phone;
        this.u_email = u_email;
        this.u_backup_phone = u_backup_phone;
        this.u_backup_email = u_backup_email;
        this.u_company = u_company;
        this.u_title = u_title;
        this.u_auth_state = u_auth_state;
        this.u_auth_type = u_auth_type;
    }

    public static final Parcelable.Creator<UserInfoEntity> CREATOR = new Parcelable.Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel source) {
            return new UserInfoEntity(source);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };
}
/**
 "u_nickname": "王昱斌昵称",
 "u_gender": "",
 "u_avatar": "https://wx.qlogo.cn/mmopen/H7TrngfktapWXKOz5R2QOAkKw0qKR7DicpBmiaiaGNJuicxRyE8zsv6ynOEibKHOYSOgGh4vPhXJb6hYemobhdLibQMt6zI2yeUZLf/0",
 "u_signature": "",
 "u_company": "",
 "u_title": "",
 "u_phone": "15221014211",
 "u_email": "test@qq.com",
 "u_backup_phone": "",
 "u_backup_email": "",
 "u_auth_state": "",
 "u_auth_type": ""

 */