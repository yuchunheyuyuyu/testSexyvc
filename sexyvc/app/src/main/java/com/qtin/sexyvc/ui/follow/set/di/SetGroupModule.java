package com.qtin.sexyvc.ui.follow.set.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.follow.set.SetGroupContract;
import com.qtin.sexyvc.ui.follow.set.SetGroupModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class SetGroupModule {
    private SetGroupContract.View view;

    public SetGroupModule(SetGroupContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SetGroupContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    SetGroupContract.Model provideDemoModel(SetGroupModel model){
        return model;
    }
}
