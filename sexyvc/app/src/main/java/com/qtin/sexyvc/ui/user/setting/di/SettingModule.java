package com.qtin.sexyvc.ui.user.setting.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.setting.SettingContract;
import com.qtin.sexyvc.ui.user.setting.SettingModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SettingModule {
    private SettingContract.View view;

    public SettingModule(SettingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SettingContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SettingContract.Model provideDemoModel(SettingModel model){
        return model;
    }
}
