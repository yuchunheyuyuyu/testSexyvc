package com.qtin.sexyvc.ui.concern.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.concern.detail.ConcernDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ConcernDetailModule.class,dependencies = AppComponent.class)
public interface ConcernDetailComponent {
    void inject(ConcernDetailActivity activity);
}
