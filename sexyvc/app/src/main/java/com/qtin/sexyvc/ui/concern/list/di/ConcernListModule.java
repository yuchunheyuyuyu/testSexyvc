package com.qtin.sexyvc.ui.concern.list.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.concern.list.ConcernListContract;
import com.qtin.sexyvc.ui.concern.list.ConcernListModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ConcernListModule {
    private ConcernListContract.View view;

    public ConcernListModule(ConcernListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConcernListContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ConcernListContract.Model provideDemoModel(ConcernListModel model){
        return model;
    }
}
