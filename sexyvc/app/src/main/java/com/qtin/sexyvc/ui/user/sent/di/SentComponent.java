package com.qtin.sexyvc.ui.user.sent.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.sent.SentActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = SentModule.class,dependencies = AppComponent.class)
public interface SentComponent {
    void inject(SentActivity activity);
}
