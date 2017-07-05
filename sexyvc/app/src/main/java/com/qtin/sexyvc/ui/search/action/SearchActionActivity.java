package com.qtin.sexyvc.ui.search.action;

import android.content.Intent;

import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.search.action.di.DaggerSearchActionComponent;
import com.qtin.sexyvc.ui.search.action.di.SearchActionModule;

/**
 * Created by ls on 17/4/26.
 */
public class SearchActionActivity extends MyBaseActivity<SearchActionPresent> implements SearchActionContract.View {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchActionComponent.builder().appComponent(appComponent).searchActionModule(new SearchActionModule(this)).build().inject(this);
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
