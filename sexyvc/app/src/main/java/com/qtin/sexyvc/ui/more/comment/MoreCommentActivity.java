package com.qtin.sexyvc.ui.more.comment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.more.comment.di.DaggerMoreCommentComponent;
import com.qtin.sexyvc.ui.more.comment.di.MoreCommentModule;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class MoreCommentActivity extends MyBaseActivity<MoreCommentPresent> implements MoreCommentContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int type;
    private long contentId;
    private long comment_id=ConstantUtil.DEFALUT_ID;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    private CommentAdapter mAdapter;
    private ArrayList<CommentBean> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMoreCommentComponent.builder().appComponent(appComponent).moreCommentModule(new MoreCommentModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_refresh_list_white_activity;
    }

    @Override
    protected void initData() {
        type = getIntent().getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);
        contentId=getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
        String title=getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        tvTitle.setText(title);

        if (type == ConstantUtil.TYPE_FUND) {

        } else if (type == ConstantUtil.TYPE_INVESTOR) {

        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                comment_id=ConstantUtil.DEFALUT_ID;
                query();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CommentAdapter(this,data,type);
        recyclerView.setAdapter(mAdapter);
        initPaginate();
        query();
    }

    private void query(){
        if (type == ConstantUtil.TYPE_FUND) {
            mPresenter.queryFundComment(contentId,comment_id);
        } else if (type == ConstantUtil.TYPE_INVESTOR) {
            mPresenter.queryInvestorComment(contentId,comment_id);
        }
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    query();
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
        UiUtils.showToastShort(this,message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void querySuccess(FundDetailBackBean bean) {
        if (comment_id == ConstantUtil.DEFALUT_ID) {
            data.clear();
        }
        if(bean.getComments()!=null){
            if (bean.getComments().getList()!=null) {
                data.addAll(bean.getComments().getList());
            }
            if (bean.getComments().getTotal() > data.size()) {
                hasLoadedAllItems = false;
            } else {
                hasLoadedAllItems = true;
            }

            if(!data.isEmpty()){
                comment_id=data.get(data.size()-1).getComment_id();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void querySuccess(CallBackBean bean) {
        if (comment_id == ConstantUtil.DEFALUT_ID) {
            data.clear();
        }
        if(bean.getComments()!=null){
            if (bean.getComments().getList()!=null) {
                data.addAll(bean.getComments().getList());
            }
            if (bean.getComments().getTotal() > data.size()) {
                hasLoadedAllItems = false;
            } else {
                hasLoadedAllItems = true;
            }

            if(!data.isEmpty()){
                comment_id=data.get(data.size()-1).getComment_id();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
