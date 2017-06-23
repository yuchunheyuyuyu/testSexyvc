package com.qtin.sexyvc.ui.subject.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SubjectDetailModule.class,dependencies = AppComponent.class)
public interface SubjectDetailComponent {
    void inject(SubjectDetailActivity activity);
}
