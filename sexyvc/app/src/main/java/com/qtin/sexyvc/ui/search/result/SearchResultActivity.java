package com.qtin.sexyvc.ui.search.result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.FundBackEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.main.fragInvestor.EfficiencyAdapter;
import com.qtin.sexyvc.ui.main.fragInvestor.InvestorAdapter;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.InvestorRequest;
import com.qtin.sexyvc.ui.search.action.SearchActionActivity;
import com.qtin.sexyvc.ui.search.result.di.DaggerSearchResultComponent;
import com.qtin.sexyvc.ui.search.result.di.SearchResultModule;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.DropDownMenu;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SearchResultActivity extends MyBaseActivity<SearchResultPresent> implements SearchResultContract.View {


    @BindView(R.id.tvChange)
    TextView tvChange;
    @BindView(R.id.tvChangeHint)
    TextView tvChangeHint;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private List<View> popupViews = new ArrayList<>();
    private String headers[] = {"评分", "行业", "轮次"};
    private ArrayList<FilterEntity> efficiencyData = new ArrayList<>();
    private ArrayList<FilterEntity> industryData = new ArrayList<>();
    private ArrayList<FilterEntity> turnData = new ArrayList<>();

    private TagFlowLayout domainFlowLayout;
    private TagFlowLayout stageFlowLayout;

    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段
    public static final int TYPE_RATE = 0x003;//评分

    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;
    private EfficiencyAdapter ratingAdapter;
    private boolean hasLoadedAllItems;

    private int page = 1;
    private final int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();
    private InvestorAdapter mAdapter;

    private int searchType;
    private ContentViewHolder contentViewHolder;
    private String keyWord;

    private static final int REQUEST_CODE_SEARCH=0x001;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchResultComponent.builder().appComponent(appComponent).searchResultModule(new SearchResultModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initData() {
        searchType=getIntent().getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);
        keyWord=getIntent().getExtras().getString(ConstantUtil.KEY_WORD_INTENT);
        tvChangeHint.setText(keyWord);

        if(searchType==ConstantUtil.TYPE_FUND){
            tvChange.setText(getResources().getString(R.string.fund));
        }else{
            tvChange.setText(getResources().getString(R.string.investor));
        }

        View contentView = LayoutInflater.from(this).inflate(R.layout.recycleview, null);
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

        search();
    }

    private void search(){
        InvestorRequest request=new InvestorRequest();
        request.setPage(page);
        request.setPage_size(page_size);
        request.setDomains(new ArrayList<Long>());
        request.setStages(new ArrayList<Long>());
        request.setKeyword(keyWord);
        request.setSort(0);
        if(searchType==ConstantUtil.TYPE_FUND){
            mPresenter.queryFunds(request);
        }else{
            mPresenter.queryInvestors(request);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick({R.id.ivBack, R.id.changeContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.changeContainer:
                Bundle bundle=new Bundle();
                bundle.putBoolean(ConstantUtil.INTENT_IS_FOR_RESULT,true);
                bundle.putString(ConstantUtil.KEY_WORD_INTENT,keyWord);
                bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,searchType);
                gotoActivityFadeForResult(SearchActionActivity.class,bundle,REQUEST_CODE_SEARCH);
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
            case REQUEST_CODE_SEARCH:
                searchType=data.getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);
                keyWord=data.getExtras().getString(ConstantUtil.KEY_WORD_INTENT);
                tvChangeHint.setText(keyWord);

                domainAdapter.setSelectedList();


                if(searchType==ConstantUtil.TYPE_FUND){
                    tvChange.setText(getResources().getString(R.string.fund));
                }else{
                    tvChange.setText(getResources().getString(R.string.investor));
                }
                break;
        }
    }

    @Override
    public Context getContext() {
        return null;
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
    public void queryFundSuccess(FundBackEntity bean) {
        if (page == 1) {
            data.clear();
        }
        if (bean.getList() != null) {
            data.addAll(bean.getList());
        }
        if (bean.getTotal() > data.size()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryInvestorSuccess(InvestorBean bean) {
        if (page == 1) {
            data.clear();
        }
        if (bean.getList() != null) {
            data.addAll(bean.getList());
        }
        if (bean.getTotal() > data.size()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        mAdapter.notifyDataSetChanged();
    }

    private void addIndustry() {
        View view = LayoutInflater.from(this).inflate(R.layout.filter_flow, null);
        domainFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowLayout);
        view.findViewById(R.id.tvResetFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domainAdapter.setSelectedList();
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
                TextView tv = (TextView) LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.item_filter_textview, domainFlowLayout, false);
                tv.setText(o.getType_name());
                return tv;
            }
        };
        domainFlowLayout.setAdapter(domainAdapter);
        popupViews.add(view);
    }

    private void addTurn() {
        View view = LayoutInflater.from(this).inflate(R.layout.filter_flow, null);
        stageFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowLayout);

        view.findViewById(R.id.tvResetFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stageAdapter.setSelectedList();
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
                TextView tv = (TextView) LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.item_filter_textview, stageFlowLayout, false);
                tv.setText(o.getType_name());
                return tv;
            }
        };

        stageFlowLayout.setAdapter(stageAdapter);
        popupViews.add(view);
    }


    private void addEfficiency() {

        RecyclerView efficiencyRecycle = new RecyclerView(this);
        efficiencyRecycle.setLayoutManager(new LinearLayoutManager(this));
        ratingAdapter = new EfficiencyAdapter(this, efficiencyData);
        efficiencyRecycle.setAdapter(ratingAdapter);
        popupViews.add(efficiencyRecycle);
    }

    private void configRecycleView() {
        contentViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InvestorAdapter(this, data);
        contentViewHolder.recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {

            }
        });
        initPaginate();
        //获取数据
        //mPresenter.getInvestorData(page, page_size);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    //mPresenter.getInvestorData(page, page_size);
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

    static class ContentViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
