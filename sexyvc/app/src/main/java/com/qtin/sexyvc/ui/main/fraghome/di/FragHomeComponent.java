package com.qtin.sexyvc.ui.main.fraghome.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.main.fraghome.FragHome;
import dagger.Component;
/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = FragHomeModule.class,dependencies = AppComponent.class)
public interface FragHomeComponent {
    void inject(FragHome fragHome);
}
