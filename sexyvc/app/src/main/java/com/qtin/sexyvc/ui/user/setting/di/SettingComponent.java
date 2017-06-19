package com.qtin.sexyvc.ui.user.setting.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.setting.SettingActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SettingModule.class,dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(SettingActivity activity);
}
