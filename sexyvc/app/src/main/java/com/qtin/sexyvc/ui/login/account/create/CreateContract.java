package com.qtin.sexyvc.ui.login.account.create;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.RegisterStatusBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface CreateContract {
    interface View extends BaseView{
        void gotoBind(int platform_type);
        void notNeedBind();
        void checkRegisterSuccess(int has_register);
        void startLoad(String msg);
        void endLoad();
    }
    interface Model extends IModel{
        void saveUser(UserEntity entity);
        Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity);

        boolean isLogin();
        void saveUsrInfo(UserInfoEntity entity);
        Observable<BaseEntity<RegisterStatusBean>> checkRegisterStatus(String mobile);
    }
}
