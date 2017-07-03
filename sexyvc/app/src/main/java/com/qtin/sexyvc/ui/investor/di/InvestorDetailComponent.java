package com.qtin.sexyvc.ui.investor.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = InvestorDetailModule.class,dependencies = AppComponent.class)
public interface InvestorDetailComponent {
    void inject(InvestorDetailActivity activity);
}
