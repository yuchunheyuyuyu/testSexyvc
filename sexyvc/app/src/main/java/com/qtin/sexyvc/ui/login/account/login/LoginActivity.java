package com.qtin.sexyvc.ui.login.account.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.account.login.di.DaggerLoginComponent;
import com.qtin.sexyvc.ui.login.account.login.di.LoginModule;
import com.qtin.sexyvc.ui.login.password.forget.ForgetActivity;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.widget.PhoneEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class LoginActivity extends MyBaseActivity<LoginPresent> implements LoginContract.View {


    @BindView(R.id.etPhone)
    PhoneEditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.login_activity;
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

    @OnClick({R.id.ivBack, R.id.tvLogin, R.id.tvForgetPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvLogin:
                String username=etPhone.getPhoneText();
                String password=etPassword.getText().toString();
                mPresenter.login(username,password);
                break;
            case R.id.tvForgetPassword:
                gotoActivity(ForgetActivity.class);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        gotoActivity(MainActivity.class);
    }
}
