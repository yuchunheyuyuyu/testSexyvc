package com.qtin.sexyvc.ui.login.account.create.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.account.create.CreateContract;
import com.qtin.sexyvc.ui.login.account.create.CreateModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class CreateModule {
    private CreateContract.View view;

    public CreateModule(CreateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CreateContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    CreateContract.Model provideDemoModel(CreateModel model){
        return model;
    }
}
