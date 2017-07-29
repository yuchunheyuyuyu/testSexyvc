package com.qtin.sexyvc.ui.road.success.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.road.success.SuccessActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RoadSuccessModule.class,dependencies = AppComponent.class)
public interface RoadSuccessComponent {
    void inject(SuccessActivity activity);
}
