package com.qtin.sexyvc.ui.login.password.set.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.password.set.SetPasswordContract;
import com.qtin.sexyvc.ui.login.password.set.SetPasswordModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SetPasswordModule {
    private SetPasswordContract.View view;

    public SetPasswordModule(SetPasswordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SetPasswordContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SetPasswordContract.Model provideDemoModel(SetPasswordModel model){
        return model;
    }
}
