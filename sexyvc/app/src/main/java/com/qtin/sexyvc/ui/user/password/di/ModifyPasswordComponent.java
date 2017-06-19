package com.qtin.sexyvc.ui.user.password.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.password.ModifyPasswordActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ModifyPasswordModule.class,dependencies = AppComponent.class)
public interface ModifyPasswordComponent {
    void inject(ModifyPasswordActivity activity);
}
