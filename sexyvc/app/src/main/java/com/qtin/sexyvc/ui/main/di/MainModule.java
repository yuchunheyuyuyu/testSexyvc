package com.qtin.sexyvc.ui.main.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.main.MainContract;
import com.qtin.sexyvc.ui.main.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideDemoModel(MainModel model){
        return model;
    }
}
