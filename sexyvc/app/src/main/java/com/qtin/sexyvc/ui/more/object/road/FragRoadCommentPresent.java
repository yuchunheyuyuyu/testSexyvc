package com.qtin.sexyvc.ui.more.object.road;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.investor.bean.CommentListBean;
import com.qtin.sexyvc.utils.ConstantUtil;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class FragRoadCommentPresent extends BasePresenter<FragRoadCommentContract.Model,FragRoadCommentContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public FragRoadCommentPresent(FragRoadCommentContract.Model model, FragRoadCommentContract.View rootView, RxErrorHandler mErrorHandler,
                                  AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void queryFundComment(long fund_id, String data_type, String page_type, int page_size,final long last_id,int auth_state){
        mModel.queryFundComment(mModel.getToken(),fund_id,data_type,page_type,page_size,last_id,auth_state)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.showLoading();//显示上拉刷新的进度条
                        }else{
                            mRootView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        }else{
                            mRootView.endLoadMore();
                        }
                    }
                })
                .compose(RxUtils.<BaseEntity<CommentListBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new Subscriber<BaseEntity<CommentListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.showNetErrorView();
                        }else{
                            UiUtils.SnackbarText(UiUtils.getString(R.string.net_error_hint));
                        }
                    }

                    @Override
                    public void onNext(BaseEntity<CommentListBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public void queryInvestorComment(long investor_id, String data_type, String page_type, int page_size,final long last_id,int auth_state){
        mModel.queryInvestorComment(mModel.getToken(),investor_id,data_type,page_type,page_size,last_id,auth_state)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.showLoading();//显示上拉刷新的进度条
                        }else{
                            mRootView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        }else{
                            mRootView.endLoadMore();
                        }
                    }
                })
                .compose(RxUtils.<BaseEntity<CommentListBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new Subscriber<BaseEntity<CommentListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(last_id== ConstantUtil.DEFALUT_ID){
                            mRootView.showNetErrorView();
                        }else{
                            UiUtils.SnackbarText(UiUtils.getString(R.string.net_error_hint));
                        }
                    }

                    @Override
                    public void onNext(BaseEntity<CommentListBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.querySuccess(baseEntity.getItems());
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
