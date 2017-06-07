package com.qtin.sexyvc.di.component;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.di.module.LoginModule;
import com.qtin.sexyvc.mvp.test.login.LoginActivity;
import dagger.Component;

/**
 * Created by ls on 17/3/6.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
