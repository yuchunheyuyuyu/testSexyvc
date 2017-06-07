package com.qtin.sexyvc.mvp.model.entity;

import com.qtin.sexyvc.mvp.model.api.Api;

/**
 * Created by ls on 17/3/1.
 */
public class BaseEntity<T> {
    private String errCode;
    private String errMsg;
    private T items;

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

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
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