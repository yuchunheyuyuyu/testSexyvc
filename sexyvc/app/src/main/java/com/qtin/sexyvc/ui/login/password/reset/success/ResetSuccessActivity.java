package com.qtin.sexyvc.ui.login.password.reset.success;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.account.create.CreateActivity;
import butterknife.OnClick;

/**
 * Created by ls on 17/6/12.
 */
public class ResetSuccessActivity extends MyBaseActivity {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.reset_success_activity;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tvReLogin)
    public void onClick() {
        gotoActivity(CreateActivity.class);
    }
}
