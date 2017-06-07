package com.qtin.sexyvc.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.qtin.sexyvc.mvp.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by ls on 17/2/27.
 */

public class MainPresent extends BasePresenter<MainContract.Model,MainContract.View> {

    @Inject
    public MainPresent(MainContract.Model model,MainContract.View rootView){
        super(model, rootView);
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
