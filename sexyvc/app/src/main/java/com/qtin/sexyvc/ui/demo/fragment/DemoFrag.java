package com.qtin.sexyvc.ui.demo.fragment;

import android.content.Intent;

import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;

/**
 * Created by ls on 17/4/26.
 */

public class DemoFrag extends MyBaseFragment<DemoPresent> implements DemoContract.View {
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected int setContentViewId() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
