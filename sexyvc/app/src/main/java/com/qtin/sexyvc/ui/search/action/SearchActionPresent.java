package com.qtin.sexyvc.ui.search.action;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.qtin.sexyvc.ui.bean.KeyWordBean;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SearchActionPresent extends BasePresenter<SearchActionContract.Model,SearchActionContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public SearchActionPresent(SearchActionContract.Model model, SearchActionContract.View rootView, RxErrorHandler mErrorHandler,
                               AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void getKeys(){
        mRootView.querySuccess(mModel.getKeys());
    }

    public void insertKeys(KeyWordBean bean){
        mModel.insertKey(bean);
    }

    public void delete(){
        mModel.delete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
