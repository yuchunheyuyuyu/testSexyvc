package com.qtin.sexyvc.ui.mycase;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.DeleteCaseRequest;
import com.qtin.sexyvc.ui.bean.ListBean;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class MyCasePresent extends BasePresenter<MyCaseContract.Model,MyCaseContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public MyCasePresent(MyCaseContract.Model model, MyCaseContract.View rootView, RxErrorHandler mErrorHandler,
                         AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void deleteCase(final DeleteCaseRequest request){
        request.setToken(mModel.getToken());
        mModel.deleteCase(request)
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
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        if(codeEntity.isSuccess()){
                            mRootView.deleteSuccess(request.getCase_ids().get(0));
                        }
                    }
                });
    }

    public void queryMyCase(){
        mModel.queryMyCase(mModel.getToken())
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
                }).compose(RxUtils.<BaseEntity<ListBean<CaseBean>>>bindToLifecycle(mRootView))
                .subscribe(new Subscriber<BaseEntity<ListBean<CaseBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.showNetErrorView();
                    }

                    @Override
                    public void onNext(BaseEntity<ListBean<CaseBean>> listBeanBaseEntity) {
                        if(listBeanBaseEntity.isSuccess()){
                            if(listBeanBaseEntity.getItems()!=null&&listBeanBaseEntity.getItems().getList()!=null){
                                if(listBeanBaseEntity.getItems().getList().isEmpty()){
                                    mRootView.showEmptyView();
                                }else{
                                    mRootView.showContentView();
                                    mRootView.querySuccess(listBeanBaseEntity.getItems());
                                }
                            }
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
