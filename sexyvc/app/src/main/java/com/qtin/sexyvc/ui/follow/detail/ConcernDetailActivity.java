package com.qtin.sexyvc.ui.follow.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.qtin.sexyvc.common.AddProjectBaseActivity;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.ContactBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.choose.ChooseActivity;
import com.qtin.sexyvc.ui.follow.detail.di.ConcernDetailModule;
import com.qtin.sexyvc.ui.follow.detail.di.DaggerConcernDetailComponent;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.RoadShowItemBean;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.request.UnFollowContactRequest;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.action.RoadCommentActivity;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ConcernDetailActivity extends AddProjectBaseActivity<ConcernDetailPresent> implements ConcernDetailContract.View {

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
    RatingBar ratingScore;
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
    @BindView(R.id.cardView)
    View cardView;

    private long contact_id;
    private long investor_id;
    private ContactBean contactBean;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private static final int REQUEST_CODE_SELECTED_TYPE = 0x223;

    private boolean isNeedRefresh=false;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscriber(tag = ConstantUtil.ROAD_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveRoad(CommentEvent commentEvent){
        contactBean.setHas_roadshow(1);
        setCommentStatus();

    }

    @Subscriber(tag = ConstantUtil.SCORE_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveScore(CommentEvent commentEvent){
        isNeedRefresh=true;

        contactBean.setHas_score(1);
        contactBean.setScore_value(commentEvent.getScore());
        float totalScore=contactBean.getScore()*contactBean.getScore_count()+commentEvent.getScore();
        int scoreCount=contactBean.getScore_count()+1;

        contactBean.setScore_count(scoreCount);
        float average=totalScore/scoreCount;
        BigDecimal b = new BigDecimal(average);
        float averageResult=b.setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
        contactBean.setScore(averageResult);

        setScore();
        setCommentStatus();
    }

    @Subscriber(tag = ConstantUtil.COMMENT_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveComment(CommentEvent commentEvent){
        contactBean.setHas_comment(1);
        contactBean.setComment_id(commentEvent.getComment_id());
        contactBean.setComment_title(commentEvent.getComment_title());
        setCommentStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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

        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeedRefresh){
            mPresenter.query(contact_id);
            isNeedRefresh=false;
        }

    }

    private void setCommentStatus(){
        if (mPresenter.getUserInfo() != null) {
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + contactBean.getScore_value()/2 + "星)");
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
                if (contactBean.getHas_comment() != 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + contactBean.getScore_value()/2 + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            }
        }
    }

    private void setValue() {
        setCommentStatus();
        if(contactBean.getInvestor_uid()==0){
            ivAnthStatus.setVisibility(View.GONE);
        }else{
            ivAnthStatus.setVisibility(View.VISIBLE);
        }

        if (contactBean.getInvestor_id() == 0) {
            tvRight.setVisibility(View.GONE);
            bottomCenterLine.setVisibility(View.GONE);
            commentContainer.setVisibility(View.GONE);

            cardView.setVisibility(View.GONE);

        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(getResources().getString(R.string.detail));
            cardView.setVisibility(View.VISIBLE);
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
        tvCompany.setText(AppStringUtil.formatNoKnown(this, contactBean.getFund_name()));
        tvPosition.setText(AppStringUtil.formatNoKnown(this, contactBean.getTitle()));

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

        tvEmail.setText(AppStringUtil.formatNoKnown(this, contactBean.getEmail()));
        tvWechat.setText(AppStringUtil.formatNoKnown(this, contactBean.getWechat()));
        tvRemark.setText(AppStringUtil.formatNoKnown(this, contactBean.getRemark()));

        //标签
        if (contactBean.getTags() == null || contactBean.getTags().isEmpty()) {
            flowLayout.setVisibility(View.GONE);
        } else {
            flowLayout.setVisibility(View.VISIBLE);
            TagAdapter tagAdapter = new TagAdapter<TagEntity>(contactBean.getTags()) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity o) {
                    View view = LayoutInflater.from(ConcernDetailActivity.this).inflate(R.layout.item_filter_textview5_ll, flowLayout, false);
                    TextView tv= (TextView) view.findViewById(R.id.tvContent);
                    tv.setText(o.getTag_name());
                    return view;
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
        setScore();
    }

    private void setScore(){
        tvRating.setText("" + contactBean.getScore());
        ratingScore.setRating(contactBean.getScore());
        //缺少参数
        tvRateNum.setText(contactBean.getScore_count() + " 人");
    }

    private int countRoadPercent(RoadShowItemBean itemBean) {
        if(itemBean==null){
            itemBean=new RoadShowItemBean();
        }

        itemBean.setAgainst(itemBean.getAgainst()+5);
        itemBean.setAgree(itemBean.getAgree()+5);

        int totle = itemBean.getAgree() + itemBean.getAgainst();
        if (totle == 0) {
            return 0;
        } else {
            return itemBean.getAgree() * 100 / totle;
        }
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

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.concernContainer, R.id.commentContainer, R.id.telephoneContainer,
            R.id.emailContainer, R.id.wechatContainer, R.id.remarkContainer,R.id.fundContainer})
    public void onClick(View view) {
        if (contactBean == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.fundContainer:
                if(contactBean!=null){
                    if (contactBean.getInvestor_id() != 0&&contactBean.getFund_id()!=0) {
                        Bundle bundle=new Bundle();
                        bundle.putLong("fund_id",contactBean.getFund_id());
                        gotoActivity(FundDetailActivity.class,bundle);
                    }
                }
                break;
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
                                bundle.putLong(ConstantUtil.INTENT_ID, contact_id);
                                bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_CONTACT);
                                gotoActivity(SetGroupActivity.class, bundle);
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                UnFollowContactRequest request=new UnFollowContactRequest();
                                ArrayList<Long> contact_ids=new ArrayList<Long>();
                                contact_ids.add(contact_id);

                                request.setGroup_id(0);
                                request.setContact_ids(contact_ids);
                                mPresenter.cancleFollow(request);
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });

                break;
            case R.id.commentContainer:
                doCommentLogic();
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

    private void doCommentLogic(){
        if (mPresenter.getUserInfo() != null) {
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER
                    ||mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {
                if(mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER){
                    if(mPresenter.getUserInfo().getHas_project()==0){
                        /**showTwoButtonDialog(getResources().getString(R.string.please_complete_project),
                         getResources().getString(R.string.cancle),
                         getResources().getString(R.string.comfirm),
                         new TwoButtonListerner() {
                        @Override
                        public void leftClick() {
                        dismissTwoButtonDialog();
                        }

                        @Override
                        public void rightClick() {
                        dismissTwoButtonDialog();
                        Bundle bundle=new Bundle();
                        bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT,false);
                        gotoActivity(AddProjectActivity.class,bundle);
                        }
                        });*/
                        showProjectDialog(new OnProjectComfirmListener() {
                            @Override
                            public void onComfirm(ProjectBean projectBean) {
                                mPresenter.createProject(projectBean);
                            }
                        });
                        return;
                    }
                }

                if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 1) {
                    showBottomOneDialog(getResources().getString(R.string.plus_comment),
                            new OneButtonListerner() {
                                @Override
                                public void onOptionSelected() {
                                    dismissBottomOneButtonDialog();
                                    gotoComment();
                                }

                                @Override
                                public void onCancle() {
                                    dismissBottomOneButtonDialog();
                                }
                            });
                } else if (contactBean.getHas_comment() == 1 && contactBean.getHas_roadshow() == 0) {
                    gotoRoad();
                } else if (contactBean.getHas_comment() == 0 && contactBean.getHas_roadshow() == 1) {
                    showBottomOneDialog(getResources().getString(R.string.comment),
                            new OneButtonListerner() {
                                @Override
                                public void onOptionSelected() {
                                    dismissBottomOneButtonDialog();
                                    if(contactBean.getHas_score()==0){
                                        gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                                    }else{
                                        gotoComment();
                                    }
                                }

                                @Override
                                public void onCancle() {
                                    dismissBottomOneButtonDialog();
                                }
                            });
                } else {
                    Bundle bundle=new Bundle();
                    bundle.putInt(ChooseActivity.AUTH_TYPE,mPresenter.getUserInfo().getU_auth_type());
                    gotoActivityFadeForResult(ChooseActivity.class,bundle,REQUEST_CODE_SELECTED_TYPE);
                }

            } else {
                if (contactBean.getHas_comment() == 0) {
                    if(contactBean.getHas_score()==0){
                        gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                    }else{
                        gotoComment();
                    }
                } else {
                    showBottomOneDialog(getResources().getString(R.string.plus_comment),
                            new OneButtonListerner() {
                                @Override
                                public void onOptionSelected() {
                                    dismissBottomOneButtonDialog();
                                    gotoComment();
                                }

                                @Override
                                public void onCancle() {
                                    dismissBottomOneButtonDialog();
                                }
                            });
                }
            }
        }
    }

    /**
     * 进入评分
     */
    private void gotoScore(int intent){
        gotoActivity(RateActivity.class,getBundle(intent));
    }

    /**
     * 进入评论或者追评
     */
    private void gotoComment(){
        gotoActivity(ReviewActivity.class,getBundle(ConstantUtil.INTENT_TEXT_COMMENT));
    }

    /**
     * 进入路演评价
     */
    private void gotoRoad() {
        Bundle bundle=getBundle(ConstantUtil.INTENT_ROAD_COMMENT);
        bundle.putInt(ConstantUtil.INTENT_INDEX,0);
        gotoActivity(RoadCommentActivity.class, bundle);
    }

    private Bundle getBundle(int intent){
        Bundle bundle=new Bundle();
        InvestorInfoBean infoBean=new InvestorInfoBean();
        infoBean.setIntent(intent);
        infoBean.setInvestor_id(contactBean.getInvestor_id());
        infoBean.setFund_id(contactBean.getFund_id());
        infoBean.setFund_name(contactBean.getFund_name());
        infoBean.setInvestor_avatar(contactBean.getInvestor_avatar());
        infoBean.setTitle(contactBean.getTitle());
        infoBean.setInvestor_name(contactBean.getInvestor_name());
        infoBean.setInvestor_uid(contactBean.getInvestor_uid());
        infoBean.setTags(contactBean.getTags());
        infoBean.setHas_comment(contactBean.getHas_comment());
        infoBean.setHas_roadshow(contactBean.getHas_roadshow());
        infoBean.setHas_score(contactBean.getHas_score());
        infoBean.setScore_value(contactBean.getScore_value());
        infoBean.setComment_id(contactBean.getComment_id());
        infoBean.setComment_title(contactBean.getComment_title());
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,infoBean);
        return bundle;
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
                        if(contactBean.getHas_score()==0){
                            gotoScore(ConstantUtil.INTENT_ROAD_COMMENT);
                        }else{
                            gotoRoad();
                        }

                    } else if (type == ConstantUtil.COMMENT_TYPE_EDIT) {
                        if(contactBean.getHas_score()==0){
                            gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                        }else {
                            gotoComment();
                        }
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

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_DOMAIN:
                domainData.clear();
                domainData.addAll(list);
                break;
            case TYPE_STAGE:
                initStageData(list);
                break;
        }
    }

    @Override
    public void onCreateSuccess(ProjectBean bean) {
        dismissProjectDialog();

        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        doCommentLogic();
                    }
                });
    }
}
