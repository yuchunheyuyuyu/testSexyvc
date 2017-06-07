package com.qtin.sexyvc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.mvp.contract.MainContract;
import com.qtin.sexyvc.mvp.model.MainModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/2/27.
 */
@Module
public class MainModule {
    private MainContract.View view;
    /**
     * 构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     * @param view
     */
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView(){
        return view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model){
        return model;
    }
}
