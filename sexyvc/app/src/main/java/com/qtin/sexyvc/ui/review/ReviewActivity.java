package com.qtin.sexyvc.ui.review;

import android.content.Intent;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.review.di.DaggerReviewComponent;
import com.qtin.sexyvc.ui.review.di.ReviewModule;

/**
 * Created by ls on 17/4/26.
 */
public class ReviewActivity extends MyBaseActivity<ReviewPresent> implements ReviewContract.View {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerReviewComponent.builder().appComponent(appComponent).reviewModule(new ReviewModule(this)).build().inject(this);
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
