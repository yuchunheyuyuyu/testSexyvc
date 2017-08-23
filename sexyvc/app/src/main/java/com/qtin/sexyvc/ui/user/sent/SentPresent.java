package com.qtin.sexyvc.ui.user.sent;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.user.sent.bean.SentBean;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SentPresent extends BasePresenter<SentContract.Model,SentContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public SentPresent(SentContract.Model model, SentContract.View rootView, RxErrorHandler mErrorHandler,
                       AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void queryMySent(int page_size,final long record_id){

        mModel.queryMySent(mModel.getToken(),page_size,record_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (record_id==0)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示下拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (record_id==0)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<ListBean<SentBean>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ListBean<SentBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ListBean<SentBean>> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(record_id,baseEntity.getItems());
                        }
                    }
                });
    }

    public UserInfoEntity getUserInfo() {
        return mModel.getUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
