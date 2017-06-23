package com.qtin.sexyvc.ui.follow.list.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.follow.list.ConcernListActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ConcernListModule.class,dependencies = AppComponent.class)
public interface ConcernListComponent {
    void inject(ConcernListActivity activity);
}
