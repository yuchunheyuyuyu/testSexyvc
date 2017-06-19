package com.qtin.sexyvc.ui.user.project.my;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class MyProjectPresent extends BasePresenter<MyProjectContract.Model,MyProjectContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public MyProjectPresent(MyProjectContract.Model model, MyProjectContract.View rootView, RxErrorHandler mErrorHandler,
                            AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
