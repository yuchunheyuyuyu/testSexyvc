package com.qtin.sexyvc.ui.main.fragmine;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.InfluencyBean;
import com.qtin.sexyvc.ui.bean.UnReadBean;
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
@FragmentScope
public class FragMinePresent extends BasePresenter<FragMineContract.Model,FragMineContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public FragMinePresent(FragMineContract.Model model, FragMineContract.View rootView,
                           RxErrorHandler mErrorHandler, AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void getUserInfo(){
        mModel.getUserInfo(mModel.getToken())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<UserInfoEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserInfoEntity> baseEntity) {
                        if(baseEntity!=null){
                            if(baseEntity.isSuccess()){
                                if(baseEntity.getItems()!=null){
                                    mRootView.requestSuccess(baseEntity.getItems());
                                    baseEntity.getItems().setToken(mModel.getToken());
                                    mModel.saveUsrInfo(baseEntity.getItems());
                                }
                            }else{
                                //mRootView.showMessage(AppStringUtil.formatString(baseEntity.getErrMsg()));
                            }
                        }
                    }
                });
    }

    public void queryInfluency(){
        mModel.queryInfluency(mModel.getToken())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<InfluencyBean>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<InfluencyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<InfluencyBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.queryInfluencySuccess(baseEntity.getItems());
                        }
                    }
                });
    }

    public void queryUnRead(){
        mModel.queryUnRead(mModel.getToken())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<UnReadBean>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UnReadBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UnReadBean> baseEntity) {
                        if(baseEntity!=null){
                            if(baseEntity.isSuccess()){
                                mRootView.queryUnReadSuccess(baseEntity.getItems());
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
