package com.qtin.sexyvc.ui.follow.detail;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ContactBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
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
public class ConcernDetailPresent extends BasePresenter<ConcernDetailContract.Model,ConcernDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public ConcernDetailPresent(ConcernDetailContract.Model model, ConcernDetailContract.View rootView, RxErrorHandler mErrorHandler,
                                AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void query(long contact_id){
        mModel.queryContactDetail(mModel.getToken(),contact_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();//显示上拉刷新的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<ContactBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ContactBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ContactBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
                        }else{
                            //mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    public void cancleFollow(final long investor_id){
        mModel.unFollowInvestor(mModel.getToken(),investor_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("正在提交");//显示上拉刷新的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity baseEntity) {
                        //mRootView.showMessage(baseEntity.getErrMsg());
                        if(baseEntity.isSuccess()){
                            mRootView.cancleSuccess(investor_id);
                        }
                    }
                });
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
