package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.mvp.model.api.Api;
import java.util.ArrayList;

/**
 * Created by ls on 17/3/7.
 */

public class BaseListEntity<T> {
    private int errCode;
    private String errMsg;
    private ArrayList<T> items;

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

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        if (errCode==Api.RequestSuccess) {
            return true;
        } else {
            return false;
        }
    }
}
