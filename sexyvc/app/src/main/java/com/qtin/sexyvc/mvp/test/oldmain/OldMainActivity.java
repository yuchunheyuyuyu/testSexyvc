package com.qtin.sexyvc.mvp.test.oldmain;

import android.content.Intent;
import android.view.View;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.di.component.DaggerMainComponent;
import com.qtin.sexyvc.di.module.MainModule;
import com.qtin.sexyvc.mvp.contract.MainContract;
import com.qtin.sexyvc.mvp.presenter.MainPresent;
import com.qtin.sexyvc.mvp.test.anim.AnimActivity;
import com.qtin.sexyvc.mvp.test.progress.ProgressActivity;
import com.qtin.sexyvc.mvp.test.share.ShareActivity;
import com.qtin.sexyvc.mvp.test.upload.UploadActivity;
import com.qtin.sexyvc.mvp.test.user.UserActivity;
import com.qtin.sexyvc.ui.login.account.login.LoginActivity;
import com.tbruyelle.rxpermissions.RxPermissions;
import butterknife.OnClick;

/**
 * Created by ls on 17/2/27.
 */

public class OldMainActivity extends MyBaseActivity<MainPresent> implements MainContract.View {

    private RxPermissions mRxPermissions;


    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_main_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void gotoGithub() {
        Intent intent=new Intent(this, UserActivity.class);
        startActivity(intent);
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
        finish();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions=new RxPermissions(this);
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.btnUserList, R.id.btnRatingStar, R.id.btnShare, R.id.btnLogin,R.id.btnRefresh,
            R.id.btnUpload,R.id.btnProgress,R.id.btnAnim,R.id.btnBugly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUserList:
                gotoGithub();
                break;
            case R.id.btnUpload:
                Intent intent2=new Intent(this, UploadActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnShare:
                Intent intent3=new Intent(this, ShareActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnLogin:
                Intent intent4=new Intent(this, LoginActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnProgress:
                Intent intent5=new Intent(this, ProgressActivity.class);
                startActivity(intent5);
                break;
            case R.id.btnAnim:
                Intent intent6=new Intent(this, AnimActivity.class);
                startActivity(intent6);
                break;
            case R.id.btnBugly:
                Intent intent7=new Intent(this,BuglyActivity.class);
                startActivity(intent7);
                break;
        }
    }
}
