package com.qtin.sexyvc.ui.login.account.login.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.account.login.LoginContract;
import com.qtin.sexyvc.ui.login.account.login.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class LoginModule {
    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LoginContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    LoginContract.Model provideDemoModel(LoginModel model){
        return model;
    }
}
