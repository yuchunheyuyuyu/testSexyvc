package com.qtin.sexyvc.ui.mycase.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.mycase.MyCaseActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = MyCaseModule.class,dependencies = AppComponent.class)
public interface MyCaseComponent {
    void inject(MyCaseActivity activity);
}
