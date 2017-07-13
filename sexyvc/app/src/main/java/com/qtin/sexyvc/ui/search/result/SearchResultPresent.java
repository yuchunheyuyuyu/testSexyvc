package com.qtin.sexyvc.ui.search.result;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.FundBackEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.InvestorRequest;

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
public class SearchResultPresent extends BasePresenter<SearchResultContract.Model,SearchResultContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public SearchResultPresent(SearchResultContract.Model model, SearchResultContract.View rootView, RxErrorHandler mErrorHandler,
                               AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void getType(String type_key, final int type){
        mModel.getType(type_key)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<Typebean> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Typebean>(mErrorHandler) {
                    @Override
                    public void onNext(Typebean baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.requestTypeBack(type,baseEntity.getItems());
                        }
                    }
                });
    }

    public void queryFunds(final InvestorRequest request){
        request.setToken(mModel.getToken());
        mModel.queryFunds(request)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (request.getPage()==1)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示下拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (request.getPage()==1)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<FundBackEntity>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<FundBackEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<FundBackEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryFundSuccess(baseEntity.getItems());
                        }else{
                            mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    public void queryInvestors(final InvestorRequest request){
        request.setToken(mModel.getToken());
        mModel.queryInvestors(request)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (request.getPage()==1)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示下拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (request.getPage()==1)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<InvestorBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<InvestorBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<InvestorBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryInvestorSuccess(baseEntity.getItems());
                        }else{
                            mRootView.showMessage(baseEntity.getErrMsg());
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