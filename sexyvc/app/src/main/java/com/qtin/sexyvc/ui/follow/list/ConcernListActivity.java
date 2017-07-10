package com.qtin.sexyvc.ui.follow.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.detail.ConcernDetailActivity;
import com.qtin.sexyvc.ui.follow.list.di.ConcernListModule;
import com.qtin.sexyvc.ui.follow.list.di.DaggerConcernListComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class ConcernListActivity extends MyBaseActivity<ConcernListPresent> implements ConcernListContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String group_name;
    private long group_id;
    private ConcernListAdapter mAdapter;
    private ArrayList<ConcernListEntity> data = new ArrayList<>();

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private int page=1;
    private int page_size=15;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerConcernListComponent.builder().appComponent(appComponent).concernListModule(new ConcernListModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_refresh_list_activity;
    }

    @Override
    protected void initData() {
        group_id = getIntent().getExtras().getLong("group_id");
        group_name = getIntent().getExtras().getString("group_name");
        tvTitle.setText(StringUtil.formatString(group_name));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mPresenter.query(group_id,page,page_size);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConcernListAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                //再跳转
                Bundle bundle=new Bundle();
                bundle.putLong("contact_id",data.get(position).getContact_id());
                bundle.putLong("investor_id",data.get(position).getInvestor_id());
                gotoActivity(ConcernDetailActivity.class,bundle);
            }
        });
        initPaginate();
        mPresenter.query(group_id,page,page_size);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.query(group_id,page,page_size);
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
        UiUtils.showToastShort(this,StringUtil.formatString(message));
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
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
    public void querySuccess(ConcernEntity entity) {
        if(page==1){
            data.clear();
        }
        if(entity.getList()!=null){
            data.addAll(entity.getList());
        }
        if(data.size()<entity.getTotal()){
            hasLoadedAllItems=false;
        }else{
            hasLoadedAllItems=true;
        }
        mAdapter.notifyDataSetChanged();
    }
}
