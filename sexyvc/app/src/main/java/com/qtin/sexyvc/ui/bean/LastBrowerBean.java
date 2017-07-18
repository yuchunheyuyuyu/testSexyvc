package com.qtin.sexyvc.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ls on 17/7/18.
 */
@Entity
public class LastBrowerBean {
    @Id
    private long investor_id;
    private String investor_avatar;
    private String investor_name;
    private String fund_name;
    private long investor_uid;
    private long localTime;
    private String title;

    @Generated(hash = 468109648)
    public LastBrowerBean(long investor_id, String investor_avatar,
            String investor_name, String fund_name, long investor_uid,
            long localTime, String title) {
        this.investor_id = investor_id;
        this.investor_avatar = investor_avatar;
        this.investor_name = investor_name;
        this.fund_name = fund_name;
        this.investor_uid = investor_uid;
        this.localTime = localTime;
        this.title = title;
    }

    @Generated(hash = 1431548990)
    public LastBrowerBean() {
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
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

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
    }

    public long getLocalTime() {
        return localTime;
    }

    public void setLocalTime(long localTime) {
        this.localTime = localTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
