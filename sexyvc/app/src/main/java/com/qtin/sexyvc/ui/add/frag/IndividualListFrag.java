package com.qtin.sexyvc.ui.add.frag;

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
import com.qtin.sexyvc.ui.add.frag.di.DaggerIndividualListComponent;
import com.qtin.sexyvc.ui.add.frag.di.IndividualListModule;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.follow.list.ConcernListAdapter;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */

public class IndividualListFrag extends MyBaseFragment<IndividualListPresent> implements IndividualListContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<ConcernListEntity> data = new ArrayList<>();
    private ConcernListAdapter mAdapter;

    private int dataSourceType;

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private int page=1;
    private int page_size=15;

    private long DEFALUT_GROUP_ID=0;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerIndividualListComponent.builder().appComponent(appComponent).individualListModule(new IndividualListModule(this)).build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSourceType=getArguments().getInt(ConstantUtil.DATA_FROM_TYPE);
    }

    public static IndividualListFrag getInstance(int type){
        IndividualListFrag frag=new IndividualListFrag();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtil.DATA_FROM_TYPE,type);
        frag.setArguments(bundle);
        return frag;
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
                mPresenter.query(DEFALUT_GROUP_ID,page,page_size);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter=new ConcernListAdapter(mActivity,data);
        recyclerView.setAdapter(mAdapter);

        //本地不分页
        if(dataSourceType==ConstantUtil.DATA_FROM_LOCAL){
            hasLoadedAllItems=true;
        }else{
            initPaginate();
            mPresenter.query(DEFALUT_GROUP_ID,page,page_size);
        }
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    mPresenter.query(DEFALUT_GROUP_ID,page,page_size);
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
        UiUtils.showToastShort(mActivity,message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

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
