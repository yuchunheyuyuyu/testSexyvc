package com.qtin.sexyvc.ui.login.account.create.success;

import android.os.Bundle;
import android.view.View;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;

import butterknife.OnClick;

/**
 * Created by ls on 17/6/12.
 */
public class CreateSuccessActivity extends MyBaseActivity {

    private UserInfoEntity userInfoEntity;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.create_success_activity;
    }

    @Override
    protected void initData() {
        userInfoEntity=getIntent().getExtras().getParcelable(UserInfoActivity.INTENT_USER);
    }

    @OnClick({R.id.tvContinueInfo, R.id.tvLookAround})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinueInfo:
                Bundle bundle=new Bundle();
                bundle.putParcelable(UserInfoActivity.INTENT_USER,userInfoEntity);
                bundle.putBoolean("isNeedGotoMain",true);
                gotoActivity(UserInfoActivity.class,bundle);

                finish();

                break;
            case R.id.tvLookAround:
                gotoActivity(MainActivity.class);
                break;
        }
    }
}
