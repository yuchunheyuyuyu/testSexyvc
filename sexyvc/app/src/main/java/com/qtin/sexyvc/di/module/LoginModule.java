package com.qtin.sexyvc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.mvp.contract.LoginContarct;
import com.qtin.sexyvc.mvp.model.LoginModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/3/6.
 */
@Module
public class LoginModule {

    private LoginContarct.View view;

    public LoginModule(LoginContarct.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    LoginContarct.View provideLoginView(){
        return view;
    }
    @ActivityScope
    @Provides
    LoginContarct.Model provideLoginModel(LoginModel model){
        return model;
    }
}
