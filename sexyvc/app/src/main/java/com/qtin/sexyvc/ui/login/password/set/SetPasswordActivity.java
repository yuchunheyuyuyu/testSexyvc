package com.qtin.sexyvc.ui.login.password.set;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.login.account.create.success.CreateSuccessActivity;
import com.qtin.sexyvc.ui.login.password.set.di.DaggerSetPasswordComponent;
import com.qtin.sexyvc.ui.login.password.set.di.SetPasswordModule;
import com.qtin.sexyvc.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SetPasswordActivity extends MyBaseActivity<SetPasswordPresent> implements SetPasswordContract.View {


    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.etFirstPassword)
    EditText etFirstPassword;
    @BindView(R.id.etSecondPassword)
    EditText etSecondPassword;

    private String phoneStr;
    private int type;//1：手机号，2：微信；3：QQ

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSetPasswordComponent.builder().appComponent(appComponent).setPasswordModule(new SetPasswordModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.set_password_activity;
    }

    private String getFormatPhone(String origin){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            if (i != 3 && i != 8 && origin.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(origin.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        return sb.toString();
    }

    @Override
    protected void initData() {
        type=getIntent().getExtras().getInt("type");
        phoneStr=getIntent().getExtras().getString("phoneStr");
        tvPhone.setText(getFormatPhone(phoneStr));
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
                if(pwd1.equals(pwd2)){
                    if(type==1){
                        RegisterRequestEntity entity=new RegisterRequestEntity();
                        // 1：手机号，2：微信；3：QQ
                        entity.setAccount_type(1);
                        entity.setAvatar("");
                        entity.setGender(0);
                        entity.setNickname("");
                        entity.setPassword(pwd1);
                        entity.setUsername(phoneStr);
                        entity.setWx_union_id("");
                        entity.setDevice_token(customApplication.deviceToken);

                        mPresenter.doRegister(entity);
                    }else{
                        mPresenter.bindMobile(phoneStr,pwd1);
                    }
                }
                break;
        }
    }

    @Override
    public void rigisterSuccess() {
        gotoActivity(CreateSuccessActivity.class);
    }

    @Override
    public void bindSuccess() {
        gotoActivity(MainActivity.class);
    }
}
