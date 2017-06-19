package com.qtin.sexyvc.ui.user.message;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
/**
 * Created by ls on 17/6/16.
 */
public class MessageActivity extends MyBaseActivity {


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.message_activity;
    }

    @Override
    protected void initData() {

    }
}
