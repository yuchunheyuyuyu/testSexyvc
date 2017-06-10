package com.qtin.sexyvc.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.mvp.contract.LoginContarct;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/3/6.
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContarct.Model,LoginContarct.View> {
    private RxErrorHandler errorHandler;

    @Inject
    public LoginPresenter(LoginContarct.Model model, LoginContarct.View rootView, RxErrorHandler errorHandler) {
        super(model, rootView);
        this.errorHandler = errorHandler;
    }

    public void register(RegisterRequestEntity entity){
        mModel.register(entity)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseListEntity<LoginEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseListEntity<LoginEntity>>(errorHandler) {
                    @Override
                    public void onNext(BaseListEntity<LoginEntity> loginEntityBaseEntity) {
                        mRootView.showMessage(loginEntityBaseEntity.getErrMsg());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        errorHandler=null;
    }
}
