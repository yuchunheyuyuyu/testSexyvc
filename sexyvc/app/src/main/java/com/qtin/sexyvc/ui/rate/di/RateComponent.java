package com.qtin.sexyvc.ui.rate.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.rate.RateActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = RateModule.class,dependencies = AppComponent.class)
public interface RateComponent {
    void inject(RateActivity activity);
}
