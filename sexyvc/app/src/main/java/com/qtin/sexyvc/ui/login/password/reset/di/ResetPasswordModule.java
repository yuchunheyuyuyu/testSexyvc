package com.qtin.sexyvc.ui.login.password.reset.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.password.reset.ResetPasswordContract;
import com.qtin.sexyvc.ui.login.password.reset.ResetPasswordModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ResetPasswordModule {
    private ResetPasswordContract.View view;

    public ResetPasswordModule(ResetPasswordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ResetPasswordContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ResetPasswordContract.Model provideDemoModel(ResetPasswordModel model){
        return model;
    }
}
