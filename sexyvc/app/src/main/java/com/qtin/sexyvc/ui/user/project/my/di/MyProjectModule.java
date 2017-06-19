package com.qtin.sexyvc.ui.user.project.my.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.project.my.MyProjectContract;
import com.qtin.sexyvc.ui.user.project.my.MyProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class MyProjectModule {
    private MyProjectContract.View view;

    public MyProjectModule(MyProjectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyProjectContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    MyProjectContract.Model provideDemoModel(MyProjectModel model){
        return model;
    }
}
