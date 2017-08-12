package com.qtin.sexyvc.ui.login.register;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RegisterContract {
    interface View extends BaseView{
        void validateSuccess();
        void registerSuccess(UserInfoEntity entity);
    }
    interface Model extends IModel{
        Observable<CodeEntity> getVertifyCode(String token, String mobile, String code_type);
        Observable<BaseEntity<BindEntity>> validateCode(String mobile, String code_type, String code_value);
        Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity);

        void saveUser(UserEntity entity);
        void saveUsrInfo(UserInfoEntity entity);
    }
}
