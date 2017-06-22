package com.qtin.sexyvc.ui.concern.search.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.concern.search.ConcernSearchContract;
import com.qtin.sexyvc.ui.concern.search.ConcernSearchModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ConcernSearchModule {
    private ConcernSearchContract.View view;

    public ConcernSearchModule(ConcernSearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConcernSearchContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ConcernSearchContract.Model provideDemoModel(ConcernSearchModel model){
        return model;
    }
}
