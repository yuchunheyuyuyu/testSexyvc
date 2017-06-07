package com.qtin.sexyvc.ui.main.fragInvestor.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.main.fragInvestor.FragInvestor;
import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = FragInvestorModule.class,dependencies = AppComponent.class)
public interface FragInvestorComponent {
    void inject(FragInvestor fragProject);
}
