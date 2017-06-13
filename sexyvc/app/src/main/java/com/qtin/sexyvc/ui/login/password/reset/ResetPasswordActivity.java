package com.qtin.sexyvc.ui.login.password.reset;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.password.reset.di.DaggerResetPasswordComponent;
import com.qtin.sexyvc.ui.login.password.reset.di.ResetPasswordModule;
import com.qtin.sexyvc.ui.login.password.reset.success.ResetSuccessActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ResetPasswordActivity extends MyBaseActivity<ResetPasswordPresent> implements ResetPasswordContract.View {


    @BindView(R.id.etFirstPassword)
    EditText etFirstPassword;
    @BindView(R.id.etSecondPassword)
    EditText etSecondPassword;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerResetPasswordComponent.builder().appComponent(appComponent).resetPasswordModule(new ResetPasswordModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.reset_password_activity;
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

    @OnClick({R.id.ivBack, R.id.tvComplete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvComplete:
                gotoActivity(ResetSuccessActivity.class);
                break;
        }
    }
}
