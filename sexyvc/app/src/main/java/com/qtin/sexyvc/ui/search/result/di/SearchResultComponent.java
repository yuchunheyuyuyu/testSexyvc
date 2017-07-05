package com.qtin.sexyvc.ui.search.result.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.search.result.SearchResultActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SearchResultModule.class,dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity activity);
}
