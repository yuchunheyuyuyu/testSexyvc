package com.qtin.sexyvc.ui.login.password.reset.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.password.reset.ResetPasswordActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ResetPasswordModule.class,dependencies = AppComponent.class)
public interface ResetPasswordComponent {
    void inject(ResetPasswordActivity activity);
}
