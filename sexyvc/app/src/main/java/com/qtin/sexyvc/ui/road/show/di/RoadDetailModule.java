package com.qtin.sexyvc.ui.road.show.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.road.show.RoadDetailContract;
import com.qtin.sexyvc.ui.road.show.RoadDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RoadDetailModule {
    private RoadDetailContract.View view;

    public RoadDetailModule(RoadDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RoadDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RoadDetailContract.Model provideDemoModel(RoadDetailModel model){
        return model;
    }
}
