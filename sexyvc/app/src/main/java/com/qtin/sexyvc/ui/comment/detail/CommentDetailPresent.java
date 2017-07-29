package com.qtin.sexyvc.ui.comment.detail;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentBean;
import com.qtin.sexyvc.utils.ConstantUtil;

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
public class CommentDetailPresent extends BasePresenter<CommentDetailContract.Model,CommentDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public CommentDetailPresent(CommentDetailContract.Model model, CommentDetailContract.View rootView, RxErrorHandler mErrorHandler,
                                AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public UserInfoEntity getUserInfo(){
        return mModel.getUserInfo();
    };

    public void query(long comment_id,final long reply_id){
        mModel.queryCommentDetail(mModel.getToken(),comment_id,15,reply_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (reply_id==0)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();//显示下拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (reply_id==0)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<CommentBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CommentBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CommentBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(reply_id,baseEntity.getItems());
                        }else{
                            if(baseEntity.getErrCode()==40001){
                                mRootView.showNotExistDialog();
                            }else{
                                //mRootView.showMessage(baseEntity.getErrMsg());
                            }
                        }
                    }
                });
    }

    public void reply(final int position, long object_id, long reply_id, final String reply_content){
        mModel.reply(mModel.getToken(), ConstantUtil.OBJECT_TYPE_COMMENT,object_id,reply_id,reply_content)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.dialogWaitLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.dialogWaitDismiss();
                    }
                }).compose(RxUtils.<BaseEntity<ReplyIdBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ReplyIdBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ReplyIdBean> baseEntity) {
                        //mRootView.showMessage(baseEntity.getErrMsg());
                        if(baseEntity.isSuccess()){
                            mRootView.replySuccess(position,baseEntity.getItems().getReply_id(),reply_content);
                        }
                    }
                });
    }

    public void praise(final int position,int object_type, long object_id, int handle_type){
        mModel.praise(mModel.getToken(),object_type,object_id,handle_type)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.dialogWaitLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.dialogWaitDismiss();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.praiseSuccess(position);
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
