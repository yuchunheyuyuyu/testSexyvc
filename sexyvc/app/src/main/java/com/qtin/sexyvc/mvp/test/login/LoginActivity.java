package com.qtin.sexyvc.mvp.test.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.di.component.DaggerLoginComponent;
import com.qtin.sexyvc.di.module.LoginModule;
import com.qtin.sexyvc.mvp.contract.LoginContarct;
import com.qtin.sexyvc.mvp.model.entity.DaoSession;
import com.qtin.sexyvc.mvp.model.entity.LoginUser;
import com.qtin.sexyvc.mvp.model.entity.LoginUserDao;
import com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity;
import com.qtin.sexyvc.mvp.presenter.LoginPresenter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/3/3.
 */

public class LoginActivity extends MyBaseActivity<LoginPresenter> implements LoginContarct.View {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tvHistory)
    TextView tvHistory;

    private LoginUserDao userDao;
    private Query<LoginUser> query;

    private UMShareAPI mShareAPI;

    @Inject
    DaoSession daoSession;
    /**
     * 账号统计相关
     * <p>
     * 当用户使用自有账号登录时，可以这样统计：
     * MobclickAgent.onProfileSignIn("userID");
     * 当用户使用第三方账号（如新浪微博）登录时，可以这样统计：
     * MobclickAgent.onProfileSignIn("WB"，"userID");
     * 账号登出时需调用此接口，调用之后不再发送账号相关内容。
     * MobclickAgent.onProfileSignOff();
     */

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onProfileSignOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onProfileSignIn("da14523456");
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
        userDao = daoSession.getLoginUserDao();
        query = userDao.queryBuilder().build();

        setTvHistory();
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.loginQQ, R.id.loginWX, R.id.loginSina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();

                LoginUser user = new LoginUser();
                user.setName(name);
                user.setPassword(password);
                user.setDate(new Date());
                userDao.insert(user);

                setTvHistory();
                break;
            case R.id.btnRegister:

                break;
            case R.id.loginQQ:
                getThirdInfo(SHARE_MEDIA.QQ);
                break;
            case R.id.loginWX:
                getThirdInfo(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.loginSina:
                getThirdInfo(SHARE_MEDIA.SINA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShareAPI = null;
        umAuthListener = null;
        UMShareAPI.get(this).release();
    }

    private void getThirdInfo(SHARE_MEDIA media) {
        if (mShareAPI == null) {
            mShareAPI = UMShareAPI.get(LoginActivity.this);
        }
        mShareAPI.getPlatformInfo(LoginActivity.this, media, umAuthListener);
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
            textView.setText(temp);

            String uid = data.get("uid");
            String name = data.get("name");
            String gender = data.get("gender");
            String iconurl = data.get("iconurl");

            RegisterRequestEntity entity = new RegisterRequestEntity();
            entity.setAvatar(iconurl);
            entity.setDevice_token(customApplication.deviceToken);

            if (platform == SHARE_MEDIA.QQ) {
                entity.setUsertype(3);
            } else if (platform == SHARE_MEDIA.SINA) {
                entity.setUsertype(2);
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                entity.setUsertype(1);
            } else {
                entity.setUsertype(0);
            }
            entity.setUsername(uid);

            entity.setNickname(name);

            if ("男".equals(gender)) {
                entity.setGender(1);
            } else if ("女".equals(gender)) {
                entity.setGender(2);
            } else {
                entity.setGender(0);
            }

            mPresenter.register(entity);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private void setTvHistory(){

        List<LoginUser> data=query.list();
        String  result="";
        if(data!=null&&!data.isEmpty()){
            for(LoginUser user:data){
                result+="name:"+user.getName()+"\t"+"password:"+user.getPassword()+"\n";
            }
        }
        tvHistory.setText(result);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
