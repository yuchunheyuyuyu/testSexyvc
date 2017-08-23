package com.qtin.sexyvc.ui.user.message.message;

import android.content.Intent;
import android.os.Bundle;
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
import com.qtin.sexyvc.ui.fund.detail.FundDetailActivity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;
import com.qtin.sexyvc.ui.user.bean.MsgBean;
import com.qtin.sexyvc.ui.user.bean.MsgItems;
import com.qtin.sexyvc.ui.user.message.MessageActivity;
import com.qtin.sexyvc.ui.user.message.message.di.DaggerMessageFragComponent;
import com.qtin.sexyvc.ui.user.message.message.di.MessageFragModule;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */

public class MessageFrag extends MyBaseFragment<MessageFragPresent> implements MessageFragContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private long id = ConstantUtil.DEFALUT_ID;
    private int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;
    private ArrayList<MsgBean> data=new ArrayList<>();
    private MessageAdapter mAdapter;
    private MessageActivity activity;
    private int hasLoadedNum;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMessageFragComponent.builder().appComponent(appComponent).messageFragModule(new MessageFragModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.swipe_recycleview;
    }

    @Override
    protected void init() {
        activity= (MessageActivity) mActivity;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                id=ConstantUtil.DEFALUT_ID;
                mPresenter.queryMessage(id,page_size);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter=new MessageAdapter(data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                try{

                    MsgBean bean=data.get(position);
                    bean.setRead_time(System.currentTimeMillis()/1000);
                    mAdapter.notifyDataSetChanged();
                    //设置为已读
                    ChangeReadStatusRequest request=new ChangeReadStatusRequest();
                    ArrayList<Long> ids=new ArrayList<Long>();
                    ids.add(bean.getId());
                    request.setIds(ids);
                    request.setObject_type(1);
                    mPresenter.changeReadStatus(request,position);

                    long id=Long.parseLong(bean.getAction_content());
                    Bundle bundle=new Bundle();
                    if("investor_detail".equals(bean.getAction_type())){
                        bundle.putLong("investor_id",id);
                        gotoActivity(InvestorDetailActivity.class,bundle);

                    }else if("fund_detail".equals(bean.getAction_type())){
                        bundle.putLong("fund_id",id);
                        gotoActivity(FundDetailActivity.class,bundle);

                    }else if("comment_detail".equals(bean.getAction_type())){
                        bundle.putLong("comment_id",id);
                        gotoActivity(CommentDetailActivity.class,bundle);

                    }else if("subject_detail".equals(bean.getAction_type())){
                        bundle.putLong("subject_id",id);
                        gotoActivity(SubjectDetailActivity.class,bundle);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        initPaginate();
        mPresenter.queryMessage(id,page_size);
    }

    public void changeAllReadStatus(){
        ChangeReadStatusRequest request=new ChangeReadStatusRequest();
        ArrayList<Long> ids=new ArrayList<Long>();
        request.setIds(ids);
        request.setObject_type(1);
        mPresenter.changeReadStatus(request,-1);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.queryMessage(id,page_size);
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
    public void onResume() {
        super.onResume();
        mPresenter.queryMessageStatus(ConstantUtil.DEFALUT_ID,1);
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
    public void querySuccess(MsgItems items) {
        if(id==ConstantUtil.DEFALUT_ID){
            data.clear();
            hasLoadedNum=0;
        }
        if(items.getList()!=null){
            hasLoadedNum+=items.getList().size();

            for(MsgBean bean:items.getList()){
                if(bean.getMessage_type()==1||bean.getMessage_type()==2){
                    data.add(bean);
                }
            }
        }
        if(data.size()>0){
            id=data.get(data.size()-1).getId();
        }
        if(page_size<items.getTotal()){
            hasLoadedAllItems=false;
        }else{
            hasLoadedAllItems=true;
            mAdapter.setHasLoadMore(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryStatusSuccess(MsgItems items) {
        if(items.getMsg_no_read()>0){
            activity.setTvRightSelected(true);
        }else{
            activity.setTvRightSelected(false);
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
    public void startRefresh(String msg) {
        showLoadingDialog(msg);
    }



    @Override
    public void endRefresh() {
        loadingDialogDismiss();
    }

    @Override
    public void changeStatusSuccess(int position) {
        if(position==-1){
            for(MsgBean bean:data){
                bean.setRead_time(System.currentTimeMillis()/1000);
            }
            activity.setTvRightSelected(false);
        }else{

        }

        mAdapter.notifyDataSetChanged();
    }
}
