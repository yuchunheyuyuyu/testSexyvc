package com.qtin.sexyvc.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ls on 17/3/6.
 */
@Entity
public class LoginEntity {
    @Id
    private String u_token;
    private long u_expire_time;
    private String u_nickname;
    private String u_realname;

    private int u_gender;
    private String u_avatar;
    private int u_auth_state;
    private int u_auth_type;

    @Generated(hash = 1032756692)
    public LoginEntity(String u_token, long u_expire_time, String u_nickname,
            String u_realname, int u_gender, String u_avatar, int u_auth_state,
            int u_auth_type) {
        this.u_token = u_token;
        this.u_expire_time = u_expire_time;
        this.u_nickname = u_nickname;
        this.u_realname = u_realname;
        this.u_gender = u_gender;
        this.u_avatar = u_avatar;
        this.u_auth_state = u_auth_state;
        this.u_auth_type = u_auth_type;
    }

    @Generated(hash = 441342942)
    public LoginEntity() {
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

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getU_realname() {
        return u_realname;
    }

    public void setU_realname(String u_realname) {
        this.u_realname = u_realname;
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
}
/**
 "u_token": "e475f7cceee65754343dbcdebbab6546",
 "u_expire_time": 1489037584,
 "u_nickname": "用户昵称",
 "u_realname": "测试用户",
 "u_gender": 0,
 "u_avatar": "",
 "u_auth_state": null,
 "u_auth_type": null

 */