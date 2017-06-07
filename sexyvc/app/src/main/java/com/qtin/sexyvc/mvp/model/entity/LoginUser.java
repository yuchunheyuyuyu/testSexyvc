package com.qtin.sexyvc.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by ls on 17/4/11.
 */
@Entity
public class LoginUser {
    @Id
    private Date date;
    private String name;
    private String password;

    @Generated(hash = 980370474)
    public LoginUser(Date date, String name, String password) {
        this.date = date;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 1159929338)
    public LoginUser() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
