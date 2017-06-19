package com.qtin.sexyvc.ui.main.fragInvestor;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBackBean;
import com.qtin.sexyvc.utils.LocalFileReader;

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
public class FragInvestorPresent extends BasePresenter<FragInvestorContract.Model,FragInvestorContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public FragInvestorPresent(FragInvestorContract.Model model, FragInvestorContract.View rootView,
                               RxErrorHandler mErrorHandler, AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void getInvestorData(){
        new LocalFileReader().readAssets(mRootView.getContext(), "InventorData.json", new LocalFileReader.ReadListener() {
            @Override
            public void complete(String result) {
                InvestorBackBean investorBackBean=new Gson().fromJson(result,InvestorBackBean.class);
                mRootView.dataCallback(investorBackBean.getItems().getList());
            }
        });
    }

    public void getType(String type_key, final int type){
        mModel.getType(type_key)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseListEntity<FilterEntity>> bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseListEntity<FilterEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseListEntity<FilterEntity> baseEntity) {
                        if(baseEntity.isSuccess()){
                            mRootView.requestTypeBack(type,baseEntity.getItems());
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
