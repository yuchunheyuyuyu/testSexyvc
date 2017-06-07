package com.qtin.sexyvc.ui.demo.fragment.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.demo.fragment.DemoFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = DemoModule.class,dependencies = AppComponent.class)
public interface DemoComponent {
    void inject(DemoFrag demoFrag);
}
