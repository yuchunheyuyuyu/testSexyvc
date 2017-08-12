package com.qtin.sexyvc.ui.login.register.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.register.RegisterContract;
import com.qtin.sexyvc.ui.login.register.RegisterModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RegisterModule {
    private RegisterContract.View view;

    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegisterContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RegisterContract.Model provideDemoModel(RegisterModel model){
        return model;
    }
}
