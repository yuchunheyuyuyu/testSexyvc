package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.HotSearchBean;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.main.fragInvestor.di.DaggerFragInvestorComponent;
import com.qtin.sexyvc.ui.main.fragInvestor.di.FragInvestorModule;
import com.qtin.sexyvc.ui.search.action.SearchActionActivity;
import com.qtin.sexyvc.ui.search.result.SearchResultActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
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
public class FragInvestor extends MyBaseFragment<FragInvestorPresent> implements FragInvestorContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    private int indexPage=0;
    private static final int index_page_size = 3;
    private int page = 1;
    private static final int page_size = 30;
    private ArrayList<DataTypeInterface> currentData = new ArrayList<>();
    private ArrayList<DataTypeInterface> wholeData = new ArrayList<>();

    private HotInvestorAdapter mAdapter;
    private HotSearchBean hotSearchBean;
    private boolean hasLoadedAllItems;

    private int searchType = ConstantUtil.TYPE_INVESTOR;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragInvestorComponent.builder().appComponent(appComponent).fragInvestorModule(new FragInvestorModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_investor;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        configRecycleView();
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void configRecycleView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //page = 1;
                //mPresenter.getInvestorData(page, page_size);
                mPresenter.queryHotSearch();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new HotInvestorAdapter(mRootView.getContext(), currentData);
        mAdapter.setOnClickHotListener(new HotInvestorAdapter.onClickHotListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putString(ConstantUtil.KEY_WORD_INTENT,hotSearchBean.getList().get(position));
                bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,ConstantUtil.TYPE_INVESTOR);
                gotoActivity(SearchResultActivity.class,bundle);
            }
        });

        mAdapter.setOnClickSwitchListener(new HotInvestorAdapter.OnClickSwitchListener() {
            @Override
            public void onClickSwitch() {
                indexPage++;
                setCurrentData();
            }
        });

        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if (currentData.get(position) instanceof InvestorEntity) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("investor_id", ((InvestorEntity) currentData.get(position)).getInvestor_id());
                    gotoActivity(InvestorDetailActivity.class, bundle);
                }
            }
        });
        mPresenter.queryHotSearch();
        //获取数据
        mPresenter.getInvestorData(page, page_size);
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

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void querySuccess(InvestorBean bean) {
        if (page == 1) {
            wholeData.clear();
        }
        if (bean.getList() != null) {
            wholeData.addAll(bean.getList());
        }
        if (bean.getList()==null||bean.getList().size() < page_size) {
            //已经加载完
            hasLoadedAllItems=true;
        } else {
            hasLoadedAllItems=false;
        }
        for(int i=0;i<wholeData.size();i++){
            if(wholeData.get(i) instanceof InvestorEntity){
                InvestorEntity entity= (InvestorEntity) wholeData.get(i);
                if(i%index_page_size==0){
                    entity.setFirst(true);
                }else{
                    entity.setFirst(false);
                }
            }
        }
        setCurrentData();
    }

    @Override
    public void showNetErrorView() {
        if (page == 1) {
            errorLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            page--;
            UiUtils.SnackbarText(getString(R.string.net_error_hint));
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
    }

    private void setCurrentData(){
        if(!wholeData.isEmpty()){
            //是否加载完所有
            if(hasLoadedAllItems){
                if(indexPage*index_page_size>=wholeData.size()-1){
                    return;
                }else if((indexPage+1)*index_page_size>wholeData.size()-1){
                    currentData.clear();
                    if(hotSearchBean!=null){
                        currentData.add(hotSearchBean);
                    }
                    currentData.addAll(wholeData.subList(indexPage*index_page_size,wholeData.size()-1));
                    mAdapter.notifyDataSetChanged();
                }else{
                    currentData.clear();
                    if(hotSearchBean!=null){
                        currentData.add(hotSearchBean);
                    }
                    currentData.addAll(wholeData.subList(indexPage*index_page_size,(indexPage+1)*index_page_size));
                    mAdapter.notifyDataSetChanged();
                }
            }else{
                if((indexPage+1)*index_page_size>wholeData.size()-1){
                    page++;
                    mPresenter.getInvestorData(page,page_size);
                }else{
                    currentData.clear();
                    if(hotSearchBean!=null){
                        currentData.add(hotSearchBean);
                    }
                    currentData.addAll(wholeData.subList(indexPage*index_page_size,(indexPage+1)*index_page_size));
                    mAdapter.notifyDataSetChanged();
                }
            }
        }else{
            currentData.clear();
            if(hotSearchBean!=null){
                currentData.add(hotSearchBean);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryHotSuccess(HotSearchBean hotSearchBean) {
        this.hotSearchBean = hotSearchBean;
        setCurrentData();
    }

    @OnClick({R.id.searchContainer, R.id.ivErrorStatus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivErrorStatus:
                page = 1;
                mPresenter.getInvestorData(page, page_size);
                break;
            case R.id.searchContainer:
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.KEY_WORD_INTENT, "");
                bundle.putBoolean(ConstantUtil.INTENT_IS_FOR_RESULT, false);
                bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT, ConstantUtil.TYPE_INVESTOR);
                gotoActivity(SearchActionActivity.class, bundle);
                break;
        }
    }
}
