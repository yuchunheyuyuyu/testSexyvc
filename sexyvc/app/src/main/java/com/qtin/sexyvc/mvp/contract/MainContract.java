package com.qtin.sexyvc.mvp.contract;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by ls on 17/2/27.
 */

public interface MainContract {
    interface View extends BaseView{
        //申请权限
        RxPermissions getRxPermissions();
        void gotoGithub();
    }
    interface Model extends IModel{

    }
}
