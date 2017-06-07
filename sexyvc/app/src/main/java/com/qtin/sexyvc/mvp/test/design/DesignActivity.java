package com.qtin.sexyvc.mvp.test.design;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
/**
 * Created by ls on 17/3/29.
 */

public class DesignActivity extends MyBaseActivity {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_design_activity;
    }

    @Override
    protected void initData() {

    }
}
