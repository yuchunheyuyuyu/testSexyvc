package com.qtin.sexyvc.ui.recommend.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.recommend.RecommendActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendActivity activity);
}
