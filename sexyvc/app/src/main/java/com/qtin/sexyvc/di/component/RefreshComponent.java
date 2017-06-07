package com.qtin.sexyvc.di.component;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.di.module.RefreshModule;
import com.qtin.sexyvc.mvp.test.refresh.RefreshActivity;

import dagger.Component;
/**
 * Created by ls on 17/2/28.
 */
@ActivityScope
@Component(modules = RefreshModule.class,dependencies = AppComponent.class)
public interface RefreshComponent {
    void inject(RefreshActivity activity);
}
