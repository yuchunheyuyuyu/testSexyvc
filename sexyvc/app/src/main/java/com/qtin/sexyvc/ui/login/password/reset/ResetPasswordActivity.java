package com.qtin.sexyvc.ui.login.password.reset;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
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

    private String phoneStr;
    private String code_value;//验证码

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
        phoneStr=getIntent().getExtras().getString("phoneStr");
        code_value=getIntent().getExtras().getString("code_value");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this,message);
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
                String pwd1=etFirstPassword.getText().toString();
                String pwd2=etSecondPassword.getText().toString();
                if(!pwd1.equals(pwd2)){
                    showMessage("两次密码输入不同");
                }

                if(StringUtil.checkPasswordFormat(pwd1)){
                    showMessage("新密码长度范围在8-20位数字/字母");
                    return;
                }

                mPresenter.resetPassword(code_value,phoneStr,pwd1);

                break;
        }
    }

    @Override
    public void resetSuccess() {
        gotoActivity(ResetSuccessActivity.class);
    }
}
