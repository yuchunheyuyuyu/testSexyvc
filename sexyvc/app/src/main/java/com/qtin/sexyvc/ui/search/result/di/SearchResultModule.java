package com.qtin.sexyvc.ui.search.result.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.search.result.SearchResultContract;
import com.qtin.sexyvc.ui.search.result.SearchResultModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SearchResultModule {
    private SearchResultContract.View view;

    public SearchResultModule(SearchResultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.Model provideDemoModel(SearchResultModel model){
        return model;
    }
}
