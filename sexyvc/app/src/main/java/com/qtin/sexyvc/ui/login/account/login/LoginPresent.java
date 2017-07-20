package com.qtin.sexyvc.ui.login.account.login;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

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
public class LoginPresent extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public LoginPresent(LoginContract.Model model, LoginContract.View rootView, RxErrorHandler mErrorHandler,
                        AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void login(String username, String password, String device_token) {
        mModel.login(1, username, 1, password, device_token)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<UserEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> userEntityBaseEntity) {
                        if (userEntityBaseEntity != null) {
                            if (userEntityBaseEntity.isSuccess()) {
                                if (userEntityBaseEntity.getItems() != null) {
                                    userEntityBaseEntity.getItems().setBind_mobile(1);
                                    mModel.saveUser(userEntityBaseEntity.getItems());

                                    UserInfoEntity entity=new UserInfoEntity();

                                    entity.setToken(userEntityBaseEntity.getItems().getU_token());
                                    entity.setHas_project(userEntityBaseEntity.getItems().getHas_project());
                                    entity.setBusiness_card(userEntityBaseEntity.getItems().getBusiness_card());
                                    entity.setU_nickname(userEntityBaseEntity.getItems().getU_nickname());

                                    entity.setU_gender(userEntityBaseEntity.getItems().getU_gender());
                                    entity.setU_avatar(userEntityBaseEntity.getItems().getU_avatar());
                                    entity.setU_signature(userEntityBaseEntity.getItems().getU_signature());
                                    entity.setU_phone(userEntityBaseEntity.getItems().getU_phone());

                                    entity.setU_email(userEntityBaseEntity.getItems().getU_email());
                                    entity.setU_backup_phone(userEntityBaseEntity.getItems().getU_backup_phone());
                                    entity.setU_backup_email(userEntityBaseEntity.getItems().getU_backup_email());
                                    entity.setU_company(userEntityBaseEntity.getItems().getU_company());

                                    entity.setU_title(userEntityBaseEntity.getItems().getU_title());
                                    entity.setU_auth_state(userEntityBaseEntity.getItems().getU_auth_state());
                                    entity.setU_auth_type(userEntityBaseEntity.getItems().getU_auth_type());

                                    mModel.saveUsrInfo(entity);
                                    mRootView.loginSuccess();
                                }
                            } else {
                                mRootView.showMessage(userEntityBaseEntity.getErrMsg());
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
        mAppManager = null;
        mApplication = null;
    }
}
