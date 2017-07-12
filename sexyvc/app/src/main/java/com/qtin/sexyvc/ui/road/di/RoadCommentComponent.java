package com.qtin.sexyvc.ui.road.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.road.RoadCommentActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RoadCommentModule.class,dependencies = AppComponent.class)
public interface RoadCommentComponent {
    void inject(RoadCommentActivity activity);
}
