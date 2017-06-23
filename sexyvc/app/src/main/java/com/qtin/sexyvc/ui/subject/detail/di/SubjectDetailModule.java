package com.qtin.sexyvc.ui.subject.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailContract;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SubjectDetailModule {
    private SubjectDetailContract.View view;

    public SubjectDetailModule(SubjectDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SubjectDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SubjectDetailContract.Model provideDemoModel(SubjectDetailModel model){
        return model;
    }
}
