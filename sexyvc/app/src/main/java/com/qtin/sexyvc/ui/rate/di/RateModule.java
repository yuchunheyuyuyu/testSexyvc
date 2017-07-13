package com.qtin.sexyvc.ui.rate.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.rate.RateContract;
import com.qtin.sexyvc.ui.rate.RateModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class RateModule {
    private RateContract.View view;

    public RateModule(RateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RateContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    RateContract.Model provideDemoModel(RateModel model){
        return model;
    }
}
