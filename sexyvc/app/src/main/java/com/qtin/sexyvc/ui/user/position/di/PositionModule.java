package com.qtin.sexyvc.ui.user.position.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.position.PositionContract;
import com.qtin.sexyvc.ui.user.position.PositionModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class PositionModule {
    private PositionContract.View view;

    public PositionModule(PositionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PositionContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    PositionContract.Model provideDemoModel(PositionModel model){
        return model;
    }
}
