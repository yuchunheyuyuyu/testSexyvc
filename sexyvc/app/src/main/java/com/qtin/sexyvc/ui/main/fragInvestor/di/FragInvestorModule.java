package com.qtin.sexyvc.ui.main.fragInvestor.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.main.fragInvestor.FragInvestorContract;
import com.qtin.sexyvc.ui.main.fragInvestor.FragInvestorModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FragInvestorModule {
    private FragInvestorContract.View view;

    public FragInvestorModule(FragInvestorContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    FragInvestorContract.View provideFragProjectView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    FragInvestorContract.Model provideFragProjectModel(FragInvestorModel model){
        return model;
    }
}
