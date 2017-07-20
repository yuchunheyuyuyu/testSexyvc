package com.qtin.sexyvc.ui.login.account.login;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface LoginContract {
    interface View extends BaseView{
        void loginSuccess();
    }
    interface Model extends IModel{
        Observable<BaseEntity<UserEntity>> login(int client_type,String username,int account_type,String password,String device_token);
        void saveUser(UserEntity entity);
        void saveUsrInfo(UserInfoEntity entity);
    }
}
