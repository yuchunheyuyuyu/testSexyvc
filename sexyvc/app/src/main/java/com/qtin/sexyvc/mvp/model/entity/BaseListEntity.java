package com.qtin.sexyvc.mvp.model.entity;

import com.qtin.sexyvc.mvp.model.api.Api;
import java.util.ArrayList;

/**
 * Created by ls on 17/3/7.
 */

public class BaseListEntity<T> {
    private String errCode;
    private String errMsg;
    private ArrayList<T> items;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
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
        if (errCode.equals(Api.RequestSuccess)) {
            return true;
        } else {
            return false;
        }
    }
}
