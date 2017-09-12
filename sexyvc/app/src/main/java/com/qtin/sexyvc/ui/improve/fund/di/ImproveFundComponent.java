package com.qtin.sexyvc.ui.improve.fund.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.improve.fund.ImproveFundActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ImproveFundModule.class,dependencies = AppComponent.class)
public interface ImproveFundComponent {
    void inject(ImproveFundActivity activity);
}
