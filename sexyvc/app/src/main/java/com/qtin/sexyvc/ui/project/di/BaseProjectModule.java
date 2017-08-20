package com.qtin.sexyvc.ui.project.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.project.BaseProjectContract;
import com.qtin.sexyvc.ui.project.BaseProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class BaseProjectModule {
    private BaseProjectContract.View view;

    public BaseProjectModule(BaseProjectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BaseProjectContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    BaseProjectContract.Model provideDemoModel(BaseProjectModel model){
        return model;
    }
}
