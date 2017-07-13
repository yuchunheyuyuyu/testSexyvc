package com.qtin.sexyvc.ui.follow.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.follow.detail.di.ConcernDetailModule;
import com.qtin.sexyvc.ui.follow.detail.di.DaggerConcernDetailComponent;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.RoadShowItemBean;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.road.RoadCommentActivity;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.bottomCenterLine)
    View bottomCenterLine;
    @BindView(R.id.commentContainer)
    LinearLayout commentContainer;
    @BindView(R.id.ivAnthStatus)
    ImageView ivAnthStatus;

    private long contact_id;
    private long investor_id;
    private ContactBean contactBean;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private static final int REQUEST_CODE_SELECTED_TYPE = 0x223;


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
        contact_id = getIntent().getExtras().getLong("contact_id");
        investor_id = getIntent().getExtras().getLong("investor_id");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(contact_id);
            }
        });
        tvTitle.setText("");
        mImageLoader = customApplication.getAppComponent().imageLoader();
        mPresenter.query(contact_id);
    }

    private void setValue() {

        if (mPresenter.getUserInfo() != null) {
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + contactBean.getScore_value() + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("还未评价路演");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                } else if (contactBean.getHas_comment() == 0 && contactBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价路演");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            } else {
                if (contactBean.getHas_comment() == 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + contactBean.getScore_value() + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            }
        }

        if(contactBean.getInvestor_uid()==0){
            ivAnthStatus.setVisibility(View.GONE);
        }else{
            ivAnthStatus.setVisibility(View.VISIBLE);
        }

        if (contactBean.getInvestor_id() == 0) {
            tvRight.setVisibility(View.GONE);
            bottomCenterLine.setVisibility(View.GONE);
            commentContainer.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(getResources().getString(R.string.detail));
        }

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
        tvCompany.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this, contactBean.getFund_name()));
        tvPosition.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this, contactBean.getTitle()));

        if (StringUtil.isBlank(contactBean.getPhone()) && StringUtil.isBlank(contactBean.getBackup_phone())) {
            tvTelephone.setText(getResources().getString(R.string.no_known));
        } else if (!StringUtil.isBlank(contactBean.getPhone()) && StringUtil.isBlank(contactBean.getBackup_phone())) {
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getPhone()));
        } else if (StringUtil.isBlank(contactBean.getPhone()) && !StringUtil.isBlank(contactBean.getBackup_phone())) {
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getBackup_phone()));
        } else {
            tvTelephone.setText(StringUtil.getFormatPhone(contactBean.getPhone()) + "/"
                    + StringUtil.getFormatPhone(contactBean.getBackup_phone()));
        }

        tvEmail.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this, contactBean.getEmail()));
        tvWechat.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this, contactBean.getWechat()));
        tvRemark.setText(com.qtin.sexyvc.utils.StringUtil.formatNoKnown(this, contactBean.getRemark()));

        //标签
        if (contactBean.getTags() == null || contactBean.getTags().isEmpty()) {
            flowLayout.setVisibility(View.GONE);
        } else {
            flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(contactBean.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    TextView tv = (TextView) LayoutInflater.from(ConcernDetailActivity.this).inflate(R.layout.item_filter_textview5, flowLayout, false);
                    tv.setText(o.getTag_name());
                    return tv;
                }
            };
            flowLayout.setAdapter(tagAdapter);
        }

        //路演评价
        if (contactBean.getRoad_show() != null) {
            pbProfessionalQualities.setProgress(countRoadPercent(contactBean.getRoad_show().getProfessional()));
            pbRoadEfficiency.setProgress(countRoadPercent(contactBean.getRoad_show().getEfficiency()));
            pbFeedbackSpeed.setProgress(countRoadPercent(contactBean.getRoad_show().getFeedback()));
            pbExperienceShare.setProgress(countRoadPercent(contactBean.getRoad_show().getExperience()));
        } else {
            pbProfessionalQualities.setProgress(0);
            pbRoadEfficiency.setProgress(0);
            pbFeedbackSpeed.setProgress(0);
            pbExperienceShare.setProgress(0);
        }
        tvRating.setText("" + contactBean.getScore());
        ratingScore.setRating10(contactBean.getScore());
        //缺少参数
        tvRateNum.setText(contactBean.getScore_count() + " 人");
    }

    private int countRoadPercent(RoadShowItemBean itemBean) {
        if (itemBean != null) {
            int totle = itemBean.getAgree() + itemBean.getAgainst();
            if (totle == 0) {
                return 0;
            } else {
                return itemBean.getAgree() * 100 / totle;
            }
        }
        return 0;
    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.concernContainer, R.id.commentContainer, R.id.telephoneContainer, R.id.emailContainer, R.id.wechatContainer, R.id.remarkContainer})
    public void onClick(View view) {
        if (contactBean == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                Bundle detail = new Bundle();
                detail.putLong("investor_id", investor_id);
                gotoActivity(InvestorDetailActivity.class, detail);
                break;
            case R.id.concernContainer:
                showBottomDialog("#fe3824", getResources().getString(R.string.set_group),
                        getResources().getString(R.string.cancle_concern),
                        getResources().getString(R.string.cancle),
                        new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                Bundle bundle = new Bundle();
                                bundle.putLong(ConstantUtil.INTENT_ID, investor_id);
                                gotoActivity(SetGroupActivity.class, bundle);
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                mPresenter.cancleFollow(investor_id);
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });

                break;
            case R.id.commentContainer:
                Bundle bundle=new Bundle();
                InvestorInfoBean infoBean=new InvestorInfoBean();
                infoBean.setInvestor_id(contactBean.getInvestor_id());
                infoBean.setFund_id(contactBean.getFund_id());
                infoBean.setFund_name(contactBean.getFund_name());
                infoBean.setInvestor_avatar(contactBean.getInvestor_avatar());

                infoBean.setInvestor_name(contactBean.getInvestor_name());
                infoBean.setInvestor_uid(contactBean.getInvestor_uid());
                infoBean.setTags(contactBean.getTags());

                bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,infoBean);
                gotoActivity(RateActivity.class,bundle);

                //逻辑暂且隐藏起来
              /**  if (mPresenter.getUserInfo() != null) {
                    if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                        if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 1) {
                            showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {

                                        }

                                        @Override
                                        public void onCancle() {

                                        }
                                    });
                        } else if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 0) {
                            gotoRoad();
                        } else if (contactBean.getHas_comment() == 0 && contactBean.getHas_roadshow() == 1) {
                            showBottomOneDialog(getResources().getString(R.string.comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {

                                        }

                                        @Override
                                        public void onCancle() {

                                        }
                                    });
                        } else {
                            gotoActivityFadeForResult(ChooseActivity.class, null, REQUEST_CODE_SELECTED_TYPE);
                        }

                    } else {
                        if (contactBean.getHas_comment() == 0) {

                        } else {
                            showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {

                                        }

                                        @Override
                                        public void onCancle() {

                                        }
                                    });
                        }
                    }
                }
                */

                break;
            case R.id.telephoneContainer:
                Bundle mobile = new Bundle();
                mobile.putLong("contact_id", contact_id);
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE1, contactBean.getPhone());
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE2, contactBean.getBackup_phone());
                mobile.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_CONCERN_TELPHONE);
                gotoActivityForResult(ModifyActivity.class, mobile, ModifyActivity.MODIFY_CONCERN_TELPHONE);
                break;
            case R.id.emailContainer:
                Bundle email = new Bundle();
                email.putLong("contact_id", contact_id);
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE1, contactBean.getEmail());
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE2, contactBean.getBackup_email());
                email.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_CONCERN_EMAIL);
                gotoActivityForResult(ModifyActivity.class, email, ModifyActivity.MODIFY_CONCERN_EMAIL);
                break;
            case R.id.wechatContainer:
                Bundle wechat = new Bundle();
                wechat.putLong("contact_id", contact_id);
                wechat.putString(ModifyActivity.MODIFY_INTENT_VALUE1, contactBean.getWechat());
                wechat.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_CONCERN_WECAHT);
                gotoActivityForResult(ModifyActivity.class, wechat, ModifyActivity.MODIFY_CONCERN_WECAHT);
                break;
            case R.id.remarkContainer:
                Bundle remark = new Bundle();
                remark.putLong("contact_id", contact_id);
                remark.putString(ModifyActivity.MODIFY_INTENT_VALUE1, contactBean.getRemark());
                remark.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_CONCERN_REMARK);
                gotoActivityForResult(ModifyActivity.class, remark, ModifyActivity.MODIFY_CONCERN_REMARK);
                break;
        }
    }

    private void gotoRoad() {
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.INTENT_INDEX, 0);
        bundle.putString(ConstantUtil.INTENT_TITLE, getResources().getString(R.string.evaluate) + contactBean.getInvestor_name());
        bundle.putLong(ConstantUtil.INTENT_ID_INVESTOR, contactBean.getInvestor_id());
        bundle.putLong(ConstantUtil.INTENT_ID_FUND, contactBean.getFund_id());
        gotoActivity(RoadCommentActivity.class, bundle);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_SELECTED_TYPE:
                if (data != null) {
                    int type = data.getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);
                    if (type == ConstantUtil.COMMENT_TYPE_ROAD) {
                        /** Bundle bundle=new Bundle();
                         bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT,type);
                         gotoActivity(CommentObjectActivity.class,bundle);
                         **/
                    } else if (type == ConstantUtil.COMMENT_TYPE_EDIT) {


                    }
                }
                break;
            case ModifyActivity.MODIFY_CONCERN_TELPHONE:
                String phone = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                String backup_phone = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2);
                contactBean.setPhone(phone);
                contactBean.setBackup_phone(backup_phone);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_EMAIL:
                String email = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                String backup_email = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2);
                contactBean.setEmail(email);
                contactBean.setBackup_email(backup_email);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_WECAHT:
                String wechat = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                contactBean.setWechat(wechat);
                setValue();
                break;
            case ModifyActivity.MODIFY_CONCERN_REMARK:
                String remark = data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                contactBean.setRemark(remark);
                setValue();
                break;
        }
    }

    @Override
    public void querySuccess(ContactBean bean) {
        contactBean = bean;
        setValue();
    }

    @Override
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }

    @Override
    public void cancleSuccess(final long investor_id) {
        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Intent intent = new Intent();
                        intent.putExtra(ConstantUtil.INTENT_ID, investor_id);
                        setResult(0, intent);
                        finish();
                    }
                });
    }
}
