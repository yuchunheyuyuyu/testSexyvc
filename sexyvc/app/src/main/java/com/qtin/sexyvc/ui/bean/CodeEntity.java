package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.mvp.model.api.Api;

/**
 * Created by ls on 17/6/13.
 */

public class CodeEntity {
    private int errCode;
    private String errMsg;

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

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        if (errCode== Api.RequestSuccess) {
            return true;
        } else {
            return false;
        }
    }
}
