package com.qtin.sexyvc.ui.subject.list.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.subject.list.SubjectListContract;
import com.qtin.sexyvc.ui.subject.list.SubjectListModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SubjectListModule {
    private SubjectListContract.View view;

    public SubjectListModule(SubjectListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SubjectListContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SubjectListContract.Model provideDemoModel(SubjectListModel model){
        return model;
    }
}
