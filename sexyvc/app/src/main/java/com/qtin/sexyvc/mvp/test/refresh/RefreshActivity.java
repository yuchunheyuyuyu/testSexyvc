package com.qtin.sexyvc.mvp.test.refresh;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;

import com.jess.arms.base.DefaultAdapter;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.anim.recycleview.adapter.ScaleInAnimatorAdapter;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.di.component.DaggerRefreshComponent;
import com.qtin.sexyvc.di.module.RefreshModule;
import com.qtin.sexyvc.mvp.contract.RefreshContarct;
import com.qtin.sexyvc.mvp.presenter.RefreshPresent;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;

/**
 * Created by ls on 17/2/28.
 */
public class RefreshActivity extends MyBaseActivity<RefreshPresent> implements RefreshContarct.View {

    @BindView(R.id.recyclerView)
    PullLoadMoreRecyclerView recyclerView;

    private RxPermissions rxPermissions;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        this.rxPermissions = new RxPermissions(this);
        DaggerRefreshComponent
                .builder()
                .appComponent(appComponent)
                .refreshModule(new RefreshModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_refresh_activity;
    }

    @Override
    protected void initData() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("刷新页面");
        setSupportActionBar(toolbar);
        mPresenter.requestUsers(true);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

        ScaleInAnimatorAdapter animatorAdapter=new ScaleInAnimatorAdapter(adapter,recyclerView.getRecyclerView());
        recyclerView.setAdapter(animatorAdapter);
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestUsers(true);
            }

            @Override
            public void onLoadMore() {
                mPresenter.requestUsers(false);
            }
        });
        configRecycleView();
    }

    /**
     * 配置recycleview
     *
     */
    private void configRecycleView(){
        recyclerView.setLinearLayout();
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         * 刷新结束
         mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

         显示下拉刷新
         mPullLoadMoreRecyclerView.setRefreshing(true);

         不需要下拉刷新
         mPullLoadMoreRecyclerView.setPullRefreshEnable(false);

         不需要上拉刷新
         mPullLoadMoreRecyclerView.setPushRefreshEnable(false);

         设置上拉刷新文字
         mPullLoadMoreRecyclerView.setFooterViewText("loading");

         设置上拉刷新文字颜色
         mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);

         设置加载更多背景色
         mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);

         设置下拉刷新颜色
         mPullLoadMoreRecyclerView.setColorSchemeResources(android.R.color.holo_red_dark,android.R.color.holo_blue_dark);

         快速Top
         mPullLoadMoreRecyclerView.scrollToTop();

         设置线性布局
         mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullLoadMoreRecyclerView);
         mPullLoadMoreRecyclerView.setLinearLayout();

         设置网格布局
         mPullLoadMoreRecyclerView.setGridLayout(2);//参数为列数

         设置交错网格布局，即瀑布流效果
         mPullLoadMoreRecyclerView.setStaggeredGridLayout(2);//参数为列数
         */
    }

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        recyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void showLoading() {
        //不需要做任何事情
    }

    @Override
    public void hideLoading() {
        recyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }
}
