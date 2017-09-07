package com.qtin.sexyvc.ui.mycase.add.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.mycase.add.AddCaseContract;
import com.qtin.sexyvc.ui.mycase.add.AddCaseModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class AddCaseModule {
    private AddCaseContract.View view;

    public AddCaseModule(AddCaseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddCaseContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    AddCaseContract.Model provideDemoModel(AddCaseModel model){
        return model;
    }
}
