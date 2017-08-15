package com.qtin.sexyvc.ui.flash;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.flash.bean.FlashBean;
import com.qtin.sexyvc.ui.flash.bean.FlashEntity;
import com.qtin.sexyvc.ui.flash.di.DaggerFlashComponent;
import com.qtin.sexyvc.ui.flash.di.FlashModule;
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
public class FlashActivity extends MyBaseActivity<FlashPresent> implements FlashContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private FlashAdapter mAdapter;
    private ArrayList<FlashEntity> data=new ArrayList<>();

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private long flash_id;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFlashComponent.builder().appComponent(appComponent).flashModule(new FlashModule(this)).build().inject(this);
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
        tvTitle.setText(getResources().getString(R.string.title_flash));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flash_id=ConstantUtil.DEFALUT_ID;
                mPresenter.query(flash_id,true);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new FlashAdapter(data);
        recyclerView.setAdapter(mAdapter);

        initPaginate();
        mPresenter.query(ConstantUtil.DEFALUT_ID,true);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.query(flash_id,false);
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
        UiUtils.showToastShort(this, StringUtil.formatString(message));
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

    @Override
    public void querySuccess(boolean pullToRefresh,FlashBean bean) {
        if(pullToRefresh){
            data.clear();
        }
        if(bean.getList()!=null){
            data.addAll(bean.getList());
        }
        if(data.size()<bean.getTotal()){
            hasLoadedAllItems=false;
        }else{
            hasLoadedAllItems=true;
        }
        if(data.size()>0){
            flash_id=data.get(data.size()-1).getId();
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
