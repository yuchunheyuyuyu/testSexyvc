package com.qtin.sexyvc.ui.login.password.reset;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ResetPasswordContract {
    interface View extends BaseView{
        void resetSuccess();
    }
    interface Model extends IModel{
        Observable<CodeEntity> resetPassword(String code_value, String mobile, String password);
    }
}
