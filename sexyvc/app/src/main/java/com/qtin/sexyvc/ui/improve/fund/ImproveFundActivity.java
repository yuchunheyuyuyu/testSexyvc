package com.qtin.sexyvc.ui.improve.fund;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.improve.fund.di.DaggerImproveFundComponent;
import com.qtin.sexyvc.ui.improve.fund.di.ImproveFundModule;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ImproveFundActivity extends MyBaseActivity<ImproveFundPresent> implements ImproveFundContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.etName)
    ClearableEditText etName;
    @BindView(R.id.etContent)
    ClearableEditText etContent;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerImproveFundComponent.builder().appComponent(appComponent).improveFundModule(new ImproveFundModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.improve_fund_activity;
    }

    @Override
    protected void initData() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.commit));
        tvTitle.setText(getString(R.string.improve_fund_info));


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.tvTitle, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvTitle:
                break;
            case R.id.tvRight:
                break;
        }
    }
}
