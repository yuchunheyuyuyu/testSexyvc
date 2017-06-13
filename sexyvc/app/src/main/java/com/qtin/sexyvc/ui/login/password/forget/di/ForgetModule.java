package com.qtin.sexyvc.ui.login.password.forget.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.password.forget.ForgetContract;
import com.qtin.sexyvc.ui.login.password.forget.ForgetModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ForgetModule {
    private ForgetContract.View view;

    public ForgetModule(ForgetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForgetContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ForgetContract.Model provideDemoModel(ForgetModel model){
        return model;
    }
}
