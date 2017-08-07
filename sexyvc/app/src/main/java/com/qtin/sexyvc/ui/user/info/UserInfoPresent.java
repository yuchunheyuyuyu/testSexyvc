package com.qtin.sexyvc.ui.user.info;

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
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

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
public class UserInfoPresent extends BasePresenter<UserInfoContract.Model, UserInfoContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private UploadManager uploadManager;

    @Inject
    public UserInfoPresent(UserInfoContract.Model model, UserInfoContract.View rootView, RxErrorHandler mErrorHandler,
                           AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
        uploadManager = new UploadManager();
    }

    public void editSex(final int u_gender) {
        mModel.editSex(mModel.getToken(), u_gender).subscribeOn(Schedulers.io())
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
                        mRootView.hideLoading();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        //mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            //mRootView.showMessage("性别修改成功");
                            mRootView.editSexSuccess(u_gender);
                        }
                    }
                });
    }

    public void uploadVertifyPhoto(final String img_url) {
        mModel.uploadVertifyPhoto(mModel.getToken(), img_url)
                .subscribeOn(Schedulers.io())
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

                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new Observer<CodeEntity>() {
                    @Override
                    public void onCompleted() {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        if(codeEntity.isSuccess()){
                            mRootView.showMessage("上传成功");
                            mRootView.uploadSuccess(img_url);
                        }
                    }
                });
    }

    public void editAvatar(final String u_avatar) {
        mModel.editAvatar(mModel.getToken(), u_avatar)
                .subscribeOn(Schedulers.io())
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
                        mRootView.hideLoading();
                    }
                }).compose(RxUtils.<CodeEntity>bindToLifecycle(mRootView))
                .subscribe(new Observer<CodeEntity>() {
                    @Override
                    public void onCompleted() {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        if(codeEntity.isSuccess()){
                            //mRootView.showMessage("头像修改成功");
                            mRootView.editAvatarSuccess(u_avatar);
                        }
                    }
                });
    }

    public void upload(String path, String token,final boolean isUpdateAvatar) {
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
                if(isUpdateAvatar){
                    editAvatar(key);
                }else{
                    uploadVertifyPhoto(key);
                }
            }

        }, uploadOptions);
    }

    public void getQiNiuToken(final String path,final boolean isUpdateAvatar){
        mModel.getQiniuToken(0)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
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
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onNext(BaseEntity<QiniuTokenEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            upload(path,baseEntity.getItems().getQiniu_token(),isUpdateAvatar);
                        }
                    }
                });
    }

    public void saveUsrInfo(UserInfoEntity entity){
        mModel.saveUsrInfo(entity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
        mAppManager = null;
        mApplication = null;
    }
}
