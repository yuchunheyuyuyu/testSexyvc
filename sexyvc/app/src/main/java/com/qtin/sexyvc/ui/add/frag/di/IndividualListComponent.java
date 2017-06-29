package com.qtin.sexyvc.ui.add.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.add.frag.IndividualListFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = IndividualListModule.class,dependencies = AppComponent.class)
public interface IndividualListComponent {
    void inject(IndividualListFrag demoFrag);
}
