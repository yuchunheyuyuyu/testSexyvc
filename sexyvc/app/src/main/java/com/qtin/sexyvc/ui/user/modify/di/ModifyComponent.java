package com.qtin.sexyvc.ui.user.modify.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ModifyModule.class,dependencies = AppComponent.class)
public interface ModifyComponent {
    void inject(ModifyActivity activity);
}
