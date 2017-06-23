package com.qtin.sexyvc.ui.main.fragconcern;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class ConcernPresent extends BasePresenter<ConcernContract.Model,ConcernContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public ConcernPresent(ConcernContract.Model model, ConcernContract.View rootView, RxErrorHandler mErrorHandler,
                          AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void edit(final int position,long group_id,final String group_name,final int status){
        mModel.updateInvestorGroup(mModel.getToken(),group_id,group_name,status)
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
                        mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            if(status==0){
                                mRootView.deleteSuccess(position);
                            }else{
                                mRootView.editSuccess(position,group_name);
                            }
                        }
                    }
                });
    }

    public void add(final String group_name){
        mModel.addInvestorGroup(mModel.getToken(),group_name)
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
                }).compose(RxUtils.<BaseEntity<CreateGroupEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateGroupEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateGroupEntity> baseEntity) {
                        mRootView.showMessage(baseEntity.getErrMsg());
                        if(baseEntity.isSuccess()){
                            mRootView.addSuccess(baseEntity.getItems().getGroup_id(),group_name);
                        }
                    }
                });
    }

    public void query(int page,int page_size){
        mModel.queryInvestorGroup(mModel.getToken(),0,page,page_size)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();
                    }
                }).compose(RxUtils.<BaseEntity<GroupEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<GroupEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<GroupEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            GroupEntity groupEntity=baseEntity.getItems();
                            if(groupEntity!=null){
                                mRootView.querySuccess(groupEntity);
                            }
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
