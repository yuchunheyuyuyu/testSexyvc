package com.qtin.sexyvc.ui.improve.fund.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.improve.fund.ImproveFundContract;
import com.qtin.sexyvc.ui.improve.fund.ImproveFundModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ImproveFundModule {
    private ImproveFundContract.View view;

    public ImproveFundModule(ImproveFundContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ImproveFundContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ImproveFundContract.Model provideDemoModel(ImproveFundModel model){
        return model;
    }
}
