package com.qtin.sexyvc.di.component;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.di.module.UploadModule;
import com.qtin.sexyvc.mvp.test.upload.UploadActivity;
import dagger.Component;

/**
 * Created by ls on 17/3/1.
 */
@ActivityScope
@Component(modules = UploadModule.class,dependencies = AppComponent.class)
public interface UploadComponent {
    void inject(UploadActivity activity);
}
