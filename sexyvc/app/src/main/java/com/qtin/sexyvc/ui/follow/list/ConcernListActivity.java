package com.qtin.sexyvc.ui.follow.list;

import android.content.Intent;
import android.os.Bundle;
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
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.FooterBean;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.detail.ConcernDetailActivity;
import com.qtin.sexyvc.ui.follow.list.bean.FollowedFundBean;
import com.qtin.sexyvc.ui.follow.list.di.ConcernListModule;
import com.qtin.sexyvc.ui.follow.list.di.DaggerConcernListComponent;
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
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
public class ConcernListActivity extends MyBaseActivity<ConcernListPresent> implements ConcernListContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String group_name;
    private long group_id;
    private ListAdapter mAdapter;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private int page=1;
    private int page_size=15;
    private int type;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerConcernListComponent.builder().appComponent(appComponent).concernListModule(new ConcernListModule(this)).build().inject(this);
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
        type=getIntent().getExtras().getInt("type");
        group_id = getIntent().getExtras().getLong("group_id");
        group_name = getIntent().getExtras().getString("group_name");
        tvTitle.setText(StringUtil.formatString(group_name));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                //再跳转
                if(type== ConstantUtil.TYPE_INVESTOR){
                    ConcernListEntity entity= (ConcernListEntity) data.get(position);
                    Bundle bundle=new Bundle();
                    bundle.putLong("contact_id",entity.getContact_id());
                    bundle.putLong("investor_id",entity.getInvestor_id());
                    gotoActivityForResult(ConcernDetailActivity.class,bundle, ConstantUtil.REQUEST_CODE_ID);
                }else{
                    Bundle bundle = new Bundle();
                    FollowedFundBean bean= (FollowedFundBean) data.get(position);
                    bundle.putLong("fund_id", bean.getFund_id());
                    gotoActivity(FundDetailActivity.class, bundle);
                }
            }
        });
        initPaginate();
        loadData();
    }

    private void loadData(){
        if(type==ConstantUtil.TYPE_INVESTOR){
            mPresenter.query(group_id,page,page_size);
        }else{
            mPresenter.queryFundDetail(group_id,page,page_size);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ConstantUtil.REQUEST_CODE_ID:
                if(data!=null){
                    long contact_id=data.getExtras().getLong(ConstantUtil.INTENT_ID);
                    if(this.data!=null&&!this.data.isEmpty()){
                        if(type== ConstantUtil.TYPE_INVESTOR){
                            for(DataTypeInterface typeInterface:this.data){
                                ConcernListEntity entity= (ConcernListEntity) typeInterface;
                                if(entity.getContact_id()==contact_id){
                                    this.data.remove(entity);
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
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
        UiUtils.showToastShort(this,StringUtil.formatString(message));
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
            data.add(new FooterBean());
            hasLoadedAllItems=true;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryFundSuccess(ListBean<FollowedFundBean> entity) {
        if(page==1){
            data.clear();
        }
        if(entity.getList()!=null){
            data.addAll(entity.getList());
        }
        if(data.size()<entity.getTotal()){
            hasLoadedAllItems=false;
        }else{
            data.add(new FooterBean());
            hasLoadedAllItems=true;
        }
        mAdapter.notifyDataSetChanged();
    }
}
