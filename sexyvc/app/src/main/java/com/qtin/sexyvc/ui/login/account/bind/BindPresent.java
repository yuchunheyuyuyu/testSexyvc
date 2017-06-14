package com.qtin.sexyvc.ui.login.account.bind;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.StringUtil;
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
public class BindPresent extends BasePresenter<BindContract.Model,BindContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public BindPresent(BindContract.Model model, BindContract.View rootView, RxErrorHandler mErrorHandler,
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
        mModel.getVertifyCode(mobile,"bind")
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

    /**
     * 绑定手机
     */
    public void bindMobile(String token, String mobile){
        mModel.bindMobile(token,mobile,"")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<CodeEntity> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CodeEntity codeEntity) {
                        if(codeEntity.isSuccess()){
                            mModel.changeBindStatus(1);
                            mRootView.notNeedSetPassword();
                        }
                    }
                });
    }

    public void validateCode(final String mobile, String code_value){
        mModel.validateCode(mobile,"bind",code_value)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<BindEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<BindEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<BindEntity> baseEntity) {
                        if(baseEntity!=null){
                            if(baseEntity.isSuccess()){
                                if(baseEntity.getItems()!=null){
                                    if(baseEntity.getItems().getRegister()==1){
                                        bindMobile(mModel.getToken(),mobile);
                                    }else{
                                        mRootView.gotoSetPassword();
                                    }
                                }
                            }else{
                                mRootView.showMessage(StringUtil.formatString(baseEntity.getErrMsg()));
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
