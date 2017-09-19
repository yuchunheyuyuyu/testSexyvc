package com.qtin.sexyvc.ui.fund.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.ui.add.CommentObjectActivity;
import com.qtin.sexyvc.ui.bean.DialogType;
import com.qtin.sexyvc.ui.bean.FundFollowRequest;
import com.qtin.sexyvc.ui.bean.FundUnFollowRequest;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBean;
import com.qtin.sexyvc.ui.fund.detail.di.DaggerFundDetailComponent;
import com.qtin.sexyvc.ui.fund.detail.di.FundDetailModule;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class FundDetailActivity extends MyBaseActivity<FundDetailPresent> implements FundDetailContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.headerLine)
    View headerLine;
    @BindView(R.id.headContainer)
    RelativeLayout headContainer;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.ivConcern)
    ImageView ivConcern;
    @BindView(R.id.tvConcern)
    TextView tvConcern;
    @BindView(R.id.concernContainer)
    LinearLayout concernContainer;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();
    private FundDetailAdapter mAdapter;

    private long fund_id;
    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    private boolean isFirstLoadData = true;//是不是本页面第一次加载数据
    private FundDetailBean fundDetailBean;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFundDetailComponent.builder().appComponent(appComponent).fundDetailModule(new FundDetailModule(this)).build().inject(this);
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
        return R.layout.fund_detail_activity;
    }

    @Override
    protected void initData() {
        ivLeft.setSelected(true);
        fund_id = getIntent().getExtras().getLong("fund_id");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(fund_id, ConstantUtil.DEFALUT_ID);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FundDetailAdapter(this, data);
        recyclerView.setAdapter(mAdapter);

        maxDistance = (int) DeviceUtils.dpToPixel(this, 110);
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

        mPresenter.query(fund_id, ConstantUtil.DEFALUT_ID);
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

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.ivShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                if (fundDetailBean != null) {
                    final UMWeb web = new UMWeb(Api.SHARE_FUND + fundDetailBean.getFund_id());
                    web.setTitle("【SexyVC】" + fundDetailBean.getFund_name());//标题
                    web.setDescription("对于" + fundDetailBean.getFund_name() + "，创业者们是这样评价的...");
                    web.setThumb(new UMImage(this, CommonUtil.getAbsolutePath(fundDetailBean.getFund_logo())));  //缩略图

                    showShareDialog(new onShareClick() {
                        @Override
                        public void onClickShare(int platForm) {

                            dismissShareDialog();
                            switch (platForm) {
                                case ConstantUtil.SHARE_WECHAT:
                                    new ShareAction(FundDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_WX_CIRCLE:
                                    new ShareAction(FundDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_QQ:
                                    new ShareAction(FundDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.QQ)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_SINA:
                                    new ShareAction(FundDetailActivity.this)
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

    @Override
    public void querySuccess(FundDetailBackBean bean) {
        if (isFirstLoadData) {
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
        if (bean.getFund() != null) {
            fundDetailBean = bean.getFund();
            data.add(bean.getFund());
            tvTitle.setText(StringUtil.formatString(bean.getFund().getFund_name()) + " (" + bean.getFund().getScore() + ")");
        }

        if (bean.getComments() != null && bean.getComments().getList() != null) {
            data.addAll(bean.getComments().getList());
        }
        //计算评论总数
        int commentNumber = 0;
        if (bean.getComments() != null) {
            commentNumber += bean.getComments().getTotal() + bean.getComments().getUnauth_count();
        }
        if (bean.getRoadshows() != null) {
            commentNumber += bean.getRoadshows().getTotal() + bean.getRoadshows().getUnauth_count();
        }
        fundDetailBean.setComment_number(commentNumber);

        mAdapter.notifyDataSetChanged();
        //关注
        setConcernStatus();
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
    public void followSuccess() {
        if (fundDetailBean != null) {
            fundDetailBean.setHas_follow(1);
        }
        setConcernStatus();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_FUND);
        bundle.putLong(ConstantUtil.INTENT_ID, fund_id);
        gotoActivity(SetGroupActivity.class, bundle);
    }

    @Override
    public void cancleSuccess() {
        if (fundDetailBean != null) {
            fundDetailBean.setHas_follow(0);
        }
        setConcernStatus();
    }

    private void setConcernStatus() {
        if (fundDetailBean == null) {
            concernContainer.setVisibility(View.GONE);
        } else {
            concernContainer.setVisibility(View.VISIBLE);
            if (fundDetailBean.getHas_follow() == 0) {
                ivConcern.setImageResource(R.drawable.icon_bottom_follow);
                tvConcern.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                tvConcern.setText(getResources().getString(R.string.concern));
            } else {
                ivConcern.setImageResource(R.drawable.icon_bottom_menu_copy_2);
                tvConcern.setTextColor(getResources().getColor(R.color.black50));
                tvConcern.setText(getResources().getString(R.string.has_concern));
            }
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

    @Override
    public void finish() {
        if (fundDetailBean != null && fundDetailBean.getHas_follow() == 0) {
            Intent intent = new Intent();
            intent.putExtra(ConstantUtil.INTENT_ID, fund_id);
            setResult(0, intent);
        }
        super.finish();
    }

    @OnClick(R.id.concernContainer)
    public void onClick() {
        if (fundDetailBean != null) {
            if (fundDetailBean.getHas_follow() == 0) {
                FundFollowRequest request = new FundFollowRequest();
                request.setObject_type(ConstantUtil.OBJECT_TYPE_FUND);
                ArrayList<Long> group_ids = new ArrayList<>();
                ArrayList<Long> object_ids = new ArrayList<>();
                object_ids.add(fundDetailBean.getFund_id());
                request.setGroup_ids(group_ids);
                request.setObject_ids(object_ids);
                mPresenter.followFund(request);
            } else {
                showBottomDialog("#fe3824", getResources().getString(R.string.set_group),
                        getResources().getString(R.string.cancle_concern),
                        getResources().getString(R.string.cancle),
                        new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                Bundle bundle = new Bundle();
                                bundle.putLong(ConstantUtil.INTENT_ID, fund_id);
                                bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_FUND);
                                gotoActivity(SetGroupActivity.class, bundle);
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                FundUnFollowRequest request = new FundUnFollowRequest();
                                request.setObject_type(ConstantUtil.OBJECT_TYPE_FUND);
                                ArrayList<Long> group_ids = new ArrayList<Long>();
                                ArrayList<Long> object_ids = new ArrayList<Long>();
                                object_ids.add(fundDetailBean.getFund_id());
                                request.setGroup_ids(group_ids);
                                request.setObject_ids(object_ids);
                                mPresenter.cancleFollow(request);
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });
            }
        }
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
}
