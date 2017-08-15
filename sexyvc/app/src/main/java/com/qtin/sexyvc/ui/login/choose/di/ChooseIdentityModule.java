package com.qtin.sexyvc.ui.login.choose.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.login.choose.ChooseIdentityContract;
import com.qtin.sexyvc.ui.login.choose.ChooseIdentityModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ChooseIdentityModule {
    private ChooseIdentityContract.View view;

    public ChooseIdentityModule(ChooseIdentityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChooseIdentityContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ChooseIdentityContract.Model provideDemoModel(ChooseIdentityModel model){
        return model;
    }
}
