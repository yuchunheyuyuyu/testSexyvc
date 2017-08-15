package com.qtin.sexyvc.ui.login.account.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

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
import com.qtin.sexyvc.ui.login.choose.ChooseIdentityActivity;
import com.qtin.sexyvc.ui.login.register.RegisterActivity;
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
    private UMShareAPI mShareAPI;
    private String mobile;

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
            gotoActivity(ChooseIdentityActivity.class);
            //gotoActivity(MainActivity.class);
            return;
        }
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

    @OnClick({ R.id.tvCreateAccount,  R.id.wxLogin, R.id.qqLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCreateAccount:
                mobile=etPhone.getPhoneText();
                if(StringUtil.isBlank(mobile)){
                    showMessage("请输入手机号");
                    return;
                }
                if(!StringUtil.isMobile(mobile)){
                    showMessage("手机号输入不正确");
                    return;
                }
                mPresenter.checkRegisterStatus(mobile);
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

    @Override
    public void checkRegisterSuccess(int has_register) {
        Bundle bundle=new Bundle();
        bundle.putString("mobile",mobile);

        if(has_register==0){
            gotoActivity(RegisterActivity.class,bundle);
        }else{
            gotoActivity(LoginActivity.class,bundle);
        }
    }

    @Override
    public void startLoad(String msg) {
        showDialog(msg);
    }

    @Override
    public void endLoad() {
        dialogDismiss();
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            //Toast.makeText(getApplicationContext(), "Authorize start", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
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
                mShareAPI.deleteOauth(CreateActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
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
            //Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            //Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
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
