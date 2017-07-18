package com.qtin.sexyvc.ui.create.investor;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.IdBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.request.CreateInvestorRequest;

import org.json.JSONObject;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class CreateInvestorPresent extends BasePresenter<CreateInvestorContract.Model,CreateInvestorContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private UploadManager uploadManager;

    @Inject
    public CreateInvestorPresent(CreateInvestorContract.Model model, CreateInvestorContract.View rootView, RxErrorHandler mErrorHandler,
                                 AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
        uploadManager = new UploadManager();
    }

    public void createInvestor(CreateInvestorRequest request){
        request.setToken(mModel.getToken());
        mModel.createInvestor(request)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("提交中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();

                    }
                }).compose(RxUtils.<BaseEntity<IdBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<IdBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<IdBean> codeEntity) {
                        mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.onCreateSuccess(codeEntity.getItems().getId());
                        }
                    }
                });
    }

    public void upload(String path, String token) {
        String key = mModel.getToken()+System.currentTimeMillis() + ".png";
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key,final double percent) {
                        //mRootView.showProgress((int)(percent*100));
                    }
                }, null);
        uploadManager.put(path, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                mRootView.uploadAvatarSuccess(key);
                mRootView.endRefresh();
            }

        }, uploadOptions);
    }

    public void getQiNiuToken(final String path){
        mModel.getQiniuToken(0)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("上传头像中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<QiniuTokenEntity>> bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseEntity<QiniuTokenEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.endRefresh();
                    }

                    @Override
                    public void onNext(BaseEntity<QiniuTokenEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            upload(path,baseEntity.getItems().getQiniu_token());
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
