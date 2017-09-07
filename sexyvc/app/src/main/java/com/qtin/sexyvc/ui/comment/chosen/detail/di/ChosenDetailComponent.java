package com.qtin.sexyvc.ui.comment.chosen.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.comment.chosen.detail.ChosenDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ChosenDetailModule.class,dependencies = AppComponent.class)
public interface ChosenDetailComponent {
    void inject(ChosenDetailActivity activity);
}
