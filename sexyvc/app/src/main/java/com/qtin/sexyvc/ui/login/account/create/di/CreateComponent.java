package com.qtin.sexyvc.ui.login.account.create.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.account.create.CreateActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = CreateModule.class,dependencies = AppComponent.class)
public interface CreateComponent {
    void inject(CreateActivity activity);
}
