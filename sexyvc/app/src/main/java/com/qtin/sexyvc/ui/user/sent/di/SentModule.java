package com.qtin.sexyvc.ui.user.sent.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.sent.SentContract;
import com.qtin.sexyvc.ui.user.sent.SentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SentModule {
    private SentContract.View view;

    public SentModule(SentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SentContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SentContract.Model provideDemoModel(SentModel model){
        return model;
    }
}
