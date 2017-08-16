package com.qtin.sexyvc.ui.recommend.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.recommend.RecommendContract;
import com.qtin.sexyvc.ui.recommend.RecommendModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RecommendModule {
    private RecommendContract.View view;

    public RecommendModule(RecommendContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecommendContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RecommendContract.Model provideDemoModel(RecommendModel model){
        return model;
    }
}
