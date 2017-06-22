package com.qtin.sexyvc.ui.flash.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.flash.FlashActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = FlashModule.class,dependencies = AppComponent.class)
public interface FlashComponent {
    void inject(FlashActivity activity);
}
