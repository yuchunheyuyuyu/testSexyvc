package com.qtin.sexyvc.ui.flash.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.flash.FlashContract;
import com.qtin.sexyvc.ui.flash.FlashModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FlashModule {
    private FlashContract.View view;

    public FlashModule(FlashContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FlashContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    FlashContract.Model provideDemoModel(FlashModel model){
        return model;
    }
}
