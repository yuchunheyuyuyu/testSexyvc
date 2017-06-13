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

    @Generated(hash = 1511994072)
    public UserEntity(String u_token, long u_expire_time) {
        this.u_token = u_token;
        this.u_expire_time = u_expire_time;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public int getBind_mobile() {
        return bind_mobile;
    }

    public void setBind_mobile(int bind_mobile) {
        this.bind_mobile = bind_mobile;
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
}
