package com.qtin.sexyvc.ui.main.fragInvestor.bean;

/**
 * Created by ls on 17/6/11.
 */

public class InvestorBackBean {
    private int errCode;
    private String errMsg;
    private InvestorBean items;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public InvestorBean getItems() {
        return items;
    }

    public void setItems(InvestorBean items) {
        this.items = items;
    }
}
