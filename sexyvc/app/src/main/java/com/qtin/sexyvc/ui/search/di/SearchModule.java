package com.qtin.sexyvc.ui.search.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.search.SearchContract;
import com.qtin.sexyvc.ui.search.SearchModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SearchModule {
    private SearchContract.View view;

    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchContract.Model provideDemoModel(SearchModel model){
        return model;
    }
}
