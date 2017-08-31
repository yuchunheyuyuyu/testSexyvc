package com.qtin.sexyvc.ui.user.message.notice;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.user.bean.MsgBean;
import com.qtin.sexyvc.ui.user.bean.MsgItems;
import com.qtin.sexyvc.ui.user.message.notice.di.DaggerNoticeFragComponent;
import com.qtin.sexyvc.ui.user.message.notice.di.NoticeFragModule;
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

public class NoticeFrag extends MyBaseFragment<NoticeFragPresent> implements NoticeFragContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;

    private long id = ConstantUtil.DEFALUT_ID;
    private int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;
    private ArrayList<MsgBean> data = new ArrayList<>();
    private NoticeAdapter mAdapter;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerNoticeFragComponent.builder().appComponent(appComponent).noticeFragModule(new NoticeFragModule(this)).build().inject(this);
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
                id = ConstantUtil.DEFALUT_ID;
                mPresenter.queryNotice(id, page_size);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new NoticeAdapter(data);
        recyclerView.setAdapter(mAdapter);

        initPaginate();
        mPresenter.queryNotice(id, page_size);
    }

    public void changeAllReadStatus() {
        ChangeReadStatusRequest request = new ChangeReadStatusRequest();
        ArrayList<Long> ids = new ArrayList<Long>();
        request.setIds(ids);
        request.setObject_type(2);
        mPresenter.changeReadStatus(request, -1);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.queryNotice(id, page_size);
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
        UiUtils.showToastShort(mActivity, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void querySuccess(MsgItems items) {
        if (id == ConstantUtil.DEFALUT_ID) {
            data.clear();

            if(items.getList()==null||items.getList().isEmpty()){
                showEmptyView();
            }else{
                showContentView();
            }
        }else{
            showContentView();
        }
        if (items.getList() != null) {
            data.addAll(items.getList());
        }
        if (data.size() > 0) {
            id = data.get(data.size() - 1).getId();
        }
        if (page_size < items.getTotal()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
            mAdapter.setHasLoadMore(true);
        }
        mAdapter.notifyDataSetChanged();
        changeAllReadStatus();
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
    public void changeStatusSuccess(int position) {

    }

    @Override
    public void showNetErrorView() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if(emptyLayout.getVisibility()==View.VISIBLE){
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
        if(emptyLayout.getVisibility()==View.VISIBLE){
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
        if(emptyLayout.getVisibility()==View.GONE){
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ivErrorStatus)
    public void onClick() {
        id = ConstantUtil.DEFALUT_ID;
        mPresenter.queryNotice(id, page_size);
    }
}
