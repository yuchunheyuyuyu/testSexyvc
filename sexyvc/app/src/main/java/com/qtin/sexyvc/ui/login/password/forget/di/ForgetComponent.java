package com.qtin.sexyvc.ui.login.password.forget.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.password.forget.ForgetActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ForgetModule.class,dependencies = AppComponent.class)
public interface ForgetComponent {
    void inject(ForgetActivity activity);
}
