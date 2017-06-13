package com.qtin.sexyvc.ui.login.password.set;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

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
public class SetPasswordPresent extends BasePresenter<SetPasswordContract.Model,SetPasswordContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public SetPasswordPresent(SetPasswordContract.Model model, SetPasswordContract.View rootView, RxErrorHandler mErrorHandler,
                              AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void doRegister(RegisterRequestEntity entity){
        mModel.doRegister(entity)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<UserEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> userEntityBaseEntity) {
                        if(userEntityBaseEntity!=null&&userEntityBaseEntity.isSuccess()){
                            mModel.saveUser(userEntityBaseEntity.getItems());
                            mRootView.rigisterSuccess();
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
