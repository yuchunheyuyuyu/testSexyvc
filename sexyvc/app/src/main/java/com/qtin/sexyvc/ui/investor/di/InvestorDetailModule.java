package com.qtin.sexyvc.ui.investor.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.investor.InvestorDetailContract;
import com.qtin.sexyvc.ui.investor.InvestorDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class InvestorDetailModule {
    private InvestorDetailContract.View view;

    public InvestorDetailModule(InvestorDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    InvestorDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    InvestorDetailContract.Model provideDemoModel(InvestorDetailModel model){
        return model;
    }
}
