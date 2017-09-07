package com.qtin.sexyvc.ui.user.influency.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.influency.InfluencyActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = InfluencyModule.class,dependencies = AppComponent.class)
public interface InfluencyComponent {
    void inject(InfluencyActivity activity);
}
