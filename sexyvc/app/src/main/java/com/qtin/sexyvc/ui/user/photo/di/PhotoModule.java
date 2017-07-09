package com.qtin.sexyvc.ui.user.photo.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.photo.PhotoContract;
import com.qtin.sexyvc.ui.user.photo.PhotoModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class PhotoModule {
    private PhotoContract.View view;

    public PhotoModule(PhotoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoContract.Model provideDemoModel(PhotoModel model){
        return model;
    }
}
