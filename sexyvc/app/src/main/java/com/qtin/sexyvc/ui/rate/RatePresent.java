package com.qtin.sexyvc.ui.rate;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.request.RateRequest;

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
public class RatePresent extends BasePresenter<RateContract.Model,RateContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public RatePresent(RateContract.Model model, RateContract.View rootView, RxErrorHandler mErrorHandler,
                       AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void rateInvestor(final RateRequest request){
        request.setToken(mModel.getToken());
        mModel.rateInvestor(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        //mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.showMessage("评分成功");
                            mRootView.rateSuccess(request.getScore());
                        }
                    }
                });
    }

    public void queryNormalQuestion(){
        mModel.queryNormalQuestion()
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mRootView.startLoad("获取数据中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        //mRootView.endLoad();
                    }
                }).compose(RxUtils.<BaseEntity<CommonBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CommonBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CommonBean> baseListEntity) {
                        if(baseListEntity.isSuccess()){
                            mRootView.queryNormalQuestionsSuccess(baseListEntity.getItems());
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
