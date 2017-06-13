package com.qtin.sexyvc.ui.login.account.create.success;

import android.view.View;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.main.MainActivity;

import butterknife.OnClick;

/**
 * Created by ls on 17/6/12.
 */
public class CreateSuccessActivity extends MyBaseActivity {


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

    }

    @OnClick({R.id.tvContinueInfo, R.id.tvLookAround})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvContinueInfo:

                break;
            case R.id.tvLookAround:
                gotoActivity(MainActivity.class);
                break;
        }
    }
}
