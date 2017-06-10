package com.qtin.sexyvc.ui.main.fraghome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.di.DaggerFragHomeComponent;
import com.qtin.sexyvc.ui.main.fraghome.di.FragHomeModule;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

import java.util.ArrayList;

import butterknife.BindView;
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

    private ArrayList<HomeInterface> data=new ArrayList<>();
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
                mPresenter.getHomeData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter=new HomeAdapter(mActivity,data);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getHomeData();
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
    public Context getContext() {
        return mActivity;
    }

    private Handler handler=new Handler();

    @Override
    public void dataCallback(ArrayList<HomeInterface> list) {

        data.clear();
        if(list!=null&&!list.isEmpty()){
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
    public void killMyself() {

    }
}
