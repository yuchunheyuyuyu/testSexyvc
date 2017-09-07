package com.qtin.sexyvc.ui.mycase.add.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.mycase.add.AddCaseActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = AddCaseModule.class,dependencies = AppComponent.class)
public interface AddCaseComponent {
    void inject(AddCaseActivity activity);
}
