package com.qtin.sexyvc.ui.search.action.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.search.action.SearchActionActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SearchActionModule.class,dependencies = AppComponent.class)
public interface SearchActionComponent {
    void inject(SearchActionActivity activity);
}
