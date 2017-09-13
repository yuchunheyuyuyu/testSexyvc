package com.qtin.sexyvc.ui.follow.list;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.follow.list.bean.FollowedFundBean;
import com.qtin.sexyvc.utils.ConstantUtil;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ConcernListPresent extends BasePresenter<ConcernListContract.Model,ConcernListContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public ConcernListPresent(ConcernListContract.Model model, ConcernListContract.View rootView, RxErrorHandler mErrorHandler,
                              AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void query(long group_id, final int page, int page_size){
        mModel.queryGroupDetail(mModel.getToken(),group_id,page,page_size)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(page==1){
                            mRootView.showLoading();
                        }else{
                            mRootView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(page==1){
                            mRootView.hideLoading();
                        }else{
                            mRootView.endLoadMore();
                        }
                    }
                }).compose(RxUtils.<BaseEntity<ConcernEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ConcernEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ConcernEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public void queryFundDetail(long group_id, final int page, int page_size){
        mModel.queryFundDetail(mModel.getToken(),group_id,page,page_size, ConstantUtil.OBJECT_TYPE_FUND)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(page==1){
                            mRootView.showLoading();
                        }else{
                            mRootView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(page==1){
                            mRootView.hideLoading();
                        }else{
                            mRootView.endLoadMore();
                        }
                    }
                }).compose(RxUtils.<BaseEntity<ListBean<FollowedFundBean>>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ListBean<FollowedFundBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ListBean<FollowedFundBean>> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryFundSuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
