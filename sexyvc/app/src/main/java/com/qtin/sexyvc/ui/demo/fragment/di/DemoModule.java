package com.qtin.sexyvc.ui.demo.fragment.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.demo.fragment.DemoContract;
import com.qtin.sexyvc.ui.demo.fragment.DemoModel;
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
    @FragmentScope
    @Provides
    DemoContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    DemoContract.Model provideDemoModel(DemoModel model){
        return model;
    }
}
