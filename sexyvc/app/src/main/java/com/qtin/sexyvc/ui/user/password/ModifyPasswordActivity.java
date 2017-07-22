package com.qtin.sexyvc.ui.user.password;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.password.di.DaggerModifyPasswordComponent;
import com.qtin.sexyvc.ui.user.password.di.ModifyPasswordModule;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class ModifyPasswordActivity extends MyBaseActivity<ModifyPasswordPresent> implements ModifyPasswordContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.etOldPassword)
    EditText etOldPassword;
    @BindView(R.id.etNewPassword1)
    EditText etNewPassword1;
    @BindView(R.id.etNewPassword2)
    EditText etNewPassword2;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyPasswordComponent.builder().appComponent(appComponent).modifyPasswordModule(new ModifyPasswordModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.modify_password_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_modify_password));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.complete));
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
        UiUtils.showToastShort(this,message);
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
                String oldPwd=etOldPassword.getText().toString();
                String newPwd1=etNewPassword1.getText().toString();
                String newPwd2=etNewPassword2.getText().toString();

                if(StringUtil.checkPasswordFormat(oldPwd)){
                    showMessage("旧密码长度范围在8-20位数字/字母");
                    return;
                }

                if(StringUtil.checkPasswordFormat(newPwd1)){
                    showMessage("新密码长度范围在8-20位数字/字母");
                    return;
                }
                if(!newPwd1.equals(newPwd2)){
                    showMessage("两次新密码输入不同");
                    return;
                }

                mPresenter.modifyPassword(oldPwd,newPwd1);
                break;
        }
    }

    @Override
    public void modifySuccess() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        finish();
                    }
                });
    }
}
