package com.qtin.sexyvc.ui.project.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.project.BaseProjectActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = BaseProjectModule.class,dependencies = AppComponent.class)
public interface BaseProjectComponent {
    void inject(BaseProjectActivity activity);
}
