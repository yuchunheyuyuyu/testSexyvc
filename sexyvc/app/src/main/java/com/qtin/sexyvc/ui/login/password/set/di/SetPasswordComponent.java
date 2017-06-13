package com.qtin.sexyvc.ui.login.password.set.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.password.set.SetPasswordActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SetPasswordModule.class,dependencies = AppComponent.class)
public interface SetPasswordComponent {
    void inject(SetPasswordActivity activity);
}
