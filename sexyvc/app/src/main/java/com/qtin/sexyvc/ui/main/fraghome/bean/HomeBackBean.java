package com.qtin.sexyvc.ui.main.fraghome.bean;

/**
 * Created by ls on 17/6/10.
 */

public class HomeBackBean {
    private int errCode;
    private String errMsg;
    private HomeBean items;

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

    public HomeBean getItems() {
        return items;
    }

    public void setItems(HomeBean items) {
        this.items = items;
    }
}
