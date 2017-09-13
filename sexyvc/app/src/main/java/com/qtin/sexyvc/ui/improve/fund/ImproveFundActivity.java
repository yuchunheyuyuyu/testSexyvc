package com.qtin.sexyvc.ui.improve.fund;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
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
    EditText etContent;
    @BindView(R.id.tvCountDown)
    TextView tvCountDown;

    private static  final int wordNumber = 300;

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

        tvCountDown.setText(String.format(getResources().getString(R.string.input_count), "" + wordNumber));

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int last = wordNumber;
                if (s != null && s.toString() != null) {
                    last = wordNumber - s.toString().length();
                }
                tvCountDown.setText(String.format(getResources().getString(R.string.input_count), "" + last));
            }
        });
    }

    @Override
    public void showLoading() {
        showDialog("正在提交");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                String supply_name=etName.getText().toString().trim();
                String supply_content=etContent.getText().toString().trim();
                if(StringUtil.isBlank(supply_name)){
                    showMessage(getString(R.string.institution_name_not_null));
                    return;
                }

                if(StringUtil.isBlank(supply_content)){
                    showMessage(getString(R.string.institution_info_not_null));
                    return;
                }
                mPresenter.addFund(supply_name,supply_content);
                break;
        }
    }

    @Override
    public void addSuccess() {
        finish();
    }
}
