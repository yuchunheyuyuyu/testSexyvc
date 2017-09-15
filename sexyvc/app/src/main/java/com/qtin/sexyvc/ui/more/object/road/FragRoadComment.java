package com.qtin.sexyvc.ui.more.object.road;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.investor.bean.CommentListBean;
import com.qtin.sexyvc.ui.more.comment.CommentAdapter;
import com.qtin.sexyvc.ui.more.object.activity.CountEvent;
import com.qtin.sexyvc.ui.more.object.road.di.DaggerFragRoadCommentComponent;
import com.qtin.sexyvc.ui.more.object.road.di.FragRoadCommentModule;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class FragRoadComment extends MyBaseFragment<FragRoadCommentPresent> implements FragRoadCommentContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;

    private int type;//是投资人还是投资机构
    private long contentId;
    private int auth_state;

    private final static int page_size=15;
    private long last_id;
    private CommentAdapter mAdapter;
    private ArrayList<CommentBean> data=new ArrayList<>();

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    private int total;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragRoadCommentComponent.builder().appComponent(appComponent).fragRoadCommentModule(new FragRoadCommentModule(this)).build().inject(this);
    }

    public static FragRoadComment getInstance(int type, long contentId,int auth_state){
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        bundle.putLong("contentId",contentId);
        bundle.putInt("auth_state",auth_state);
        FragRoadComment frag = new FragRoadComment();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.swipe_recycleview;
    }

    @Override
    protected void init() {
        type=getArguments().getInt("type");
        auth_state=getArguments().getInt("auth_state");
        contentId=getArguments().getLong("contentId");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                last_id=ConstantUtil.DEFALUT_ID;
                loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter=new CommentAdapter(mActivity,data,type);
        mAdapter.setContentId(contentId);
        recyclerView.setAdapter(mAdapter);

        initPaginate();
        loadData();
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    loadData();
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

    private void loadData(){

        String page_type="app";
        String data_type="";
        if(type == ConstantUtil.TYPE_FUND||type == ConstantUtil.TYPE_INVESTOR){
            data_type="comment";
        }else{
            data_type="roadshow";
        }

        if(type == ConstantUtil.TYPE_FUND||type==ConstantUtil.TYPE_FUND_ROAD){
            mPresenter.queryFundComment(contentId,data_type, page_type,page_size,last_id,auth_state);
        } else if (type == ConstantUtil.TYPE_INVESTOR||type==ConstantUtil.TYPE_INVESTOR_ROAD){
            mPresenter.queryInvestorComment(contentId, data_type,page_type,page_size,last_id,auth_state);
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
        UiUtils.showToastShort(mActivity,message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

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
    public void querySuccess(CommentListBean commentListBean) {
        if (last_id == ConstantUtil.DEFALUT_ID) {
            data.clear();
            if (commentListBean.getList()!=null&&!commentListBean.getList().isEmpty()) {
                total=commentListBean.getTotal();
            }

            if((commentListBean.getList()==null||commentListBean.getList().isEmpty())
                    &&commentListBean.getUnauth_count()==0){
                showEmptyView();
            }else{
                showContentView();
            }

            if(auth_state==1){
                CountEvent countEvent=new CountEvent();
                countEvent.setCount(total+commentListBean.getUnauth_count());
                countEvent.setType(type);
                EventBus.getDefault().post(countEvent,ConstantUtil.QUERY_SUCCESS);
            }
        }

        if (commentListBean.getList()!=null) {
            data.addAll(commentListBean.getList());
        }
        if (data.size() < total) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }

        if(!data.isEmpty()){
            last_id=data.get(data.size()-1).getComment_id();
        }
        if(auth_state==1){
            mAdapter.setUnauth_count(commentListBean.getUnauth_count());
        }

        mAdapter.notifyDataSetChanged();
    }
}
