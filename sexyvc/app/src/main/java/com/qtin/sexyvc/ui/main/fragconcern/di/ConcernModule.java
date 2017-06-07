package com.qtin.sexyvc.ui.main.fragconcern.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.main.fragconcern.ConcernContract;
import com.qtin.sexyvc.ui.main.fragconcern.ConcernModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ConcernModule {
    private ConcernContract.View view;

    public ConcernModule(ConcernContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    ConcernContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    ConcernContract.Model provideDemoModel(ConcernModel model){
        return model;
    }
}
