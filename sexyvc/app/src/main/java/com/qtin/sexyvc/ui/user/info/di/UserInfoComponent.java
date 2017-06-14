package com.qtin.sexyvc.ui.user.info.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = UserInfoModule.class,dependencies = AppComponent.class)
public interface UserInfoComponent {
    void inject(UserInfoActivity activity);
}
