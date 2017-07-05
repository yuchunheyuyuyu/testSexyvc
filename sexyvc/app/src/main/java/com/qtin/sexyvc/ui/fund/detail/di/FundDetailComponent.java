package com.qtin.sexyvc.ui.fund.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = FundDetailModule.class,dependencies = AppComponent.class)
public interface FundDetailComponent {
    void inject(FundDetailActivity activity);
}
