package com.qtin.sexyvc.ui.login.account.create;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.login.account.bind.BindActivity;
import com.qtin.sexyvc.ui.login.account.create.di.CreateModule;
import com.qtin.sexyvc.ui.login.account.create.di.DaggerCreateComponent;
import com.qtin.sexyvc.ui.login.account.login.LoginActivity;
import com.qtin.sexyvc.ui.login.password.set.SetPasswordActivity;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.widget.PhoneEditText;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class CreateActivity extends MyBaseActivity<CreatePresent> implements CreateContract.View {

    @BindView(R.id.etPhone)
    PhoneEditText etPhone;
    @BindView(R.id.etVertifyCode)
    EditText etVertifyCode;
    @BindView(R.id.tvGetVertify)
    TextView tvGetVertify;
    private static final int TOTAL_TIME=60;//倒计时总时间
    private int countDown=TOTAL_TIME;

    private UMShareAPI mShareAPI;

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
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCreateComponent.builder().appComponent(appComponent).createModule(new CreateModule(this)).build().inject(this);
    }


    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.create_activity;
    }

    @Override
    protected void initData() {

        if(mPresenter.isLogin()){
            gotoActivity(MainActivity.class);
            return;
        }

        etPhone.setPhoneVertifyListener(new PhoneEditText.PhoneVertifyListener() {
            @Override
            public void isPhone(boolean isPhone) {
                if(countDown==TOTAL_TIME){
                    if(isPhone){
                        tvGetVertify.setSelected(true);
                    }else{
                        tvGetVertify.setSelected(false);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
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

    @OnClick({R.id.tvGetVertify, R.id.tvCreateAccount, R.id.tvToLogin, R.id.wxLogin, R.id.qqLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvGetVertify:
                if(tvGetVertify.isSelected()){
                    tvGetVertify.setSelected(false);
                    mHandler.post(runnable);
                    //获取验证码
                    mPresenter.getVertifyCode(etPhone.getPhoneText());
                }
                break;
            case R.id.tvCreateAccount:
                String code_value=etVertifyCode.getText().toString();
                if(StringUtil.isBlank(code_value)){
                    showMessage("验证码不能为空");
                    return;
                }
                if(etPhone.isMobileNO()&& !StringUtil.isBlank(code_value)){
                    mPresenter.validateCode(etPhone.getPhoneText(),code_value);
                }
                break;
            case R.id.tvToLogin:
                gotoActivity(LoginActivity.class);
                break;
            case R.id.wxLogin:
                getThirdInfo(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qqLogin:
                getThirdInfo(SHARE_MEDIA.QQ);
                break;
        }
    }

    private void getThirdInfo(SHARE_MEDIA media) {
        if (mShareAPI == null) {
            mShareAPI = UMShareAPI.get(CreateActivity.this);
        }
        if(!mShareAPI.isInstall(this,media)){
            UiUtils.showToastShort(getApplicationContext(),media.toString()+"暂未安装");
        }
        mShareAPI.getPlatformInfo(CreateActivity.this, media, umAuthListener);
    }

    @Override
    public void validateSuccess() {
        Bundle bundle=new Bundle();
        bundle.putInt("type",1);
        bundle.putString("phoneStr",etPhone.getPhoneText());
        gotoActivity(SetPasswordActivity.class,bundle);
    }

    @Override
    public void gotoBind(int type) {
        //2微信，3qq
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        gotoActivity(BindActivity.class,bundle);
    }

    @Override
    public void notNeedBind() {
        gotoActivity(MainActivity.class);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            Toast.makeText(getApplicationContext(), "Authorize start", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }

            String name = data.get("name");
            String gender = data.get("gender");
            String iconurl = data.get("iconurl");

            RegisterRequestEntity entity = new RegisterRequestEntity();
            entity.setAvatar(iconurl);

            entity.setDevice_token(customApplication.deviceToken);
            entity.setUsername(data.get("openid"));
            entity.setNickname(data.get("name"));
            if (platform == SHARE_MEDIA.QQ) {
                entity.setAccount_type(3);
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                entity.setAccount_type(2);
                entity.setWx_union_id(data.get("unionid"));
            }

            entity.setNickname(name);

            if ("男".equals(gender)) {
                entity.setGender(1);
            } else if ("女".equals(gender)) {
                entity.setGender(2);
            } else {
                entity.setGender(0);
            }
            mPresenter.doRegister(entity);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            UiUtils.exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
