package com.qtin.sexyvc.ui.follow.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ContactBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.follow.detail.di.ConcernDetailModule;
import com.qtin.sexyvc.ui.follow.detail.di.DaggerConcernDetailComponent;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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


    private long contact_id;
    private long investor_id;
    private ContactBean contactBean;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架


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
        contact_id=getIntent().getExtras().getLong("contact_id");
        investor_id=getIntent().getExtras().getLong("investor_id");

        mImageLoader = customApplication.getAppComponent().imageLoader();
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.detail));

        mPresenter.query(contact_id);

    }

    private void setValue(){
        tvTitle.setText(StringUtil.formatString(contactBean.getInvestor_name()));
        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_blank)
                .placeholder(R.drawable.avatar_blank
                )
                .url(CommonUtil.getAbsolutePath(contactBean.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(this))
                .imageView(ivAvatar)
                .build());
        tvCompany.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this,contactBean.getFund_name()));
        tvPosition.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this,contactBean.getTitle()));

        if(StringUtil.isBlank(contactBean.getPhone())&&StringUtil.isBlank(contactBean.getBackup_phone())){
            tvTelephone.setText(getResources().getString(R.string.no_known));
        }else if(!StringUtil.isBlank(contactBean.getPhone())&&StringUtil.isBlank(contactBean.getBackup_phone())){
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getPhone()));
        }else if(StringUtil.isBlank(contactBean.getPhone())&&!StringUtil.isBlank(contactBean.getBackup_phone())){
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getBackup_phone()));
        }else{
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getPhone())+"/"
                    +StringUtil.getFormatPhone(contactBean.getBackup_phone()));
        }

        tvEmail.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this,contactBean.getEmail()));
        tvWechat.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this,contactBean.getWechat()));
        tvRemark.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this,contactBean.getRemark()));

        //暂时缺字段
        pbProfessionalQualities.setProgress(60);
        ArrayList<FilterEntity> personalTags = new ArrayList<>();
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
        showDialog("加载中");
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

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.concernContainer, R.id.commentContainer, R.id.telephoneContainer, R.id.emailContainer, R.id.wechatContainer, R.id.remarkContainer})
    public void onClick(View view) {
        if(contactBean==null){
            return;
        }

        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                Bundle detail=new Bundle();
                detail.putLong("investor_id",investor_id);
                gotoActivity(InvestorDetailActivity.class,detail);
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
                mobile.putLong("contact_id",contact_id);
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE1,contactBean.getPhone());
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE2,contactBean.getBackup_phone());
                mobile.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_TELPHONE);
                gotoActivityForResult(ModifyActivity.class,mobile,ModifyActivity.MODIFY_CONCERN_TELPHONE);
                break;
            case R.id.emailContainer:
                Bundle email=new Bundle();
                email.putLong("contact_id",contact_id);
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE1,contactBean.getEmail());
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE2,contactBean.getBackup_email());
                email.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_EMAIL);
                gotoActivityForResult(ModifyActivity.class,email,ModifyActivity.MODIFY_CONCERN_EMAIL);
                break;
            case R.id.wechatContainer:
                Bundle wechat=new Bundle();
                wechat.putLong("contact_id",contact_id);
                wechat.putString(ModifyActivity.MODIFY_INTENT_VALUE1,contactBean.getWechat());
                wechat.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_WECAHT);
                gotoActivityForResult(ModifyActivity.class,wechat,ModifyActivity.MODIFY_CONCERN_WECAHT);
                break;
            case R.id.remarkContainer:
                Bundle remark=new Bundle();
                remark.putLong("contact_id",contact_id);
                remark.putString(ModifyActivity.MODIFY_INTENT_VALUE1,contactBean.getRemark());
                remark.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_CONCERN_REMARK);
                gotoActivityForResult(ModifyActivity.class,remark,ModifyActivity.MODIFY_CONCERN_REMARK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        switch(requestCode){
            case ModifyActivity.MODIFY_CONCERN_TELPHONE:
                String phone=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                String backup_phone=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2);
                contactBean.setPhone(phone);
                contactBean.setBackup_phone(backup_phone);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_EMAIL:
                String email=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                String backup_email=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2);
                contactBean.setEmail(email);
                contactBean.setBackup_email(backup_email);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_WECAHT:
                String wechat=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                contactBean.setWechat(wechat);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_REMARK:
                String remark=data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                contactBean.setRemark(remark);
                setValue();
                break;
        }
    }

    @Override
    public void querySuccess(ContactBean bean) {
        contactBean=bean;
        setValue();
    }
}
