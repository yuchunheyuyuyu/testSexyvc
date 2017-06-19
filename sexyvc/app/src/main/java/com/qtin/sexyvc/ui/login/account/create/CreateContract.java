package com.qtin.sexyvc.ui.login.account.create;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface CreateContract {
    interface View extends BaseView{
        void validateSuccess();
        void gotoBind(int platform_type);
        void notNeedBind();
    }
    interface Model extends IModel{
        void saveUser(UserEntity entity);
        Observable<CodeEntity> getVertifyCode(String mobile,String code_type);
        Observable<BaseEntity<BindEntity>> validateCode(String mobile, String code_type, String code_value);
        Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity);

        boolean isLogin();
    }
}
