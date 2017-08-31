package com.qtin.sexyvc.ui.user.message.message;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.user.bean.MsgItems;
import com.qtin.sexyvc.utils.ConstantUtil;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class MessageFragPresent extends BasePresenter<MessageFragContract.Model,MessageFragContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public MessageFragPresent(MessageFragContract.Model model, MessageFragContract.View rootView, RxErrorHandler mErrorHandler,
                              AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void queryMessage(final long id,int page_size){
        mModel.queryMessage(mModel.getToken(),id,page_size)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (id== ConstantUtil.DEFALUT_ID)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示下拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (id==ConstantUtil.DEFALUT_ID)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<MsgItems>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new Subscriber<BaseEntity<MsgItems>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(id==ConstantUtil.DEFALUT_ID){
                            mRootView.showNetErrorView();
                        }else{
                            UiUtils.SnackbarText(UiUtils.getString(R.string.net_error_hint));
                        }
                    }

                    @Override
                    public void onNext(BaseEntity<MsgItems> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public void queryMessageStatus(final long id,int page_size){
        mModel.queryMessage(mModel.getToken(),id,page_size)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .compose(RxUtils.<BaseEntity<MsgItems>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<MsgItems>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<MsgItems> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryStatusSuccess(baseEntity.getItems());
                        }else{
                            //mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    public void changeReadStatus(ChangeReadStatusRequest request,final int position){
        request.setToken(mModel.getToken());
        mModel.changeReadStatus(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        //mRootView.hideLoading();
                    }
                }).subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        //mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.changeStatusSuccess(position);
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
