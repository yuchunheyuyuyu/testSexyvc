package com.qtin.sexyvc.ui.login.password.set;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface SetPasswordContract {
    interface View extends BaseView{
        void rigisterSuccess();
        void bindSuccess();
    }
    interface Model extends IModel{
        Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity);
        void saveUser(UserEntity entity);
        boolean changeBindStatus(int bind);
        Observable<CodeEntity> bindMobile(String token, String mobile, String password);
        String getToken();
    }
}