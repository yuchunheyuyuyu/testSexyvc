package com.qtin.sexyvc.ui.login.account.bind.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.account.bind.BindContract;
import com.qtin.sexyvc.ui.login.account.bind.BindModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class BindModule {
    private BindContract.View view;

    public BindModule(BindContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BindContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    BindContract.Model provideDemoModel(BindModel model){
        return model;
    }
}
