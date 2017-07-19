package com.qtin.sexyvc.ui.comment.list.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.comment.list.frag.bean.CommentItemsBean;
import com.qtin.sexyvc.ui.comment.list.frag.di.CommentLastModule;
import com.qtin.sexyvc.ui.comment.list.frag.di.DaggerCommentLastComponent;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import java.util.ArrayList;
import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */

public class CommentLastFrag extends MyBaseFragment<CommentLastPresent> implements CommentLastContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;


    private int page=1;
    private int page_size=15;

    private CommentListAdapter mAdapter;
    private ArrayList<CommentBean> data=new ArrayList<>();
    private int hot_comment;

    public static CommentLastFrag getInstance(int hot_comment){
        CommentLastFrag frag=new CommentLastFrag();
        Bundle bundle=new Bundle();
        bundle.putInt("hot_comment",hot_comment);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hot_comment=getArguments().getInt("hot_comment");
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerCommentLastComponent.builder().appComponent(appComponent).commentLastModule(new CommentLastModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.swipe_recycleview;
    }

    @Override
    protected void init() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mPresenter.queryCommentList(page,page_size,hot_comment);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter=new CommentListAdapter(data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                Bundle bundle=new Bundle();
                bundle.putLong("comment_id",data.get(position).getComment_id());
                gotoActivity(CommentDetailActivity.class,bundle);
            }
        });
        initPaginate();
        mPresenter.queryCommentList(page,page_size,hot_comment);
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
        UiUtils.showToastShort(mActivity,message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.queryCommentList(page,page_size,hot_comment);
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
    public void querySuccess(CommentItemsBean bean) {
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
    public void startLoadMore() {
        isLoadingMore=true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore=false;
    }

}
