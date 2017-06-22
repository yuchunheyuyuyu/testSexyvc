package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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

    private ContentViewHolder contentViewHolder;

    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段
    public static final int TYPE_RATE = 0x003;//评分

    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;
    private EfficiencyAdapter ratingAdapter;
    private boolean hasLoadedAllItems;

    private int page=1;
    private int page_size=15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private ArrayList<InvestorEntity> data = new ArrayList<>();
    private InvestorAdapter mAdapter;

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
                page=1;
                mPresenter.getInvestorData(page,page_size);
            }
        });
        contentViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contentViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InvestorAdapter(mRootView.getContext(), data);
        contentViewHolder.recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                ArrayList<Long> group_ids=new ArrayList<Long>();
                group_ids.add(3L);
                group_ids.add(4L);
                group_ids.add(6L);
                mPresenter.changeGroup(data.get(position).getInvestor_id(),group_ids);
            }
        });
        initPaginate();
        //获取数据
        mPresenter.getInvestorData(page,page_size);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.getInvestorData(page,page_size);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(contentViewHolder.recyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        contentViewHolder.swipeRefreshLayout.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        contentViewHolder.swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void startLoadMore() {
        isLoadingMore=true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore=false;
    }

    @Override
    public void querySuccess(InvestorBean bean) {
        if(page==1){
            data.clear();
        }
        if(bean.getList()!=null){
            data.addAll(bean.getList());
        }
        if(bean.getTotal()>data.size()){
            hasLoadedAllItems=false;
        }else{
            hasLoadedAllItems=true;
        }
        mAdapter.notifyDataSetChanged();
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
