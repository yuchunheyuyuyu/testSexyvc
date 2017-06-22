package com.qtin.sexyvc.ui.concern.list;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.concern.list.di.ConcernListModule;
import com.qtin.sexyvc.ui.concern.list.di.DaggerConcernListComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

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

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConcernListAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
