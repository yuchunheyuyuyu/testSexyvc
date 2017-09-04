package com.qtin.sexyvc.ui.mycase.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.mycase.MyCaseContract;
import com.qtin.sexyvc.ui.mycase.MyCaseModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class MyCaseModule {
    private MyCaseContract.View view;

    public MyCaseModule(MyCaseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCaseContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCaseContract.Model provideDemoModel(MyCaseModel model){
        return model;
    }
}
