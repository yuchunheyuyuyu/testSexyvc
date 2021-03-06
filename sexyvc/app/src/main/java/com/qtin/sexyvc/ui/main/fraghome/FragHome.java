package com.qtin.sexyvc.ui.main.fraghome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.ui.bean.OnBannerClickListener;
import com.qtin.sexyvc.ui.bean.OnClickMoreInvestorListener;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.comment.list.CommentActivity;
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.di.DaggerFragHomeComponent;
import com.qtin.sexyvc.ui.main.fraghome.di.FragHomeModule;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.search.action.SearchActionActivity;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;
import com.qtin.sexyvc.ui.subject.list.SubjectListActivity;
import com.qtin.sexyvc.ui.web.SimpleWebActivity;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/14.
 */
public class FragHome extends MyBaseFragment<FragHomePresent> implements FragHomeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.searchContainer)
    LinearLayout searchContainer;
    @BindView(R.id.headContainer)
    LinearLayout headContainer;
    @BindView(R.id.homeLine)
    View homeLine;
    @BindView(R.id.ivHeadLogo)
    ImageView ivHeadLogo;
    @BindView(R.id.headLogoContainer)
    RelativeLayout headLogoContainer;
    @BindView(R.id.homeLine2)
    View homeLine2;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    private ArrayList<HomeInterface> data = new ArrayList<>();
    private HomeAdapter mAdapter;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragHomeComponent.builder().appComponent(appComponent).fragHomeModule(new FragHomeModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_home;
    }

    @Override
    protected void init() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new HomeAdapter(mActivity, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnBannerClickItem(new OnBannerClickListener() {
            @Override
            public void onBannerClickItem(BannerEntity bean) {
                long id=0;
                try {
                    id = Long.parseLong(bean.getAction_value());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                if(StringUtil.isBlank(bean.getAction_type())){
                    if(!StringUtil.isBlank(bean.getLink_url())){
                        bundle.putString(ConstantUtil.INTENT_URL,bean.getLink_url());
                        gotoActivity(SimpleWebActivity.class,bundle);
                    }
                }else if("investor".equals(bean.getAction_type())){
                    MainActivity mainActivity= (MainActivity) mActivity;
                    mainActivity.gotoInvestor();
                }else if ("investor_detail".equals(bean.getAction_type())) {
                    bundle.putLong("investor_id", id);
                    gotoActivity(InvestorDetailActivity.class, bundle);
                } else if("fund".equals(bean.getAction_type())){
                    MainActivity mainActivity= (MainActivity) mActivity;
                    mainActivity.gotoInvestor();
                }else if ("fund_detail".equals(bean.getAction_type())) {
                    bundle.putLong("fund_id", id);
                    gotoActivity(FundDetailActivity.class, bundle);
                } else if("comment".equals(bean.getAction_type())){
                    gotoActivity(CommentActivity.class);
                } else if ("comment_detail".equals(bean.getAction_type())) {
                    bundle.putLong("comment_id", id);
                    gotoActivity(CommentDetailActivity.class, bundle);
                } else if("subject".equals(bean.getAction_type())){
                    gotoActivity(SubjectListActivity.class);
                } else if ("subject_detail".equals(bean.getAction_type())) {
                    bundle.putLong("subject_id", id);
                    gotoActivity(SubjectDetailActivity.class, bundle);
                }
            }
        });
        mAdapter.setOnClickMoreInvestorListener(new OnClickMoreInvestorListener() {
            @Override
            public void onClick() {
                MainActivity mainActivity = (MainActivity) mActivity;
                mainActivity.gotoInvestor();
            }
        });
        mPresenter.query();


        maxDistance = (int) DeviceUtils.dpToPixel(mActivity, 140);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistance += dy;
                float percent = mDistance * 1f / maxDistance > 1 ? 1 : mDistance * 1f / maxDistance;
                int alpha = (int) (percent * 255);

                //整个背景
                int argb = Color.argb(alpha, 255, 255, 255);
                headContainer.setBackgroundColor(argb);
                //间隔线
                int lineColor = Color.argb(alpha, 224, 224, 226);
                homeLine.setBackgroundColor(lineColor);
                homeLine2.setBackgroundColor(lineColor);

                //搜索框 #3b4357
                int searchColor = Color.argb((int) (alpha * 0.1), 59, 67, 87);
                GradientDrawable myGrad = (GradientDrawable) searchContainer.getBackground();
                if (mDistance == 0) {
                    myGrad.setColor(Color.parseColor("#b3ffffff"));
                } else {
                    myGrad.setColor(searchColor);
                }

                ivHeadLogo.setAlpha(alpha);
                headLogoContainer.setBackgroundColor(argb);
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
        UiUtils.showToastShort(mActivity, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.bannerView != null) {
            mAdapter.bannerView.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
        if (mAdapter.bannerView != null) {
            mAdapter.bannerView.stopAutoPlay();
        }
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    //测试的时候使用
    private Handler handler = new Handler();

    @Override
    public void dataCallback(ArrayList<HomeInterface> list) {

        data.clear();
        if (list != null && !list.isEmpty()) {
            data.addAll(list);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showNetErrorView() {
        errorLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivEmptyStatus, R.id.ivErrorStatus,R.id.headContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEmptyStatus:
                break;
            case R.id.ivErrorStatus:
                mPresenter.query();
                break;
            case R.id.headContainer:
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.KEY_WORD_INTENT, "");
                bundle.putBoolean(ConstantUtil.INTENT_IS_FOR_RESULT, false);
                bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT, ConstantUtil.TYPE_INVESTOR);
                gotoActivity(SearchActionActivity.class, bundle);
                break;
        }
    }
}
