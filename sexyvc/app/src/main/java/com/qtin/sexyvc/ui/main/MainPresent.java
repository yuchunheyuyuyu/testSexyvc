package com.qtin.sexyvc.ui.main;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class MainPresent extends BasePresenter<MainContract.Model,MainContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public MainPresent(MainContract.Model model, MainContract.View rootView, RxErrorHandler mErrorHandler,
                       AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public UserInfoEntity getUserInfo(){
        return mModel.getUserInfo();
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
