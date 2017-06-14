package com.qtin.sexyvc.ui.user.info.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.user.info.UserInfoContract;
import com.qtin.sexyvc.ui.user.info.UserInfoModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class UserInfoModule {
    private UserInfoContract.View view;

    public UserInfoModule(UserInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.Model provideDemoModel(UserInfoModel model){
        return model;
    }
}
