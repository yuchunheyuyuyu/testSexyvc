package com.qtin.sexyvc.ui.create.investor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import com.qtin.sexyvc.ui.widget.PhoneEditText;
import com.qtin.sexyvc.utils.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/7/18.
 */

public class CreateInvestorInfoActivity extends MyBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.etContent)
    ClearableEditText etContent;
    @BindView(R.id.singLineContainer)
    LinearLayout singLineContainer;
    @BindView(R.id.tvCountDown)
    TextView tvCountDown;
    @BindView(R.id.etIntroduce)
    EditText etIntroduce;
    @BindView(R.id.introduceContainer)
    RelativeLayout introduceContainer;
    @BindView(R.id.etPhone)
    PhoneEditText etPhone;
    @BindView(R.id.phoneContainer)
    LinearLayout phoneContainer;

    private int type;
    private String value;
    private int wordNumber = 140;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.create_investor_info_activity;
    }

    @Override
    protected void initData() {

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.comfirm));

        type = getIntent().getExtras().getInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE);
        value = getIntent().getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);

        if (type == ConstantUtil.TYPE_CREATE_INVESTOR_REMARK) {
            introduceContainer.setVisibility(View.VISIBLE);

            etIntroduce.setHint(getResources().getString(R.string.remark));
            tvTitle.setText(getResources().getString(R.string.remark));
            etIntroduce.setText(value);
            etIntroduce.setSelection(value.length());

            tvCountDown.setText(String.format(getResources().getString(R.string.input_count), "" + (wordNumber - value.length())));

            etIntroduce.addTextChangedListener(new TextWatcher() {
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

        } else if(type == ConstantUtil.TYPE_CREATE_INVESTOR_PHONE){
            phoneContainer.setVisibility(View.VISIBLE);
            etPhone.setHint(getResources().getString(R.string.phone));
            tvTitle.setText(getResources().getString(R.string.phone));
            etPhone.setText(value);
            etPhone.setSelection(value.length());

        } else {
            singLineContainer.setVisibility(View.VISIBLE);
            etContent.setText(value);
            etContent.setSelection(value.length());

            if (type == ConstantUtil.TYPE_CREATE_INVESTOR_NAME) {
                etContent.setHint(getResources().getString(R.string.investor_name));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                tvTitle.setText(getResources().getString(R.string.investor_name));
                etContent.setInputType(InputType.TYPE_CLASS_TEXT);

            } else if (type == ConstantUtil.TYPE_CREATE_INVESTOR_FUND) {
                etContent.setHint(getResources().getString(R.string.fund_name));
                tvTitle.setText(getResources().getString(R.string.fund_name));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                etContent.setInputType(InputType.TYPE_CLASS_TEXT);

            } else if (type == ConstantUtil.TYPE_CREATE_INVESTOR_TITLE) {
                etContent.setHint(getResources().getString(R.string.investor_title));
                tvTitle.setText(getResources().getString(R.string.investor_title));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                etContent.setInputType(InputType.TYPE_CLASS_TEXT);

            } else if(type==ConstantUtil.TYPE_CREATE_INVESTOR_WECHAT){
                etContent.setHint(getResources().getString(R.string.hint_we_chat));
                tvTitle.setText(getResources().getString(R.string.we_chat));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                etContent.setInputType(InputType.TYPE_CLASS_TEXT);

            } else {
                etContent.setHint(getResources().getString(R.string.email));
                tvTitle.setText(getResources().getString(R.string.email));
                etContent.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        }
    }

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                String content = "";
                if (type == ConstantUtil.TYPE_CREATE_INVESTOR_REMARK) {
                    content = etIntroduce.getText().toString();
                    if (StringUtil.isBlank(content)) {
                        UiUtils.showToastShort(this, "备注不能为空");
                        return;
                    }
                } else if(type == ConstantUtil.TYPE_CREATE_INVESTOR_PHONE){
                    content=etPhone.getPhoneText();
                    if (StringUtil.isBlank(content)) {
                        UiUtils.showToastShort(this, "手机号不能为空");
                        return;
                    }
                    if (!etPhone.isMobileNO()) {
                        UiUtils.showToastShort(this, "手机格式不合法");
                        return;
                    }
                }else{
                    content = etContent.getText().toString();
                    if (StringUtil.isBlank(content)) {
                        UiUtils.showToastShort(this, "填写内容不能为空");
                        return;
                    }

                    if(type == ConstantUtil.TYPE_CREATE_INVESTOR_EMAIL){
                        if (!StringUtil.isEmail(content)) {
                            UiUtils.showToastShort(this, "邮箱格式不合法");
                            return;
                        }
                    }
                }


                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, content);
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
                break;
        }
    }
}
