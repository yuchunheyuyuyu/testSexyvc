package com.qtin.sexyvc.ui.login.account.bind;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface BindContract {
    interface View extends BaseView{
        void gotoSetPassword();
        void notNeedSetPassword();
    }
    interface Model extends IModel{
        Observable<CodeEntity> getVertifyCode(String mobile, String code_type);
        Observable<BaseEntity<BindEntity>> validateCode(String mobile,String code_type,String code_value);
        Observable<CodeEntity> bindMobile(String token,String mobile,String password);
        boolean changeBindStatus(int bind);
        String getToken();
    }
}
