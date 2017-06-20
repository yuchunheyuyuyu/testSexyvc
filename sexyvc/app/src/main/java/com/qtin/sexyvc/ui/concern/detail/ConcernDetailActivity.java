package com.qtin.sexyvc.ui.concern.detail;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.concern.detail.di.ConcernDetailModule;
import com.qtin.sexyvc.ui.concern.detail.di.DaggerConcernDetailComponent;
import com.qtin.sexyvc.ui.widget.CustomProgressBar;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ConcernDetailActivity extends MyBaseActivity<ConcernDetailPresent> implements ConcernDetailContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvRateNum)
    TextView tvRateNum;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.ratingScore)
    BaseRatingBar ratingScore;
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.pbProfessionalQualities)
    CustomProgressBar pbProfessionalQualities;
    @BindView(R.id.pbFeedbackSpeed)
    ProgressBar pbFeedbackSpeed;
    @BindView(R.id.pbRoadEfficiency)
    ProgressBar pbRoadEfficiency;
    @BindView(R.id.pbExperienceShare)
    ProgressBar pbExperienceShare;
    @BindView(R.id.tvTelephone)
    TextView tvTelephone;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvWechat)
    TextView tvWechat;
    @BindView(R.id.tvRemark)
    TextView tvRemark;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerConcernDetailComponent.builder().appComponent(appComponent).concernDetailModule(new ConcernDetailModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.concern_detail_activity;
    }

    @Override
    protected void initData() {

        tvTitle.setText("吴世春");


        pbProfessionalQualities.setProgress(60);


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

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.concernContainer, R.id.commentContainer, R.id.telephoneContainer, R.id.emailContainer, R.id.wechatContainer, R.id.remarkContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                break;
            case R.id.tvRight:
                break;
            case R.id.concernContainer:
                break;
            case R.id.commentContainer:
                break;
            case R.id.telephoneContainer:
                break;
            case R.id.emailContainer:
                break;
            case R.id.wechatContainer:
                break;
            case R.id.remarkContainer:
                break;
        }
    }
}
