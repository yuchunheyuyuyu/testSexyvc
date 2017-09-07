package com.qtin.sexyvc.ui.mycase.add;

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
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
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
public class AddCasePresent extends BasePresenter<AddCaseContract.Model,AddCaseContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    private UploadManager uploadManager;

    @Inject
    public AddCasePresent(AddCaseContract.Model model, AddCaseContract.View rootView, RxErrorHandler mErrorHandler,
                          AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
        uploadManager=new UploadManager();
    }

    public void createCase(String case_name,String case_logo){
        mModel.addMyCase(mModel.getToken(),case_name,case_logo)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showProgress("正在提交");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideProgress();
                    }
                }).compose(RxUtils.<BaseEntity<CaseBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<CaseBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<CaseBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.onCreateSuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public void getQiNiuToken(final String path){
        mModel.getQiniuToken(0)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showProgress("上传logo");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<QiniuTokenEntity>> bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseEntity<QiniuTokenEntity>>() {
                    @Override
                    public void onCompleted() {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideProgress();
                    }

                    @Override
                    public void onNext(BaseEntity<QiniuTokenEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            upload(path,baseEntity.getItems().getQiniu_token());
                        }else{
                            //mRootView.showMessage(baseEntity.getErrMsg());
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
                mRootView.hideProgress();
                mRootView.uploadPhotoSuccess(key);
            }

        }, uploadOptions);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
