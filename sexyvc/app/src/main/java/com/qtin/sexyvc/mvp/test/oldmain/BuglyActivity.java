package com.qtin.sexyvc.mvp.test.oldmain;

import android.view.View;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.tencent.bugly.crashreport.CrashReport;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/7.
 */
public class BuglyActivity extends MyBaseActivity {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_bugly_activity;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btnTestJavaCrash, R.id.btnTestANRCrash, R.id.btnTestNativeCrash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTestJavaCrash:
                CrashReport.testJavaCrash();
                break;
            case R.id.btnTestANRCrash:
                CrashReport.testANRCrash();
                break;
            case R.id.btnTestNativeCrash:
                CrashReport.testNativeCrash();
                break;
        }
    }
}
