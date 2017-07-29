package com.qtin.sexyvc.ui.follow.set;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.request.ChangeContactGroupRequest;
import com.qtin.sexyvc.ui.request.ChangeInvestorGroupRequest;
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
public class SetGroupPresent extends BasePresenter<SetGroupContract.Model,SetGroupContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    private int page = 1;
    private int page_size = Integer.MAX_VALUE;

    @Inject
    public SetGroupPresent(SetGroupContract.Model model, SetGroupContract.View rootView, RxErrorHandler mErrorHandler,
                           AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }
    public void add(final String group_name){
        mModel.addInvestorGroup(mModel.getToken(),group_name)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("处理中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();
                    }
                }).compose(RxUtils.<BaseEntity<CreateGroupEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CreateGroupEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CreateGroupEntity> baseEntity) {

                        if(baseEntity.isSuccess()){
                            mRootView.showMessage("该组已建立");
                            mRootView.addSuccess(baseEntity.getItems().getGroup_id(),group_name);
                        }else{
                            //mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    public void changeGroup(ChangeInvestorGroupRequest request){
        request.setToken(mModel.getToken());
        mModel.changeGroup(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("处理中");
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
                    public void onNext(CodeEntity baseEntity) {
                        //mRootView.showMessage(baseEntity.getErrMsg());
                        if(baseEntity.isSuccess()){
                            mRootView.changeSuccess();
                        }
                    }
                });
    }

    public void changeContactGroup(ChangeContactGroupRequest request){
        request.setToken(mModel.getToken());
        mModel.changeContactGroup(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("处理中");
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
                    public void onNext(CodeEntity baseEntity) {
                        //mRootView.showMessage(baseEntity.getErrMsg());
                        if(baseEntity.isSuccess()){
                            mRootView.changeSuccess();
                        }
                    }
                });
    }

    public void query(long investor_id,long contact_id){
        mModel.queryInvestorGroup(mModel.getToken(),investor_id,contact_id,page,page_size)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("获取数据");
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
                            //mRootView.showMessage(baseEntity.getErrMsg());
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
