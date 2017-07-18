package com.qtin.sexyvc.ui.add.search.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.add.search.SearchObjectContract;
import com.qtin.sexyvc.ui.add.search.SearchObjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SearchObjectModule {
    private SearchObjectContract.View view;

    public SearchObjectModule(SearchObjectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchObjectContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchObjectContract.Model provideDemoModel(SearchObjectModel model){
        return model;
    }
}
