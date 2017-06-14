package com.qtin.sexyvc.ui.user.modify.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.modify.ModifyContract;
import com.qtin.sexyvc.ui.user.modify.ModifyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ModifyModule {
    private ModifyContract.View view;

    public ModifyModule(ModifyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyContract.Model provideDemoModel(ModifyModel model){
        return model;
    }
}
