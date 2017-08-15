package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.main.fragInvestor.di.DaggerFragInvestorComponent;
import com.qtin.sexyvc.ui.main.fragInvestor.di.FragInvestorModule;
import com.qtin.sexyvc.ui.search.action.SearchActionActivity;
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
    private int page = 1;
    private int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();
    private InvestorAdapter mAdapter;

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
        isLoadingMore=false;
    }

    private void configRecycleView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getInvestorData(page, page_size);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InvestorAdapter(mRootView.getContext(), data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                /**FollowRequest entity = new FollowRequest();
                 ArrayList<Long> group_ids = new ArrayList<Long>();
                 ArrayList<Long> investor_ids = new ArrayList<Long>();
                 investor_ids.add(data.get(position).getInvestor_id());

                 entity.setGroup_ids(group_ids);
                 entity.setInvestor_ids(investor_ids);*/
                if(data.get(position) instanceof InvestorEntity){
                    Bundle bundle = new Bundle();
                    bundle.putLong("investor_id", ((InvestorEntity)data.get(position)).getInvestor_id());
                    gotoActivity(InvestorDetailActivity.class, bundle);
                }
            }
        });
        initPaginate();
        //获取数据
        mPresenter.getInvestorData(page, page_size);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.getInvestorData(page, page_size);
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

            mPaginate = Paginate.with(recyclerView, callbacks)
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
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void querySuccess(InvestorBean bean) {
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

    @OnClick(R.id.searchContainer)
    public void onClick() {
        Bundle bundle=new Bundle();
        bundle.putString(ConstantUtil.KEY_WORD_INTENT,"");
        bundle.putBoolean(ConstantUtil.INTENT_IS_FOR_RESULT,false);
        bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,ConstantUtil.TYPE_INVESTOR);
        gotoActivity(SearchActionActivity.class,bundle);
    }
}
