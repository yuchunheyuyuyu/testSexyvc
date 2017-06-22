package com.qtin.sexyvc.ui.concern.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.concern.detail.di.ConcernDetailModule;
import com.qtin.sexyvc.ui.concern.detail.di.DaggerConcernDetailComponent;
import com.qtin.sexyvc.ui.concern.set.SetGroupActivity;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;

import java.util.ArrayList;

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
    ProgressBar pbProfessionalQualities;
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
    @BindView(R.id.ivConcern)
    ImageView ivConcern;
    @BindView(R.id.tvConcern)
    TextView tvConcern;
    @BindView(R.id.ivComent)
    ImageView ivComent;
    @BindView(R.id.tvComment)
    TextView tvComment;

    private ArrayList<FilterEntity> personalTags = new ArrayList<>();

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
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.detail));

        pbProfessionalQualities.setProgress(60);
        TagAdapter tagAdapter = new TagAdapter<FilterEntity>(personalTags) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                TextView tv = (TextView) LayoutInflater.from(ConcernDetailActivity.this).inflate(R.layout.item_filter_textview2, flowLayout, false);
                tv.setText(o.getType_name());
                return tv;
            }
        };
        flowLayout.setAdapter(tagAdapter);

        for (int i = 0; i < 7; i++) {
            FilterEntity entity = new FilterEntity();
            entity.setKey_id(i);
            entity.setType_name("标签" + (i + 1));
            personalTags.add(entity);
        }
        tagAdapter.notifyDataChanged();
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
                finish();
                break;
            case R.id.tvRight:
                break;
            case R.id.concernContainer:
                showBottomDialog("#fe3824", getResources().getString(R.string.set_group),
                        getResources().getString(R.string.cancle_concern),
                        getResources().getString(R.string.cancle),
                        new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                gotoActivity(SetGroupActivity.class);
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });

                break;
            case R.id.commentContainer:
                break;
            case R.id.telephoneContainer:
                Bundle mobile=new Bundle();
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE1,"15212231584");
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE2,"15605538903");
                mobile.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_TELPHONE);
                gotoActivityForResult(ModifyActivity.class,mobile,ModifyActivity.MODIFY_CONCERN_TELPHONE);
                break;
            case R.id.emailContainer:
                Bundle email=new Bundle();
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE1,"");
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE2,"");
                email.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_EMAIL);
                gotoActivityForResult(ModifyActivity.class,email,ModifyActivity.MODIFY_CONCERN_EMAIL);
                break;
            case R.id.wechatContainer:
                Bundle wechat=new Bundle();
                wechat.putString(ModifyActivity.MODIFY_INTENT_VALUE1,"");
                wechat.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_WECAHT);
                gotoActivityForResult(ModifyActivity.class,wechat,ModifyActivity.MODIFY_CONCERN_WECAHT);
                break;
            case R.id.remarkContainer:
                Bundle remark=new Bundle();
                remark.putString(ModifyActivity.MODIFY_INTENT_VALUE1,"");
                remark.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_REMARK);
                gotoActivityForResult(ModifyActivity.class,remark,ModifyActivity.MODIFY_CONCERN_REMARK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ModifyActivity.MODIFY_CONCERN_TELPHONE:
                break;
            case ModifyActivity.MODIFY_CONCERN_EMAIL:
                break;
            case ModifyActivity.MODIFY_CONCERN_WECAHT:
                break;
            case ModifyActivity.MODIFY_CONCERN_REMARK:
                break;
        }
    }
}
