package com.qtin.sexyvc.ui.main.fragconcern.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.main.fragconcern.FragConcern;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = ConcernModule.class,dependencies = AppComponent.class)
public interface ConcernComponent {
    void inject(FragConcern demoFrag);
}
