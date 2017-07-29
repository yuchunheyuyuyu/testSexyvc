package com.qtin.sexyvc.ui.login.password.forget;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ForgetPresent extends BasePresenter<ForgetContract.Model,ForgetContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public ForgetPresent(ForgetContract.Model model, ForgetContract.View rootView, RxErrorHandler mErrorHandler,
                         AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    /**
     * 获取验证码
     */
    public void getVertifyCode(String mobile){
        mModel.getVertifyCode("",mobile,"reset")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<CodeEntity> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        if(codeEntity.isSuccess()){

                        }
                    }
                });
    }

    public void validateCode(String mobile, String code_value){
        mModel.validateCode(mobile,"reset",code_value)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<BindEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<BindEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<BindEntity> codeEntity) {
                        //mRootView.showMessage(codeEntity.getErrMsg());
                        if(codeEntity.isSuccess()){
                            mRootView.validateSuccess();
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
