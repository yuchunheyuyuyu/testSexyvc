package com.qtin.sexyvc.ui.user.project.add.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = AddProjectModule.class,dependencies = AppComponent.class)
public interface AddProjectComponent {
    void inject(AddProjectActivity activity);
}
