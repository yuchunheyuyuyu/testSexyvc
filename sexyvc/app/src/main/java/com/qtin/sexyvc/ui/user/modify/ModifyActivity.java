package com.qtin.sexyvc.ui.user.modify;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.modify.di.DaggerModifyComponent;
import com.qtin.sexyvc.ui.user.modify.di.ModifyModule;
import com.qtin.sexyvc.ui.widget.ClearableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ModifyActivity extends MyBaseActivity<ModifyPresent> implements ModifyContract.View {

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
    @BindView(R.id.etEmail)
    ClearableEditText etEmail;
    @BindView(R.id.etEmailAlternate)
    ClearableEditText etEmailAlternate;
    @BindView(R.id.emailContainer)
    LinearLayout emailContainer;
    private String hint;
    public static final int MODIFY_NICK = 0;
    public static final int MODIFY_INTRODUCE = 1;
    public static final int MODIFY_EMAIL = 2;
    public static final String MODIFY_INTENT="modify_type";

    private int modifyType;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyComponent.builder().appComponent(appComponent).modifyModule(new ModifyModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.modify_activity;
    }

    @Override
    protected void initData() {
        modifyType=getIntent().getExtras().getInt(MODIFY_INTENT);

        tvRight.setVisibility(View.VISIBLE);
        if(modifyType==MODIFY_NICK){
            etContent.setHint(getResources().getString(R.string.nick));
            singLineContainer.setVisibility(View.VISIBLE);
            tvTitle.setText(getResources().getString(R.string.title_nick));
        }else if(modifyType==MODIFY_EMAIL){
            emailContainer.setVisibility(View.VISIBLE);
            tvTitle.setText(getResources().getString(R.string.title_email));
        }else if(modifyType==MODIFY_INTRODUCE){
            introduceContainer.setVisibility(View.VISIBLE);
            tvTitle.setText(getResources().getString(R.string.title_introduce));
            tvCountDown.setText(String.format(getResources().getString(R.string.input_count),""+140));

            etIntroduce.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int last=140;
                    if(s!=null&&s.toString()!=null){
                        last=140-s.toString().length();
                    }
                    tvCountDown.setText(String.format(getResources().getString(R.string.input_count),""+last));
                }
            });

        }
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

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                break;
        }
    }
}
