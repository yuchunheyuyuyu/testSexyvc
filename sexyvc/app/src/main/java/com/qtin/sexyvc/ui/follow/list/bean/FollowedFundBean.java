package com.qtin.sexyvc.ui.follow.list.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/9/13.
 */

public class FollowedFundBean implements DataTypeInterface {

    private long id;
    private long fund_id;
    private String fund_name;
    private String fund_logo;
    private int investor_number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getFund_logo() {
        return fund_logo;
    }

    public void setFund_logo(String fund_logo) {
        this.fund_logo = fund_logo;
    }

    public int getInvestor_number() {
        return investor_number;
    }

    public void setInvestor_number(int investor_number) {
        this.investor_number = investor_number;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_FUND;
    }
}
