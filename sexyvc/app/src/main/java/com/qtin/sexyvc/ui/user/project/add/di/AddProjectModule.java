package com.qtin.sexyvc.ui.user.project.add.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.project.add.AddProjectContract;
import com.qtin.sexyvc.ui.user.project.add.AddProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class AddProjectModule {
    private AddProjectContract.View view;

    public AddProjectModule(AddProjectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddProjectContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    AddProjectContract.Model provideDemoModel(AddProjectModel model){
        return model;
    }
}
