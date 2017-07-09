package com.qtin.sexyvc.ui.user.photo.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.photo.PhotoActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = PhotoModule.class,dependencies = AppComponent.class)
public interface PhotoComponent {
    void inject(PhotoActivity activity);
}
