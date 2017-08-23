package com.qtin.sexyvc.ui.login.register;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class RegisterPresent extends BasePresenter<RegisterContract.Model,RegisterContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public RegisterPresent(RegisterContract.Model model, RegisterContract.View rootView, RxErrorHandler mErrorHandler,
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
        mModel.getVertifyCode("",mobile,"register")
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
        mModel.validateCode(mobile,"register",code_value)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("正在验证");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();
                    }
                })
                .compose(RxUtils.<BaseEntity<BindEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<BindEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<BindEntity> codeEntity) {
                        if(codeEntity.isSuccess()){
                            mRootView.validateSuccess();
                        }
                    }
                });
    }

    public void doRegister(RegisterRequestEntity requestEntity){
        requestEntity.setClient_type(1);
        mModel.doRegister(requestEntity)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startRefresh("正在注册");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endRefresh();
                    }
                })
                .compose(RxUtils.<BaseEntity<UserEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> userEntityBaseEntity) {
                        if(userEntityBaseEntity.isSuccess()){

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

                            mRootView.registerSuccess(entity);
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
