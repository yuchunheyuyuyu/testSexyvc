package com.qtin.sexyvc.ui.road.action.success.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.road.action.success.RoadSuccessContract;
import com.qtin.sexyvc.ui.road.action.success.RoadSuccessModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RoadSuccessModule {
    private RoadSuccessContract.View view;

    public RoadSuccessModule(RoadSuccessContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RoadSuccessContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RoadSuccessContract.Model provideDemoModel(RoadSuccessModel model){
        return model;
    }
}
