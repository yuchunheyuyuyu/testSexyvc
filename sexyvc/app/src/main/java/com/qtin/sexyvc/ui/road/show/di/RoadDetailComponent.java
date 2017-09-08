package com.qtin.sexyvc.ui.road.show.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.road.show.RoadDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RoadDetailModule.class,dependencies = AppComponent.class)
public interface RoadDetailComponent {
    void inject(RoadDetailActivity activity);
}
