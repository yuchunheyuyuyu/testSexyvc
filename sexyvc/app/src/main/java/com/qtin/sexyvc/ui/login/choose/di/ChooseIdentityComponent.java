package com.qtin.sexyvc.ui.login.choose.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.login.choose.ChooseIdentityActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ChooseIdentityModule.class,dependencies = AppComponent.class)
public interface ChooseIdentityComponent {
    void inject(ChooseIdentityActivity activity);
}
