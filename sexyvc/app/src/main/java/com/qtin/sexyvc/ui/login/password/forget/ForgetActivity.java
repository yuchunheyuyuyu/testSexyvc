package com.qtin.sexyvc.ui.login.password.forget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.password.forget.di.DaggerForgetComponent;
import com.qtin.sexyvc.ui.login.password.forget.di.ForgetModule;
import com.qtin.sexyvc.ui.login.password.reset.ResetPasswordActivity;
import com.qtin.sexyvc.ui.widget.PhoneEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ForgetActivity extends MyBaseActivity<ForgetPresent> implements ForgetContract.View {


    @BindView(R.id.etVertify)
    EditText etVertify;
    @BindView(R.id.etPhone)
    PhoneEditText etPhone;

    private String phoneStr;
    private static final int TOTAL_TIME = 30;//倒计时总时间
    @BindView(R.id.tvGetVertify)
    TextView tvGetVertify;
    private int countDown = TOTAL_TIME;

    private Handler mHandler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            countDown--;
            if(countDown==0){
                countDown=TOTAL_TIME;
                tvGetVertify.setSelected(true);
                tvGetVertify.setText(getResources().getString(R.string.reget_vertify_code));
            }else{
                String str=String.format(getResources().getString(R.string.get_vertify_ing),""+countDown);
                tvGetVertify.setText(str);
                mHandler.postDelayed(this,1000);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerForgetComponent.builder().appComponent(appComponent).forgetModule(new ForgetModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.forget_activity;
    }

    @Override
    protected void initData() {
        etPhone.setPhoneVertifyListener(new PhoneEditText.PhoneVertifyListener() {
            @Override
            public void isPhone(boolean isPhone) {
                if (countDown == TOTAL_TIME) {
                    if (isPhone) {
                        tvGetVertify.setSelected(true);
                    } else {
                        tvGetVertify.setSelected(false);
                    }
                }
            }
        });
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

    @OnClick({R.id.ivBack, R.id.tvNextStep,R.id.tvGetVertify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvGetVertify:
                if(tvGetVertify.isSelected()){
                    tvGetVertify.setSelected(false);
                    mHandler.post(runnable);
                    //获取验证码
                    phoneStr=etPhone.getPhoneText();
                    mPresenter.getVertifyCode(phoneStr);
                }
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvNextStep:
                mPresenter.validateCode(phoneStr,etVertify.getText().toString());
                break;
        }
    }

    @Override
    public void validateSuccess() {
        Bundle bundle=new Bundle();
        bundle.putString("code_value",etVertify.getText().toString());
        bundle.putString("phoneStr",phoneStr);
        gotoActivity(ResetPasswordActivity.class,bundle);
    }
}
