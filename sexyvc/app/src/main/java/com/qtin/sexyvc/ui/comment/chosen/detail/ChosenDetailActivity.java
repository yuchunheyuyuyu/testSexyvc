package com.qtin.sexyvc.ui.comment.chosen.detail;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.HomeCommentBean;
import com.qtin.sexyvc.ui.comment.chosen.detail.di.ChosenDetailModule;
import com.qtin.sexyvc.ui.comment.chosen.detail.di.DaggerChosenDetailComponent;
import com.qtin.sexyvc.ui.comment.list.CommentActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class ChosenDetailActivity extends MyBaseActivity<ChosenDetailPresent> implements ChosenDetailContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    private long topic_id;
    private int page=1;
    private static final int page_size=15;
    private ChosenDetailAdapter adapter;
    private List<DataTypeInterface> data=new ArrayList<>();

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    private boolean isFromChosenList;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerChosenDetailComponent.builder().appComponent(appComponent).chosenDetailModule(new ChosenDetailModule(this)).build().inject(this);
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
        isFromChosenList=getIntent().getExtras().getBoolean("isFromChosenList");
        topic_id = getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
        tvRight.setText(getString(R.string.past_hot_comment));
        tvRight.setVisibility(View.VISIBLE);
        tvTitle.setText("");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mPresenter.queryTopicDetail(topic_id,page,page_size);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChosenDetailAdapter(this,data);
        recyclerView.setAdapter(adapter);
        initPaginate();
        mPresenter.queryTopicDetail(topic_id,page,page_size);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.queryTopicDetail(topic_id,page, page_size);
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
        UiUtils.showToastShort(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(isFromChosenList){
                    finish();
                }else{
                    gotoActivity(CommentActivity.class);
                }
                break;
        }
    }

    @Override
    public void showNetErrorView() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
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
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.GONE) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
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
    public void querySuccess(int page,HomeCommentBean homeCommentBean) {
        if(page==1){
            tvTitle.setText(StringUtil.formatString(homeCommentBean.getInfo().getTitle()));
            data.clear();
            data.add(homeCommentBean.getInfo());
        }
        if(homeCommentBean.getList()!=null){
            data.addAll(homeCommentBean.getList());
        }
        if (data.size()-1<=homeCommentBean.getCount()) {
            hasLoadedAllItems = true;
        } else {
            hasLoadedAllItems = false;
        }
        adapter.notifyDataSetChanged();
    }
}
