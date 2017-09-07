package com.qtin.sexyvc.ui.comment.chosen.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.comment.chosen.detail.ChosenDetailContract;
import com.qtin.sexyvc.ui.comment.chosen.detail.ChosenDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ChosenDetailModule {
    private ChosenDetailContract.View view;

    public ChosenDetailModule(ChosenDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChosenDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ChosenDetailContract.Model provideDemoModel(ChosenDetailModel model){
        return model;
    }
}
