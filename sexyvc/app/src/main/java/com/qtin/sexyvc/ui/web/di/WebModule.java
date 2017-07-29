package com.qtin.sexyvc.ui.web.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.web.WebContract;
import com.qtin.sexyvc.ui.web.WebModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class WebModule {
    private WebContract.View view;

    public WebModule(WebContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    WebContract.Model provideDemoModel(WebModel model){
        return model;
    }
}
