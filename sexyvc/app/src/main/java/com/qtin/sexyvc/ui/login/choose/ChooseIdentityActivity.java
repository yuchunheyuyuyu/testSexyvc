package com.qtin.sexyvc.ui.login.choose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.login.choose.di.ChooseIdentityModule;
import com.qtin.sexyvc.ui.login.choose.di.DaggerChooseIdentityComponent;
import com.qtin.sexyvc.ui.recommend.RecommendActivity;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.utils.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ChooseIdentityActivity extends MyBaseActivity<ChooseIdentityPresent> implements ChooseIdentityContract.View {

    @BindView(R.id.ivTypeFD)
    ImageView ivTypeFD;
    @BindView(R.id.ivTypeVC)
    ImageView ivTypeVC;
    @BindView(R.id.ivTypeFA)
    ImageView ivTypeFA;

    private int selectedType=ConstantUtil.AUTH_TYPE_UNKNOWN;
    private final int ALPHA_70= (int) (255*0.7f);
    private final int ALPHA_100=255;

    private UserInfoEntity userInfoEntity;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerChooseIdentityComponent.builder().appComponent(appComponent).chooseIdentityModule(new ChooseIdentityModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.choose_identity_activity;
    }

    @Override
    protected void initData() {
        userInfoEntity=getIntent().getExtras().getParcelable(UserInfoActivity.INTENT_USER);
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

    @OnClick({R.id.ivTypeFD, R.id.ivTypeVC, R.id.ivTypeFA, R.id.tvNextStep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTypeFD:
                selectedType= ConstantUtil.AUTH_TYPE_FOUNDER;
                ivTypeFD.setAlpha(ALPHA_100);
                ivTypeFD.setImageResource(R.drawable.bg_type_fd_sel);

                ivTypeVC.setAlpha(ALPHA_70);
                ivTypeVC.setImageResource(R.drawable.bg_type_vc);

                ivTypeFA.setAlpha(ALPHA_70);
                ivTypeFA.setImageResource(R.drawable.bg_type_fa);
                break;
            case R.id.ivTypeVC:
                selectedType=ConstantUtil.AUTH_TYPE_INVESTOR;

                ivTypeFD.setAlpha(ALPHA_70);
                ivTypeFD.setImageResource(R.drawable.bg_type_fd);

                ivTypeVC.setAlpha(ALPHA_100);
                ivTypeVC.setImageResource(R.drawable.bg_type_vc_sel);

                ivTypeFA.setAlpha(ALPHA_70);
                ivTypeFA.setImageResource(R.drawable.bg_type_fa);

                break;
            case R.id.ivTypeFA:
                selectedType=ConstantUtil.AUTH_TYPE_FA;

                ivTypeFD.setAlpha(ALPHA_70);
                ivTypeFD.setImageResource(R.drawable.bg_type_fd);

                ivTypeVC.setAlpha(ALPHA_70);
                ivTypeVC.setImageResource(R.drawable.bg_type_vc);

                ivTypeFA.setAlpha(ALPHA_100);
                ivTypeFA.setImageResource(R.drawable.bg_type_fa_sel);

                break;
            case R.id.tvNextStep:
                if(selectedType==ConstantUtil.AUTH_TYPE_UNKNOWN){
                    showMessage("请选择身份");
                    return;
                }
                mPresenter.editPosition(selectedType,"","");
                break;
        }
    }

    @Override
    public void editSuccess() {
        Bundle bundle=new Bundle();
        userInfoEntity.setU_auth_type(selectedType);
        mPresenter.saveUsrInfo(userInfoEntity);

        bundle.putParcelable(UserInfoActivity.INTENT_USER,userInfoEntity);
        gotoActivity(RecommendActivity.class,bundle);
    }
}
