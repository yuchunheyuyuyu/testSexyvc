package com.qtin.sexyvc.ui.concern.set.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.concern.set.SetGroupActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SetGroupModule.class,dependencies = AppComponent.class)
public interface SetGroupComponent {
    void inject(SetGroupActivity activity);
}
