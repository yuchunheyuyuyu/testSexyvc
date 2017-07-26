package com.qtin.sexyvc.ui.login.password.forget;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ForgetContract {
    interface View extends BaseView{
        void validateSuccess();
    }
    interface Model extends IModel{
        Observable<CodeEntity> getVertifyCode(String token,String mobile, String code_type);
        Observable<BaseEntity<BindEntity>> validateCode(String mobile, String code_type, String code_value);
    }
}
