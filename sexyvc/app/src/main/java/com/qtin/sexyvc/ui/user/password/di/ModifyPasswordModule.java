package com.qtin.sexyvc.ui.user.password.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.password.ModifyPasswordContract;
import com.qtin.sexyvc.ui.user.password.ModifyPasswordModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ModifyPasswordModule {
    private ModifyPasswordContract.View view;

    public ModifyPasswordModule(ModifyPasswordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyPasswordContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyPasswordContract.Model provideDemoModel(ModifyPasswordModel model){
        return model;
    }
}
