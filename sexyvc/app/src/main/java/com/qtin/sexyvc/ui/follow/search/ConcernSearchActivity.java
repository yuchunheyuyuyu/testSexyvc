package com.qtin.sexyvc.ui.follow.search;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.follow.list.ConcernListAdapter;
import com.qtin.sexyvc.ui.follow.search.di.ConcernSearchModule;
import com.qtin.sexyvc.ui.follow.search.di.DaggerConcernSearchComponent;
import com.qtin.sexyvc.ui.widget.ClearableEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ConcernSearchActivity extends MyBaseActivity<ConcernSearchPresent> implements ConcernSearchContract.View {

    @BindView(R.id.tvCancle)
    TextView tvCancle;
    @BindView(R.id.etSearch)
    ClearableEditText etSearch;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private ArrayList<ConcernListEntity> data=new ArrayList<>();
    private ConcernListAdapter mAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerConcernSearchComponent.builder().appComponent(appComponent).concernSearchModule(new ConcernSearchModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.concern_search_activity;
    }

    @Override
    protected void initData() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new ConcernListAdapter(this,data);
        recycleView.setAdapter(mAdapter);

        ConcernListEntity entity=new ConcernListEntity();
        entity.setViewType(1);
        data.add(entity);

        for(int i=0;i<5;i++){
            data.add(new ConcernListEntity());
        }
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

    @OnClick(R.id.tvCancle)
    public void onClick() {
        finish();
    }
}
