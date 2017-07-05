package com.qtin.sexyvc.ui.search.action.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.search.action.SearchActionContract;
import com.qtin.sexyvc.ui.search.action.SearchActionModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SearchActionModule {
    private SearchActionContract.View view;

    public SearchActionModule(SearchActionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchActionContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchActionContract.Model provideDemoModel(SearchActionModel model){
        return model;
    }
}
