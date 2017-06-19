package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.main.fragInvestor.di.DaggerFragInvestorComponent;
import com.qtin.sexyvc.ui.main.fragInvestor.di.FragInvestorModule;
import com.qtin.sexyvc.ui.widget.DropDownMenu;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/14.
 */
public class FragInvestor extends MyBaseFragment<FragInvestorPresent> implements FragInvestorContract.View {

    private List<View> popupViews = new ArrayList<>();
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private String headers[] = {"评分", "行业", "轮次"};
    private ArrayList<FilterEntity> efficiencyData = new ArrayList<>();
    private ArrayList<FilterEntity> industryData = new ArrayList<>();
    private ArrayList<FilterEntity> turnData = new ArrayList<>();


    private String efficiencyList[] = {"综合评分", "专业素养", "路演效率", "反馈速度", "经验分享", "评论数"};
    private String industryList[] = {"不限", "教育", "旅游", "金融", "智能硬件",
            "游戏", "体育运动", "文化娱乐", "广告营销", "人力招聘",
            "社交网络", "法律财税", "软件工具", "本地生活", "消费零售",
            "交通出行", "房产家居", "医疗健康", "生物工程", "环保能源",
            "新材料", "航空航天", "工业制造", "物流供应链", "农林渔牧",
            "企业服务", "其他"};
    public String[] turnList = {"不限", "种子轮", "天使轮", "Pre-A", "A 轮",
            "A+", "B 轮", "B+", "C 轮", "D 轮及以后",
            "新三板前", "新三板后", "IPO 前", "IPO 后", "收购"};


    private ContentViewHolder contentViewHolder;
    private ArrayList<InvestorEntity> data = new ArrayList<>();
    private InvestorAdapter mAdapter;

    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段
    public static final int TYPE_RATE = 0x003;//评分

    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;
    private EfficiencyAdapter ratingAdapter;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragInvestorComponent.builder().appComponent(appComponent).fragInvestorModule(new FragInvestorModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.item_search_filter;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.swipe_recycleview, null);
        contentViewHolder = new ContentViewHolder(contentView);
        configRecycleView();
        addEfficiency();//路演效率
        addIndustry();//行业筛选条件
        addTurn();//轮次
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_RATE);
    }

    private void addIndustry() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_flow, null);
        final TagFlowLayout flowLayout = (TagFlowLayout) view.findViewById(R.id.flowLayout);

        view.findViewById(R.id.tvResetFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.findViewById(R.id.tvComfirmFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        domainAdapter = new TagAdapter<FilterEntity>(industryData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_filter_textview, flowLayout, false);
                tv.setText(o.getType_name());
                return tv;
            }
        };
        flowLayout.setAdapter(domainAdapter);

        popupViews.add(view);
    }

    private void addTurn() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_flow, null);
        final TagFlowLayout flowLayout = (TagFlowLayout) view.findViewById(R.id.flowLayout);

        view.findViewById(R.id.tvResetFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.findViewById(R.id.tvComfirmFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        stageAdapter = new TagAdapter<FilterEntity>(turnData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_filter_textview, flowLayout, false);
                tv.setText(o.getType_name());
                return tv;
            }
        };

        flowLayout.setAdapter(stageAdapter);
        popupViews.add(view);
    }


    private void addEfficiency() {

        RecyclerView efficiencyRecycle = new RecyclerView(getActivity());
        efficiencyRecycle.setLayoutManager(new LinearLayoutManager(mActivity));
        ratingAdapter = new EfficiencyAdapter(getActivity(), efficiencyData);
        efficiencyRecycle.setAdapter(ratingAdapter);
        popupViews.add(efficiencyRecycle);
    }

    private void configRecycleView() {
        contentViewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getInvestorData();
            }
        });
        contentViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contentViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new InvestorAdapter(mActivity, data);
        contentViewHolder.recyclerView.setAdapter(mAdapter);
        initPaginate();
        //获取数据
        mPresenter.getInvestorData();
    }

    private void initPaginate() {

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

    @Override
    public Context getContext() {
        return mActivity;
    }

    //测试的时候使用
    private Handler handler = new Handler();

    @Override
    public void dataCallback(ArrayList<InvestorEntity> list) {
        data.clear();
        if (list != null && !list.isEmpty()) {
            data.addAll(list);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                contentViewHolder.swipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_DOMAIN:
                industryData.clear();
                industryData.addAll(list);
                domainAdapter.notifyDataChanged();
                break;
            case TYPE_STAGE:
                turnData.clear();
                turnData.addAll(list);
                stageAdapter.notifyDataChanged();
                break;
            case TYPE_RATE:
                efficiencyData.clear();
                efficiencyData.addAll(list);
                ratingAdapter.notifyDataSetChanged();
                break;
        }
    }

    @OnClick(R.id.searchContainer)
    public void onClick() {

    }

    static class ContentViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.swipeRefreshLayout)
        SwipeRefreshLayout swipeRefreshLayout;

        ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
