package com.qtin.sexyvc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.mvp.contract.UploadContract;
import com.qtin.sexyvc.mvp.model.UploadModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/3/1.
 */
@Module
public class UploadModule {
    private UploadContract.View view;

    public UploadModule(UploadContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UploadContract.View provideUploadView(){
        return view;
    }

    @ActivityScope
    @Provides
    UploadContract.Model provideUploadModel(UploadModel model){
        return model;
    }
}
