package com.qtin.sexyvc.ui.create.investor.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.create.investor.CreateInvestorContract;
import com.qtin.sexyvc.ui.create.investor.CreateInvestorModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class CreateInvestorModule {
    private CreateInvestorContract.View view;

    public CreateInvestorModule(CreateInvestorContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CreateInvestorContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    CreateInvestorContract.Model provideDemoModel(CreateInvestorModel model){
        return model;
    }
}
