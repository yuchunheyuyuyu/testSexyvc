package com.qtin.sexyvc.ui.login.register.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.register.RegisterActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RegisterModule.class,dependencies = AppComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}
