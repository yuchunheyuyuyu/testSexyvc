package com.qtin.sexyvc.ui.create.investor.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.create.investor.CreateInvestorActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = CreateInvestorModule.class,dependencies = AppComponent.class)
public interface CreateInvestorComponent {
    void inject(CreateInvestorActivity activity);
}
