package com.qtin.sexyvc.ui.subject.detail;

import android.content.Intent;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.subject.detail.di.DaggerSubjectDetailComponent;
import com.qtin.sexyvc.ui.subject.detail.di.SubjectDetailModule;

/**
 * Created by ls on 17/4/26.
 */
public class SubjectDetailActivity extends MyBaseActivity<SubjectDetailPresent> implements SubjectDetailContract.View {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectDetailComponent.builder().appComponent(appComponent).subjectDetailModule(new SubjectDetailModule(this)).build().inject(this);
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
