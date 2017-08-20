package com.qtin.sexyvc.ui.login.account.create;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.RegisterStatusBean;
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
public class CreatePresent extends BasePresenter<CreateContract.Model,CreateContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public CreatePresent(CreateContract.Model model, CreateContract.View rootView, RxErrorHandler mErrorHandler,
                         AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public boolean isLogin(){
        return mModel.isLogin();
    }

    public void checkRegisterStatus(String mobile){
        mModel.checkRegisterStatus(mobile)
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.startLoad("验证中");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.endLoad();
                    }
                }).compose(RxUtils.<BaseEntity<RegisterStatusBean>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<RegisterStatusBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<RegisterStatusBean> codeEntity) {
                        if(codeEntity.isSuccess()){
                            mRootView.checkRegisterSuccess(codeEntity.getItems().getHas_register());
                        }
                    }
                });
    }


    public void doRegister(final RegisterRequestEntity requestEntity){
        requestEntity.setClient_type(1);
        mModel.doRegister(requestEntity)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<UserEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> userEntityBaseEntity) {
                        if(userEntityBaseEntity!=null){
                            if(userEntityBaseEntity.isSuccess()&&userEntityBaseEntity.getItems()!=null){
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


                                if(userEntityBaseEntity.getItems().getBind_mobile()==1){
                                    mRootView.notNeedBind();
                                }else{
                                    mRootView.gotoBind(requestEntity.getAccount_type());
                                }
                            }else{
                                //mRootView.showMessage(AppStringUtil.formatString(userEntityBaseEntity.getErrMsg()));
                            }
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
