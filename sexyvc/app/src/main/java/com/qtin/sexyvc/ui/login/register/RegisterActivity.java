package com.qtin.sexyvc.ui.login.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.login.account.create.success.CreateSuccessActivity;
import com.qtin.sexyvc.ui.login.register.di.DaggerRegisterComponent;
import com.qtin.sexyvc.ui.login.register.di.RegisterModule;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.web.WebActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class RegisterActivity extends MyBaseActivity<RegisterPresent> implements RegisterContract.View {


    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.etVertifyCode)
    EditText etVertifyCode;
    @BindView(R.id.tvGetVertify)
    TextView tvGetVertify;
    @BindView(R.id.etFirstPassword)
    EditText etFirstPassword;
    @BindView(R.id.etSecondPassword)
    EditText etSecondPassword;

    private String mobile;
    private static final int TOTAL_TIME=30;//倒计时总时间
    private int countDown=TOTAL_TIME;

    private String pwd1;

    private Handler mHandler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            countDown--;
            if(countDown==0){
                countDown=TOTAL_TIME;
                tvGetVertify.setSelected(true);
                tvGetVertify.setText(getResources().getString(R.string.get_vertify_code));
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
    protected void onResume() {
        super.onResume();
        if(countDown!=TOTAL_TIME){
            mHandler.post(runnable);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent.builder().appComponent(appComponent).registerModule(new RegisterModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.register_activity;
    }

    @Override
    protected void initData() {
        mobile=getIntent().getExtras().getString("mobile");
        tvPhone.setText(StringUtil.getFormatPhone(mobile));
        tvGetVertify.setSelected(true);
    }

    @Override
    public void showLoading() {
        showDialog("正在提交");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
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

    @OnClick({R.id.ivBack, R.id.tvGetVertify, R.id.tvComplete, R.id.tvServiceProtocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvGetVertify:
                if(tvGetVertify.isSelected()){
                    mHandler.post(runnable);
                    tvGetVertify.setSelected(false);
                    mPresenter.getVertifyCode(mobile);
                }
                break;
            case R.id.tvComplete:
                String code=etVertifyCode.getText().toString();
                if(StringUtil.isBlank(code)){
                    showMessage("验证码不能为空");
                    return;
                }

                pwd1=etFirstPassword.getText().toString();
                String pwd2=etSecondPassword.getText().toString();
                if(!pwd1.equals(pwd2)){
                    showMessage("两次密码不一致");
                    return;
                }
                if(StringUtil.checkPasswordFormat(pwd1)){
                    showMessage("新密码长度8~20位字母数字组合");
                    return;
                }
                mPresenter.validateCode(mobile,code);

                break;
            case R.id.tvServiceProtocol:
                Bundle bundle=new Bundle();
                bundle.putString(ConstantUtil.INTENT_URL,"service_protocol");
                gotoActivity(WebActivity.class,bundle);
                break;
        }
    }

    @Override
    public void validateSuccess() {
        RegisterRequestEntity entity=new RegisterRequestEntity();
        // 1：手机号，2：微信；3：QQ
        entity.setAccount_type(1);
        entity.setAvatar("");
        entity.setGender(0);
        entity.setNickname("");
        entity.setPassword(pwd1);
        entity.setUsername(mobile);
        entity.setWx_union_id("");
        entity.setDevice_token(customApplication.deviceToken);

        mPresenter.doRegister(entity);
    }

    @Override
    public void registerSuccess(UserInfoEntity entity) {
        Bundle bundle=new Bundle();
        bundle.putParcelable(UserInfoActivity.INTENT_USER,entity);
        gotoActivity(CreateSuccessActivity.class,bundle);
    }
}
