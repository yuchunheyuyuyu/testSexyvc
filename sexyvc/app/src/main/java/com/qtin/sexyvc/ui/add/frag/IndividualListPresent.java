package com.qtin.sexyvc.ui.add.frag;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;

import java.util.List;

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
@FragmentScope
public class IndividualListPresent extends BasePresenter<IndividualListContract.Model,IndividualListContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public IndividualListPresent(IndividualListContract.Model model, IndividualListContract.View rootView, RxErrorHandler mErrorHandler,
                                 AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public UserInfoEntity getUserInfo(){
        return mModel.getUserInfo();
    };

    public List<LastBrowerBean> queryLastBrowers(){
       return mModel.queryLastBrowers();
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
                        }else{
                            mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    public void queryDetail(long investor_id,long comment_id){
        mModel.queryInvestorDetail(mModel.getToken(),investor_id,comment_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("获取数据中");//显示上拉刷新的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<CallBackBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CallBackBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CallBackBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryDetailSuccess(baseEntity.getItems());
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
