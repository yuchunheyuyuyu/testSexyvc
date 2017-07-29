package com.qtin.sexyvc.ui.user.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.account.create.CreateActivity;
import com.qtin.sexyvc.ui.user.password.ModifyPasswordActivity;
import com.qtin.sexyvc.ui.user.setting.di.DaggerSettingComponent;
import com.qtin.sexyvc.ui.user.setting.di.SettingModule;
import com.qtin.sexyvc.ui.web.WebActivity;
import com.qtin.sexyvc.utils.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SettingActivity extends MyBaseActivity<SettingPresent> implements SettingContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvVersionName)
    TextView tvVersionName;
    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSettingComponent.builder().appComponent(appComponent).settingModule(new SettingModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_setting));
        tvCacheSize.setText(DataHelper.getCacheSize(getApplicationContext()));
        tvVersionName.setText("Ver "+getAPPVersion());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this, StringUtil.formatString(message));
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.modifyPasswordContainer, R.id.clearCacheContainer, R.id.aboutUsContainer,R.id.tvLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.modifyPasswordContainer:
                gotoActivity(ModifyPasswordActivity.class);
                break;
            case R.id.clearCacheContainer:
                showTwoButtonDialog(getResources().getString(R.string.clear_cache), getResources().getString(R.string.cancle),
                        getResources().getString(R.string.comfirm), new TwoButtonListerner() {
                            @Override
                            public void leftClick() {
                                dismissTwoButtonDialog();
                            }

                            @Override
                            public void rightClick() {
                                dismissTwoButtonDialog();
                                if(DataHelper.cleanCache(SettingActivity.this)){
                                    tvCacheSize.setText(DataHelper.getCacheSize(getApplicationContext()));
                                }
                            }
                        });
                break;
            case R.id.aboutUsContainer:
                Bundle bundle=new Bundle();
                bundle.putString(ConstantUtil.INTENT_URL,"about_sexyvc");
                gotoActivity(WebActivity.class,bundle);
                break;
            case R.id.tvLogout:
                showTwoButtonDialog(getResources().getString(R.string.comfirm_exit), getResources().getString(R.string.cancle),
                        getResources().getString(R.string.comfirm), new TwoButtonListerner() {
                            @Override
                            public void leftClick() {
                                dismissTwoButtonDialog();
                            }

                            @Override
                            public void rightClick() {
                                dismissTwoButtonDialog();
                                mPresenter.logout();
                            }
                        });
                break;
        }
    }

    private String getAPPVersion() {
        PackageManager pm = this.getPackageManager();//得到PackageManager对象
        try {
            PackageInfo packInfo = pm.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getResources().getString(R.string.no_known);
        }
    }

    @Override
    public void logoutSuccess() {
        gotoActivity(CreateActivity.class);
    }
}
