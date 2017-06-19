package com.qtin.sexyvc.ui.user.project.my.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.project.my.MyProjectActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = MyProjectModule.class,dependencies = AppComponent.class)
public interface MyProjectComponent {
    void inject(MyProjectActivity activity);
}
