package com.qtin.sexyvc.ui.concern.search.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.concern.search.ConcernSearchActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ConcernSearchModule.class,dependencies = AppComponent.class)
public interface ConcernSearchComponent {
    void inject(ConcernSearchActivity activity);
}
