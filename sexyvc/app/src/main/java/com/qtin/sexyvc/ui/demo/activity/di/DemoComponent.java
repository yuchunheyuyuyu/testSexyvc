package com.qtin.sexyvc.ui.demo.activity.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.demo.activity.DemoActivity;
import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = DemoModule.class,dependencies = AppComponent.class)
public interface DemoComponent {
    void inject(DemoActivity activity);
}
