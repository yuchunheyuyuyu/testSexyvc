package com.qtin.sexyvc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.mvp.contract.RefreshContarct;
import com.qtin.sexyvc.mvp.model.RefreshModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/2/28.
 */
@Module
public class RefreshModule {
    private RefreshContarct.View view;

    public RefreshModule(RefreshContarct.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    RefreshContarct.View provideRefreshView(){
        return view;
    }

    @Provides
    @ActivityScope
    RefreshContarct.Model provideRefreshModel(RefreshModel model){
        return model;
    }
}
