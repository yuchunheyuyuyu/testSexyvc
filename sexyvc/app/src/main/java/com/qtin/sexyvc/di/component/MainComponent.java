package com.qtin.sexyvc.di.component;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.di.module.MainModule;
import com.qtin.sexyvc.mvp.test.oldmain.OldMainActivity;
import dagger.Component;

/**
 * Created by ls on 17/2/27.
 */
@ActivityScope
@Component(modules = MainModule.class,dependencies = AppComponent.class)
public interface MainComponent {
    void inject(OldMainActivity mainActivity);
}
