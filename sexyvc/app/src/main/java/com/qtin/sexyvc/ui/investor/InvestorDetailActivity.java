package com.qtin.sexyvc.ui.investor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.choose.ChooseActivity;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.investor.bean.InvestorBean;
import com.qtin.sexyvc.ui.investor.di.DaggerInvestorDetailComponent;
import com.qtin.sexyvc.ui.investor.di.InvestorDetailModule;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.request.FollowRequest;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.RoadCommentActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class InvestorDetailActivity extends MyBaseActivity<InvestorDetailPresent> implements InvestorDetailContract.View {

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
    private long investor_id;

    private InvestorDetailAdapter mAdapter;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();

    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    private InvestorBean investorBean;
    private static final int REQUEST_CODE_SELECTED_TYPE = 0x223;

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
    public void onReceiveRoad(CommentEvent commentEvent){
        investorBean.setHas_roadshow(1);
        setCommentStatus();
    }

    @Subscriber(tag = ConstantUtil.SCORE_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveScore(CommentEvent commentEvent){
        investorBean.setHas_score(1);
        investorBean.setScore_value(commentEvent.getScore());
        setCommentStatus();
    }

    @Subscriber(tag = ConstantUtil.COMMENT_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveComment(CommentEvent commentEvent){
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
    protected int setContentViewId() {
        return R.layout.investor_detail_activity;
    }

    @Override
    protected void initData() {
        investor_id = getIntent().getExtras().getLong("investor_id");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InvestorDetailAdapter(this, data);
        recyclerView.setAdapter(mAdapter);

        mPresenter.query(investor_id, ConstantUtil.DEFALUT_ID);

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
                } else {
                    ivLeft.setSelected(true);
                    ivLeft.setAlpha(alpha);
                }
            }
        });
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
        data.clear();
        if (backBean.getInvestor() != null) {
            investorBean = backBean.getInvestor();
            data.add(investorBean);
            setValue();
            insertLastBrower();
        }

        if (backBean.getComments() != null && backBean.getComments().getList() != null) {
            data.addAll(backBean.getComments().getList());
        }
        mAdapter.notifyDataSetChanged();
    }

    private void insertLastBrower(){
        if(investorBean!=null){
            LastBrowerBean bean=new LastBrowerBean();
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
        if(investorBean!=null){
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
        if(investorBean!=null){
            investorBean.setHas_follow(0);
        }
        setConcernStatus();
    }

    private void setValue() {
        if (investorBean != null) {
            tvTitle.setText(StringUtil.formatString(investorBean.getInvestor_name()));
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
            if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 1) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + investorBean.getScore_value() + "星)");
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
                if (investorBean.getHas_comment() == 0) {
                    ivComent.setVisibility(View.GONE);
                    tvComment.setText("已评价(" + investorBean.getScore_value() + "星)");
                    tvComment.setTextColor(getResources().getColor(R.color.black50));
                } else {
                    ivComent.setVisibility(View.VISIBLE);
                    tvComment.setText("评价");
                    tvComment.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }
            }
        }
    }

    @OnClick({R.id.concernContainer, R.id.commentContainer, R.id.ivLeft, R.id.ivShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.concernContainer:
                if(investorBean!=null){
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
                if (mPresenter.getUserInfo() != null) {
                    if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                        if(mPresenter.getUserInfo().getHas_project()==0){
                            showTwoButtonDialog(getResources().getString(R.string.please_complete_project),
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
                                    });
                            return;
                        }


                        if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 1) {
                            showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {
                                            dismissBottomOneButtonDialog();
                                            gotoComment(true);
                                        }

                                        @Override
                                        public void onCancle() {
                                            dismissBottomOneButtonDialog();
                                        }
                                    });
                        } else if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 0) {
                            gotoRoad(true);
                        } else if (investorBean.getHas_comment() == 0 && investorBean.getHas_roadshow() == 1) {
                            showBottomOneDialog(getResources().getString(R.string.comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {
                                            dismissBottomOneButtonDialog();
                                            if(investorBean.getHas_score()==0){
                                                gotoScore(false);
                                            }else{
                                                gotoComment(false);
                                            }
                                        }

                                        @Override
                                        public void onCancle() {
                                            dismissBottomOneButtonDialog();
                                        }
                                    });
                        } else {
                            gotoActivityForResult(ChooseActivity.class,REQUEST_CODE_SELECTED_TYPE);
                        }

                    } else {
                        if (investorBean.getHas_comment() == 0) {
                            if(investorBean.getHas_score()==0){
                                gotoScore(false);
                            }else{
                                gotoComment(false);
                            }
                        } else {
                            showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                    new OneButtonListerner() {
                                        @Override
                                        public void onOptionSelected() {
                                            dismissBottomOneButtonDialog();
                                            gotoComment(true);
                                        }

                                        @Override
                                        public void onCancle() {
                                            dismissBottomOneButtonDialog();
                                        }
                                    });
                        }
                    }
                }


                break;
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                break;
        }
    }
    /**
     * 进入评分
     * @param isAppend
     */
    private void gotoScore(boolean isAppend){
        gotoActivity(RateActivity.class,getBundle(isAppend));
    }

    /**
     * 进入评论或者追评
     * @param isAppend
     */
    private void gotoComment(boolean isAppend){
        gotoActivity(ReviewActivity.class,getBundle(isAppend));
    }

    /**
     * 进入路演评价
     * @param isAppend
     */
    private void gotoRoad(boolean isAppend) {
        Bundle bundle=getBundle(isAppend);
        bundle.putInt(ConstantUtil.INTENT_INDEX,0);
        gotoActivity(RoadCommentActivity.class, getBundle(isAppend));
    }

    private Bundle getBundle(boolean isAppend){
        Bundle bundle=new Bundle();
        InvestorInfoBean infoBean=new InvestorInfoBean();
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
        infoBean.setAppend(isAppend);
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
                        gotoRoad(false);
                    } else if (type == ConstantUtil.COMMENT_TYPE_EDIT) {
                        gotoScore(false);
                    }
                }
                break;
        }
    }
}
