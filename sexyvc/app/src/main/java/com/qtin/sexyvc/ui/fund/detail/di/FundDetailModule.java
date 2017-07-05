package com.qtin.sexyvc.ui.fund.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.fund.detail.FundDetailContract;
import com.qtin.sexyvc.ui.fund.detail.FundDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FundDetailModule {
    private FundDetailContract.View view;

    public FundDetailModule(FundDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FundDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    FundDetailContract.Model provideDemoModel(FundDetailModel model){
        return model;
    }
}
