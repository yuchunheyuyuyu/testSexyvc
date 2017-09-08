package com.qtin.sexyvc.ui.user.sent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.action.RoadCommentActivity;
import com.qtin.sexyvc.ui.user.sent.bean.OnCommentClickListener;
import com.qtin.sexyvc.ui.user.sent.bean.SentBean;
import com.qtin.sexyvc.ui.user.sent.di.DaggerSentComponent;
import com.qtin.sexyvc.ui.user.sent.di.SentModule;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class SentActivity extends MyBaseActivity<SentPresent> implements SentContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;
    private long record_id= ConstantUtil.DEFALUT_ID;
    private SentAdapter adapter;
    private ArrayList<SentBean> data=new ArrayList<>();

    private boolean isNeedRefresh=false;
    private int u_auth_type;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSentComponent.builder().appComponent(appComponent).sentModule(new SentModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeedRefresh){
            record_id= ConstantUtil.DEFALUT_ID;
            mPresenter.queryMySent(page_size,record_id);
            isNeedRefresh=false;
        }
    }

    @Subscriber(tag = ConstantUtil.ROAD_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveRoad(CommentEvent commentEvent){
        isNeedRefresh=true;
    }

    @Subscriber(tag = ConstantUtil.COMMENT_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceiveComment(CommentEvent commentEvent){
        isNeedRefresh=true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_refresh_list_white_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.my_sent));

        if(mPresenter.getUserInfo()!=null){
            u_auth_type=mPresenter.getUserInfo().getU_auth_type();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                record_id= ConstantUtil.DEFALUT_ID;
                mPresenter.queryMySent(page_size,record_id);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new SentAdapter(this,data,u_auth_type);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("investor_id", data.get(position).getInvestor_id());
                gotoActivity(InvestorDetailActivity.class, bundle);
            }
        });
        adapter.setOnCommentClickListener(new OnCommentClickListener() {
            @Override
            public void onClickRoad(int position) {
                if(data.get(position).getHas_roadshow()==0){
                    gotoRoad(position);
                }else{
                    showMessage("已经进行过路演评价");
                }
            }

            @Override
            public void onClickText(int position) {
                if(data.get(position).getHas_comment()==0){
                    gotoComment(position);
                }else{
                    SentBean sentBean=data.get(position);

                    Bundle bundle=new Bundle();
                    InvestorInfoBean investorInfoBean=new InvestorInfoBean();
                    investorInfoBean.setHas_comment(1);
                    investorInfoBean.setComment_title(sentBean.getComment_title());
                    investorInfoBean.setScore_value(sentBean.getScore());
                    investorInfoBean.setInvestor_name(sentBean.getInvestor_name());
                    investorInfoBean.setComment_id(sentBean.getComment_id());
                    investorInfoBean.setFund_id(sentBean.getFund_id());

                    bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE, investorInfoBean);
                    gotoActivity(ReviewActivity.class, bundle);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        initPaginate();
        mPresenter.queryMySent(page_size,record_id);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.queryMySent(page_size,record_id);
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

    /**
     * 进入评论
     */
    private void gotoComment(int position){
        gotoActivity(ReviewActivity.class,getBundle(ConstantUtil.INTENT_TEXT_COMMENT, position));
    }

    /**
     * 进入路演评价
     */
    private void gotoRoad(int position) {
        Bundle bundle=getBundle(ConstantUtil.INTENT_ROAD_COMMENT,position);
        bundle.putInt(ConstantUtil.INTENT_INDEX,0);
        gotoActivity(RoadCommentActivity.class, bundle);
    }

    private Bundle getBundle(int intent,int position){
        Bundle bundle=new Bundle();
        InvestorInfoBean infoBean=new InvestorInfoBean();
        infoBean.setIntent(intent);
        infoBean.setInvestor_id(data.get(position).getInvestor_id());
        infoBean.setFund_id(data.get(position).getFund_id());
        infoBean.setFund_name(data.get(position).getFund_name());
        infoBean.setInvestor_avatar(data.get(position).getInvestor_avatar());

        infoBean.setInvestor_name(data.get(position).getInvestor_name());
        infoBean.setInvestor_uid(data.get(position).getInvestor_uid());

        infoBean.setHas_comment(data.get(position).getHas_comment());
        infoBean.setHas_roadshow(data.get(position).getHas_roadshow());
        infoBean.setHas_score(1);
        infoBean.setScore_value((int) data.get(position).getScore());
        infoBean.setComment_id(data.get(position).getComment_id());

        //infoBean.setTags(investorBean.getTags());
        //infoBean.setTitle(investorBean.getInvestor_title());
        //infoBean.setComment_title(investorBean.getComment_title());
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,infoBean);
        return bundle;
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

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }

    @Override
    public void querySuccess(long record_id, ListBean<SentBean> listBean) {
        if (record_id == ConstantUtil.DEFALUT_ID) {
            data.clear();
            if(listBean.getList()==null||listBean.getList().isEmpty()){
                showEmptyView();
            }else{
                showNormalContentView();
            }
        }else{
            showNormalContentView();
        }

        if (listBean.getList() != null) {
            data.addAll(listBean.getList());
        }
        if (listBean.getTotal() > page_size) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        adapter.notifyDataSetChanged();
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
    public void showNetErrorView() {
        showErrorView(new OnClickErrorListener() {
            @Override
            public void onClick() {
                record_id= ConstantUtil.DEFALUT_ID;
                mPresenter.queryMySent(page_size,record_id);
            }
        });
    }
}
