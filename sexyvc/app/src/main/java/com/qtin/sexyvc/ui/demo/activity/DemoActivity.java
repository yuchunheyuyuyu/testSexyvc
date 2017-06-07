package com.qtin.sexyvc.ui.demo.activity;

import android.content.Intent;

import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.demo.activity.di.DaggerDemoComponent;
import com.qtin.sexyvc.ui.demo.activity.di.DemoModule;

/**
 * Created by ls on 17/4/26.
 */
public class DemoActivity extends MyBaseActivity<DemoPresent> implements DemoContract.View {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDemoComponent.builder().appComponent(appComponent).demoModule(new DemoModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return 0;
    }

    @Override
    protected void initData() {

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
