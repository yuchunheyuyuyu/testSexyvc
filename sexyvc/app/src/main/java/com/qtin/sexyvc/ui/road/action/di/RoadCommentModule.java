package com.qtin.sexyvc.ui.road.action.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.road.action.RoadCommentContract;
import com.qtin.sexyvc.ui.road.action.RoadCommentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RoadCommentModule {
    private RoadCommentContract.View view;

    public RoadCommentModule(RoadCommentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RoadCommentContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RoadCommentContract.Model provideDemoModel(RoadCommentModel model){
        return model;
    }
}
