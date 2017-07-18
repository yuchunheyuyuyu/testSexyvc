package com.qtin.sexyvc.ui.add.search.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.add.search.SearchObjectActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SearchObjectModule.class,dependencies = AppComponent.class)
public interface SearchObjectComponent {
    void inject(SearchObjectActivity activity);
}
