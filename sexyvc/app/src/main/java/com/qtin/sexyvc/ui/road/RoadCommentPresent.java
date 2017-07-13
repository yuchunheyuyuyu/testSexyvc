package com.qtin.sexyvc.ui.road;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.bean.RoadRequest;

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
public class RoadCommentPresent extends BasePresenter<RoadCommentContract.Model,RoadCommentContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public RoadCommentPresent(RoadCommentContract.Model model, RoadCommentContract.View rootView, RxErrorHandler mErrorHandler,
                              AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void queryRoadQuestion(){
        mModel.queryRoadQuestion(mModel.getToken())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startLoad("获取数据中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endLoad();
                    }
                }).compose(RxUtils.<BaseListEntity<QuestionBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseListEntity<QuestionBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseListEntity<QuestionBean> listEntity) {
                        if(listEntity.isSuccess()){
                            mRootView.querySuccess(listEntity);
                        }else{
                            mRootView.showMessage(listEntity.getErrMsg());
                            mRootView.queryFail();
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
                }).compose(RxUtils.<BaseListEntity<String>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseListEntity<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseListEntity<String> baseListEntity) {
                        if(baseListEntity.isSuccess()){
                            mRootView.queryNormalQuestionsSuccess(baseListEntity.getItems());
                        }
                    }
                });
    }

    public void uploadAnswers(RoadRequest request){
        request.setToken(mModel.getToken());
        mModel.uploadAnswers(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startLoad("提交答案中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endLoad();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.onUploadAnswersSuccess();
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