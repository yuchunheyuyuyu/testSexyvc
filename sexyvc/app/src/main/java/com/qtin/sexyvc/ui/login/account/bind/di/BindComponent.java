package com.qtin.sexyvc.ui.login.account.bind.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.account.bind.BindActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = BindModule.class,dependencies = AppComponent.class)
public interface BindComponent {
    void inject(BindActivity activity);
}
