package com.qtin.sexyvc.ui.web.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.web.WebActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = WebModule.class,dependencies = AppComponent.class)
public interface WebComponent {
    void inject(WebActivity activity);
}
