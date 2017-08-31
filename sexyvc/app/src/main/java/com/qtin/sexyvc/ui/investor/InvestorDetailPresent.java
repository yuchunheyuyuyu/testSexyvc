package com.qtin.sexyvc.ui.investor;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

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
public class InvestorDetailPresent extends BasePresenter<InvestorDetailContract.Model,InvestorDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public InvestorDetailPresent(InvestorDetailContract.Model model, InvestorDetailContract.View rootView, RxErrorHandler mErrorHandler,
                                 AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void insertLastBrower(LastBrowerBean bean){
        mModel.insertLastBrower(bean);
    }

    public void cancleFollow(final long investor_id){
        mModel.unFollowInvestor(mModel.getToken(),investor_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("处理中...");//显示上拉刷新的进度条
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

    public void followInvestor(FollowRequest entity){
        entity.setToken(mModel.getToken());
        mModel.followInvestor(entity)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("处理中...");//显示上拉刷新的进度条
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
                            mRootView.followSuccess();
                        }
                    }
                });
    }

    public void query(long investor_id,long comment_id,int page_size,int auth_state){
        mModel.queryInvestorDetail(mModel.getToken(),investor_id,comment_id,page_size,auth_state)
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
                .compose(RxUtils.<BaseEntity<CallBackBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CallBackBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CallBackBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public UserInfoEntity getUserInfo(){
        return mModel.getUserInfo();
    };

    public void updateProjectState(){
        mModel.updateProjectState();
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

    public void createProject(final ProjectBean bean){
        bean.setToken(mModel.getToken());
        mModel.createProject(bean)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("正在提交");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        //mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.showMessage("创建成功");
                            updateProjectState();
                            mRootView.onCreateSuccess(bean);
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
