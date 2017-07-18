package com.qtin.sexyvc.ui.add.search;

import android.content.Intent;

import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.add.search.di.DaggerSearchObjectComponent;
import com.qtin.sexyvc.ui.add.search.di.SearchObjectModule;

/**
 * Created by ls on 17/4/26.
 */
public class SearchObjectActivity extends MyBaseActivity<SearchObjectPresent> implements SearchObjectContract.View {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchObjectComponent.builder().appComponent(appComponent).searchObjectModule(new SearchObjectModule(this)).build().inject(this);
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
