package com.qtin.sexyvc.ui.subject.list.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.subject.list.SubjectListActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SubjectListModule.class,dependencies = AppComponent.class)
public interface SubjectListComponent {
    void inject(SubjectListActivity activity);
}
