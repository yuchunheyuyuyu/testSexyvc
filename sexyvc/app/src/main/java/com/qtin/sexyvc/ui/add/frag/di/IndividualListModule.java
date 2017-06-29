package com.qtin.sexyvc.ui.add.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.add.frag.IndividualListContract;
import com.qtin.sexyvc.ui.add.frag.IndividualListModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class IndividualListModule {
    private IndividualListContract.View view;

    public IndividualListModule(IndividualListContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    IndividualListContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    IndividualListContract.Model provideDemoModel(IndividualListModel model){
        return model;
    }
}
