package com.qtin.sexyvc.ui.more.object.road.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.more.object.road.FragRoadComment;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = FragRoadCommentModule.class,dependencies = AppComponent.class)
public interface FragRoadCommentComponent {
    void inject(FragRoadComment demoFrag);
}
