package com.qtin.sexyvc.ui.follow.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.follow.detail.ConcernDetailContract;
import com.qtin.sexyvc.ui.follow.detail.ConcernDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ConcernDetailModule {
    private ConcernDetailContract.View view;

    public ConcernDetailModule(ConcernDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConcernDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ConcernDetailContract.Model provideDemoModel(ConcernDetailModel model){
        return model;
    }
}
