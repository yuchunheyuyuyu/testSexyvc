package com.qtin.sexyvc.ui.user.influency.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.influency.InfluencyContract;
import com.qtin.sexyvc.ui.user.influency.InfluencyModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class InfluencyModule {
    private InfluencyContract.View view;

    public InfluencyModule(InfluencyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    InfluencyContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    InfluencyContract.Model provideDemoModel(InfluencyModel model){
        return model;
    }
}
