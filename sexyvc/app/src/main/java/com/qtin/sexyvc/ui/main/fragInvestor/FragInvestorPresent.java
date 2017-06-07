package com.qtin.sexyvc.ui.main.fragInvestor;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
