package com.qtin.sexyvc.ui.investor;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AddProjectBaseActivity;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.ui.add.CommentObjectActivity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.DialogType;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.OnClickFundListener;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.choose.ChooseActivity;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.investor.bean.InvestorBean;
import com.qtin.sexyvc.ui.investor.di.DaggerInvestorDetailComponent;
import com.qtin.sexyvc.ui.investor.di.InvestorDetailModule;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.request.FollowRequest;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.action.RoadCommentActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.user.position.PositionActivity;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
public class InvestorDetailActivity extends AddProjectBaseActivity<InvestorDetailPresent> implements InvestorDetailContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.headerLine)
    View headerLine;
    @BindView(R.id.headContainer)
    RelativeLayout headContainer;
    @BindView(R.id.ivConcern)
    ImageView ivConcern;
    @BindView(R.id.tvConcern)
    TextView tvConcern;
    @BindView(R.id.ivComent)
    ImageView ivComent;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.lineClaim)
    View lineClaim;
    @BindView(R.id.claimContainer)
    LinearLayout claimContainer;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    private long investor_id;

    private InvestorDetailAdapter mAdapter;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();

    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    private InvestorBean investorBean;
    private static final int REQUEST_CODE_SELECTED_TYPE = 0x223;
    private static final int REQUEST_CODE_CLAIM = 0x225;

    private boolean isFromFund = false;

    private boolean isNeedRefresh = false;
    private boolean isFirstLoadData = true;//是不是本页面第一次加载数据

    private int page_size = 3;
    private int auth_state = 1;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = ConstantUtil.ROAD_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveRoad(CommentEvent commentEvent) {
        investorBean.setHas_roadshow(1);
        setCommentStatus();
    }

    @Subscriber(tag = ConstantUtil.SCORE_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveScore(CommentEvent commentEvent) {
        isNeedRefresh = true;
        investorBean.setHas_score(1);
        investorBean.setScore_value(commentEvent.getScore());

        float totalScore = investorBean.getScore() * investorBean.getScore_count() + commentEvent.getScore();
        int scoreCount = investorBean.getScore_count() + 1;

        investorBean.setScore_count(scoreCount);

        float average = totalScore / scoreCount;
        BigDecimal b = new BigDecimal(average);
        float averageResult = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

        investorBean.setScore(averageResult);
        setCommentStatus();
        mAdapter.notifyDataSetChanged();
    }

    @Subscriber(tag = ConstantUtil.COMMENT_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveComment(CommentEvent commentEvent) {
        investorBean.setHas_comment(1);
        investorBean.setComment_id(commentEvent.getComment_id());
        investorBean.setComment_title(commentEvent.getComment_title());
        setCommentStatus();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerInvestorDetailComponent.builder().appComponent(appComponent).investorDetailModule(new InvestorDetailModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.investor_detail_activity;
    }

    @Override
    protected void initData() {
        ivLeft.setSelected(true);

        mImageLoader = customApplication.getAppComponent().imageLoader();
        investor_id = getIntent().getExtras().getLong("investor_id");
        try {
            isFromFund = getIntent().getExtras().getBoolean("isFromFund");
        } catch (Exception e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID, page_size, auth_state);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InvestorDetailAdapter(this, data, auth_state);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickFundListener(new OnClickFundListener() {
            @Override
            public void onClick() {
                if (investorBean != null && investorBean.getFund_id() != 0) {
                    if (isFromFund) {
                        finish();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putLong("fund_id", investorBean.getFund_id());
                        gotoActivity(FundDetailActivity.class, bundle);
                    }
                }
            }
        });

        maxDistance = (int) DeviceUtils.dpToPixel(this, 122);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mDistance += dy;
                float percent = mDistance * 1f / maxDistance > 1 ? 1 : mDistance * 1f / maxDistance;

                int alpha = (int) (percent * 255);
                Log.e("透明度", "=======透明度=====" + percent);
                //整个背景
                int argb = Color.argb(alpha, 255, 255, 255);
                headContainer.setBackgroundColor(argb);
                //间隔线
                int lineColor = Color.argb(alpha, 224, 224, 226);
                headerLine.setBackgroundColor(lineColor);
                //标题
                int titleColor = Color.argb(alpha, 59, 67, 87);
                tvTitle.setTextColor(titleColor);

                //返回键
                if (mDistance == 0) {
                    ivLeft.setSelected(false);
                    ivLeft.setAlpha(255);
                    ivShare.setSelected(false);
                    ivShare.setAlpha(255);
                } else {
                    ivLeft.setSelected(true);
                    ivLeft.setAlpha(alpha);
                    ivShare.setSelected(true);
                    ivShare.setAlpha(alpha);
                }
            }
        });
        mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID, page_size, auth_state);
        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedRefresh) {
            mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID, page_size, auth_state);
            isNeedRefresh = false;
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


    @Override
    public void querySuccess(CallBackBean backBean) {
        if (isFirstLoadData) {
            //设置导航栏颜色
            ivLeft.setSelected(false);

            UserInfoEntity entity = mPresenter.getUserInfo();
            if (entity != null) {
                if (entity.getHas_project() == 1 && entity.getHas_comment() == 0 && entity.getHas_roadshow() == 0) {
                    int currentScore = DataHelper.getIntergerSF(this, "read_score");
                    currentScore += 20;
                    if (currentScore >= 100) {
                        //清空分数
                        DataHelper.SetIntergerSF(this, "read_score", 0);
                        showHintDialog(entity.getU_phone(), DialogType.TYPE_COMMENT, new ComfirmListerner() {
                            @Override
                            public void onComfirm() {
                                Bundle bundle = new Bundle();
                                bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT, ConstantUtil.COMMENT_TYPE_NONE);
                                gotoActivity(CommentObjectActivity.class, bundle);
                            }
                        });
                    } else {
                        DataHelper.SetIntergerSF(this, "read_score", currentScore);
                    }
                }
            }

            isFirstLoadData = false;
        }

        data.clear();
        if (backBean.getInvestor() != null) {
            investorBean = backBean.getInvestor();
            data.add(investorBean);
            setValue();
            insertLastBrower();
        }
        setClaimStatus();

        if (backBean.getComments() != null && backBean.getComments().getList() != null) {
            data.addAll(backBean.getComments().getList());
        }
        //计算评论总数
        int commentNumber = 0;
        if (backBean.getComments() != null) {
            commentNumber += backBean.getComments().getTotal() + backBean.getComments().getUnauth_count();
        }
        if (backBean.getRoadshows() != null) {
            commentNumber += backBean.getRoadshows().getTotal() + backBean.getRoadshows().getUnauth_count();
        }
        investorBean.setComment_number(commentNumber);

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置认证的状态
     */
    private void setClaimStatus() {
        if (investorBean != null) {
            UserInfoEntity entity = mPresenter.getUserInfo();
            if (investorBean.getInvestor_uid() == 0
                    && entity.getU_auth_type() == ConstantUtil.AUTH_TYPE_INVESTOR
                    && entity.getU_auth_state() == ConstantUtil.AUTH_STATE_UNPASS) {
                claimContainer.setVisibility(View.VISIBLE);
                lineClaim.setVisibility(View.VISIBLE);
            } else {
                claimContainer.setVisibility(View.GONE);
                lineClaim.setVisibility(View.GONE);
            }
        } else {
            claimContainer.setVisibility(View.GONE);
            lineClaim.setVisibility(View.GONE);
        }
    }

    private void insertLastBrower() {
        if (investorBean != null) {
            LastBrowerBean bean = new LastBrowerBean();
            bean.setLocalTime(System.currentTimeMillis());
            bean.setTitle(investorBean.getInvestor_title());
            bean.setInvestor_uid(investorBean.getInvestor_uid());

            bean.setInvestor_name(investorBean.getInvestor_name());
            bean.setInvestor_avatar(investorBean.getInvestor_avatar());
            bean.setFund_name(investorBean.getFund_name());
            bean.setInvestor_id(investorBean.getInvestor_id());

            mPresenter.insertLastBrower(bean);
        }
    }

    @Override
    public void followSuccess() {
        if (investorBean != null) {
            investorBean.setHas_follow(1);
        }
        setConcernStatus();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_INVESTOR);
        bundle.putLong(ConstantUtil.INTENT_ID, investor_id);
        gotoActivity(SetGroupActivity.class, bundle);
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
    public void cancleSuccess(long investor_id) {
        if (investorBean != null) {
            investorBean.setHas_follow(0);
        }
        setConcernStatus();
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

    @Override
    public void showNetErrorView() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContentView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.GONE) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setValue() {
        if (investorBean != null) {
            if(investorBean.getScore_count()==0){
                tvTitle.setText(StringUtil.formatString(investorBean.getInvestor_name()));
            }else{
                tvTitle.setText(StringUtil.formatString(investorBean.getInvestor_name()) + " (" + investorBean.getScore() + ")");
            }
            setConcernStatus();
            setCommentStatus();
        }
    }

    private void setConcernStatus() {
        if (investorBean == null) {
            return;
        }
        if (investorBean.getHas_follow() == 0) {
            ivConcern.setImageResource(R.drawable.icon_bottom_follow);
            tvConcern.setTextColor(getResources().getColor(R.color.barbie_pink_two));
            tvConcern.setText(getResources().getString(R.string.concern));
        } else {
            ivConcern.setImageResource(R.drawable.icon_bottom_menu_copy_2);
            tvConcern.setTextColor(getResources().getColor(R.color.black50));
            tvConcern.setText(getResources().getString(R.string.has_concern));
        }
    }

    private void setCommentStatus() {
        if (investorBean == null) {
            return;
        }
        if (mPresenter.getUserInfo() != null) {
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER
                    || mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {
                if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + investorBean.getScore_value() / 2 + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("还未评价路演");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                } else if (investorBean.getHas_comment() == 0 && investorBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价路演");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            } else {
                if (investorBean.getHas_comment() != 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + investorBean.getScore_value() / 2 + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            }
        }
    }

    @OnClick({R.id.concernContainer, R.id.commentContainer, R.id.ivLeft, R.id.ivShare, R.id.claimContainer,
            R.id.ivErrorStatus,R.id.ivEmptyStatus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivErrorStatus:
                mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID, page_size, auth_state);
                break;
            case R.id.ivEmptyStatus:

                break;
            case R.id.claimContainer:
                showHintDialog();
                break;
            case R.id.concernContainer:
                if (investorBean != null) {
                    if (investorBean.getHas_follow() == 0) {
                        FollowRequest entity = new FollowRequest();
                        ArrayList<Long> group_ids = new ArrayList<Long>();
                        ArrayList<Long> investor_ids = new ArrayList<Long>();
                        investor_ids.add(investor_id);
                        entity.setGroup_ids(group_ids);
                        entity.setInvestor_ids(investor_ids);
                        mPresenter.followInvestor(entity);
                    } else {
                        showBottomDialog("#fe3824", getResources().getString(R.string.set_group),
                                getResources().getString(R.string.cancle_concern),
                                getResources().getString(R.string.cancle),
                                new SelecteListerner() {
                                    @Override
                                    public void onFirstClick() {
                                        dismissBottomDialog();
                                        Bundle bundle = new Bundle();
                                        bundle.putLong(ConstantUtil.INTENT_ID, investor_id);
                                        bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_INVESTOR);
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
                    }
                }

                break;
            case R.id.commentContainer:
                if (investorBean == null) {
                    return;
                }

                doCommentLogic();
                break;
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                if (investorBean != null) {
                    final UMWeb web = new UMWeb(Api.SHARE_BASE_URL+Api.SHARE_INVESTOR + investorBean.getInvestor_id());
                    web.setTitle("【SexyVC】" + investorBean.getInvestor_name());//标题
                    web.setDescription("对于" + investorBean.getInvestor_name() + "，创业者们是这样评价的...");
                    web.setThumb(new UMImage(this, CommonUtil.getAbsolutePath(investorBean.getInvestor_avatar())));  //缩略图

                    showShareDialog(new onShareClick() {
                        @Override
                        public void onClickShare(int platForm) {

                            dismissShareDialog();
                            switch (platForm) {
                                case ConstantUtil.SHARE_WECHAT:
                                    new ShareAction(InvestorDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_WX_CIRCLE:
                                    new ShareAction(InvestorDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_QQ:
                                    new ShareAction(InvestorDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.QQ)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_SINA:
                                    new ShareAction(InvestorDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.SINA)
                                            .setCallback(shareListener).share();
                                    break;
                            }
                        }
                    });
                }


                break;
        }
    }

    private void doCommentLogic() {
        if (mPresenter.getUserInfo() != null) {
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER
                    || mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {

                if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                    if (mPresenter.getUserInfo().getHas_project() == 0) {
                        /**showTwoButtonDialog(getResources().getString(R.string.please_complete_project),
                         getResources().getString(R.string.cancle),
                         getResources().getString(R.string.comfirm),
                         new TwoButtonListerner() {
                        @Override public void leftClick() {
                        dismissTwoButtonDialog();
                        }

                        @Override public void rightClick() {
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

                if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 1) {
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
                } else if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 0) {
                    gotoRoad();
                } else if (investorBean.getHas_comment() == 0 && investorBean.getHas_roadshow() == 1) {
                    showBottomOneDialog(getResources().getString(R.string.comment),
                            new OneButtonListerner() {
                                @Override
                                public void onOptionSelected() {
                                    dismissBottomOneButtonDialog();
                                    if (investorBean.getHas_score() == 0) {
                                        gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                                    } else {
                                        gotoComment();
                                    }
                                }

                                @Override
                                public void onCancle() {
                                    dismissBottomOneButtonDialog();
                                }
                            });
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ChooseActivity.AUTH_TYPE, mPresenter.getUserInfo().getU_auth_type());
                    gotoActivityFadeForResult(ChooseActivity.class, bundle, REQUEST_CODE_SELECTED_TYPE);
                }

            } else {


                if (investorBean.getHas_comment() == 0) {
                    if (investorBean.getHas_score() == 0) {
                        gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                    } else {
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
    private void gotoScore(int intent) {
        gotoActivity(RateActivity.class, getBundle(intent));
    }

    /**
     * 进入评论或者追评
     */
    private void gotoComment() {
        gotoActivity(ReviewActivity.class, getBundle(ConstantUtil.INTENT_TEXT_COMMENT));
    }

    /**
     * 进入路演评价
     */
    private void gotoRoad() {
        Bundle bundle = getBundle(ConstantUtil.INTENT_ROAD_COMMENT);
        bundle.putInt(ConstantUtil.INTENT_INDEX, 0);
        gotoActivity(RoadCommentActivity.class, bundle);
    }

    private Bundle getBundle(int intent) {
        Bundle bundle = new Bundle();
        InvestorInfoBean infoBean = new InvestorInfoBean();
        infoBean.setIntent(intent);
        infoBean.setInvestor_id(investorBean.getInvestor_id());
        infoBean.setFund_id(investorBean.getFund_id());
        infoBean.setFund_name(investorBean.getFund_name());
        infoBean.setInvestor_avatar(investorBean.getInvestor_avatar());
        infoBean.setTitle(investorBean.getInvestor_title());
        infoBean.setInvestor_name(investorBean.getInvestor_name());
        infoBean.setInvestor_uid(investorBean.getInvestor_uid());
        infoBean.setTags(investorBean.getTags());
        infoBean.setHas_comment(investorBean.getHas_comment());
        infoBean.setHas_roadshow(investorBean.getHas_roadshow());
        infoBean.setHas_score(investorBean.getHas_score());
        infoBean.setScore_value(investorBean.getScore_value());
        infoBean.setComment_id(investorBean.getComment_id());
        infoBean.setComment_title(investorBean.getComment_title());
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE, infoBean);
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CLAIM:
                setClaimStatus();
                break;
            case REQUEST_CODE_SELECTED_TYPE:
                if (data != null) {
                    int type = data.getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);
                    if (type == ConstantUtil.COMMENT_TYPE_ROAD) {
                        if (investorBean.getHas_score() == 0) {
                            gotoScore(ConstantUtil.INTENT_ROAD_COMMENT);
                        } else {
                            gotoRoad();
                        }
                    } else if (type == ConstantUtil.COMMENT_TYPE_EDIT) {
                        if (investorBean.getHas_score() == 0) {
                            gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                        } else {
                            gotoComment();
                        }
                    }
                }
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    private Dialog claimDialog;

    protected void showHintDialog() {
        if (investorBean == null) {
            return;
        }
        View view = View.inflate(this, R.layout.app_claim_dialog, null);
        view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmissClaimDialog();
            }
        });
        ImageView ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(StringUtil.formatString(investorBean.getInvestor_name()));
        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_user_s)
                .placeholder(R.drawable.avatar_user_s)
                .url(CommonUtil.getAbsolutePath(investorBean.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(this))
                .imageView(ivAvatar)
                .build());

        view.findViewById(R.id.tvAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmissClaimDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserInfoActivity.INTENT_USER, mPresenter.getUserInfo());
                gotoActivityForResult(PositionActivity.class, bundle, REQUEST_CODE_CLAIM);
            }
        });

        claimDialog = new Dialog(this);
        claimDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        claimDialog.setContentView(view);
        Window regionWindow = claimDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        claimDialog.setCanceledOnTouchOutside(true);
        claimDialog.setCancelable(false);
        claimDialog.show();
    }

    private void dissmissClaimDialog() {
        if (claimDialog != null && claimDialog.isShowing()) {
            claimDialog.dismiss();
            claimDialog = null;
        }
    }
}
