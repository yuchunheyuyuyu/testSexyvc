package com.qtin.sexyvc.ui.user.info;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.info.di.DaggerUserInfoComponent;
import com.qtin.sexyvc.ui.user.info.di.UserInfoModule;
import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by ls on 17/4/26.
 */
public class UserInfoActivity extends MyBaseActivity<UserInfoPresent> implements UserInfoContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvMyDescription)
    TextView tvMyDescription;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvdentification)
    TextView tvdentification;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserInfoComponent.builder().appComponent(appComponent).userInfoModule(new UserInfoModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_user_info));
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

    @OnClick({R.id.ivLeft, R.id.avatarContainer, R.id.nickContainer, R.id.sexContainer, R.id.descriptionContainer, R.id.mobileContainer, R.id.emailContainer, R.id.positionContainer, R.id.identifyContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.avatarContainer:
                break;
            case R.id.nickContainer:
                break;
            case R.id.sexContainer:
                break;
            case R.id.descriptionContainer:
                break;
            case R.id.mobileContainer:
                break;
            case R.id.emailContainer:
                break;
            case R.id.positionContainer:
                break;
            case R.id.identifyContainer:
                break;
        }
    }
}
