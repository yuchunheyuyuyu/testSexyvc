package com.qtin.sexyvc.ui.main.fragmine.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.main.fragmine.FragMine;
import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = FragmineModule.class,dependencies = AppComponent.class)
public interface FragMineComponent {
    void inject(FragMine fragMine);
}
