package com.qtin.sexyvc.ui.demo.activity.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.demo.activity.DemoContract;
import com.qtin.sexyvc.ui.demo.activity.DemoModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class DemoModule {
    private DemoContract.View view;

    public DemoModule(DemoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DemoContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    DemoContract.Model provideDemoModel(DemoModel model){
        return model;
    }
}
