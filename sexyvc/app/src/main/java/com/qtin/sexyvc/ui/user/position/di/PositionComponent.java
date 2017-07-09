package com.qtin.sexyvc.ui.user.position.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.position.PositionActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = PositionModule.class,dependencies = AppComponent.class)
public interface PositionComponent {
    void inject(PositionActivity activity);
}
