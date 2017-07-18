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
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.list.ConcernListAdapter;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.investor.bean.InvestorBean;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.RoadCommentActivity;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import java.util.List;
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
    private InvestorBean investorBean;
    private int typeComment;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerIndividualListComponent.builder().appComponent(appComponent).individualListModule(new IndividualListModule(this)).build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSourceType=getArguments().getInt(ConstantUtil.DATA_FROM_TYPE);
        typeComment=getArguments().getInt(ConstantUtil.COMMENT_TYPE_INTENT);
    }

    public static IndividualListFrag getInstance(int type,int typeComment){
        IndividualListFrag frag=new IndividualListFrag();
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtil.DATA_FROM_TYPE,type);
        bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT,typeComment);
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
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                mPresenter.queryDetail(data.get(position).getInvestor_id(),ConstantUtil.DEFALUT_ID);
            }
        });

        //本地不分页,服务端都不分页
        if(dataSourceType==ConstantUtil.DATA_FROM_LOCAL){
            hasLoadedAllItems=true;
            List<LastBrowerBean> list=mPresenter.queryLastBrowers();
            if(list!=null){
                for(LastBrowerBean bean:list){
                    ConcernListEntity entity=new ConcernListEntity();
                    entity.setTitle(bean.getTitle());
                    entity.setFund_name(bean.getFund_name());
                    entity.setInvestor_avatar(bean.getInvestor_avatar());
                    entity.setInvestor_name(bean.getInvestor_name());
                    entity.setInvestor_id(bean.getInvestor_id());
                    entity.setContact_id(2);//为了区分类型的
                    entity.setLocalTime(bean.getLocalTime());
                    entity.setInvestor_uid(bean.getInvestor_uid());
                    data.add(entity);
                }
            }
            mAdapter.notifyDataSetChanged();
        }else{
            //initPaginate();
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
    @Override
    public void queryDetailSuccess(CallBackBean backBean) {
        if (backBean.getInvestor() != null) {
            investorBean=backBean.getInvestor();

            if (mPresenter.getUserInfo() != null) {
                if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                    if(mPresenter.getUserInfo().getHas_project()==0){
                        showTwoButtonDialog(getResources().getString(R.string.please_complete_project),
                                getResources().getString(R.string.cancle),
                                getResources().getString(R.string.comfirm),
                                new TwoButtonListerner() {
                                    @Override
                                    public void leftClick() {
                                        dismissTwoButtonDialog();
                                    }

                                    @Override
                                    public void rightClick() {
                                        dismissTwoButtonDialog();
                                        Bundle bundle=new Bundle();
                                        bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT,false);
                                        gotoActivity(AddProjectActivity.class,bundle);
                                    }
                                });
                        return;
                    }


                    if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 1) {
                        showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                new OneButtonListerner() {
                                    @Override
                                    public void onOptionSelected() {
                                        dismissBottomOneButtonDialog();
                                        gotoComment(true);
                                    }

                                    @Override
                                    public void onCancle() {
                                        dismissBottomOneButtonDialog();
                                    }
                                });
                    } else if (investorBean.getHas_comment() == 1 && investorBean.getHas_roadshow() == 0) {
                        gotoRoad(true);
                    } else if (investorBean.getHas_comment() == 0 && investorBean.getHas_roadshow() == 1) {
                        showBottomOneDialog(getResources().getString(R.string.comment),
                                new OneButtonListerner() {
                                    @Override
                                    public void onOptionSelected() {
                                        dismissBottomOneButtonDialog();
                                        if(investorBean.getHas_score()==0){
                                            gotoScore(false);
                                        }else{
                                            gotoComment(false);
                                        }
                                    }

                                    @Override
                                    public void onCancle() {
                                        dismissBottomOneButtonDialog();
                                    }
                                });
                    } else {
                        if(typeComment==ConstantUtil.COMMENT_TYPE_ROAD){
                            gotoRoad(false);
                        }else if(typeComment==ConstantUtil.COMMENT_TYPE_EDIT){
                            gotoScore(false);
                        }
                        //gotoActivityForResult(ChooseActivity.class,REQUEST_CODE_SELECTED_TYPE);
                    }

                } else {
                    if (investorBean.getHas_comment() == 0) {
                        if(investorBean.getHas_score()==0){
                            gotoScore(false);
                        }else{
                            gotoComment(false);
                        }
                    } else {
                        showBottomOneDialog(getResources().getString(R.string.plus_comment),
                                new OneButtonListerner() {
                                    @Override
                                    public void onOptionSelected() {
                                        dismissBottomOneButtonDialog();
                                        gotoComment(true);
                                    }

                                    @Override
                                    public void onCancle() {
                                        dismissBottomOneButtonDialog();
                                    }
                                });
                    }
                }
            }

        }
    }

    @Override
    public void startRefresh(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    public void endRefresh() {
        loadingDialogDismiss();
    }

    /**
     * 进入评分
     * @param isAppend
     */
    private void gotoScore(boolean isAppend){
        gotoActivity(RateActivity.class,getBundle(isAppend));
    }

    /**
     * 进入评论或者追评
     * @param isAppend
     */
    private void gotoComment(boolean isAppend){
        gotoActivity(ReviewActivity.class,getBundle(isAppend));
    }

    /**
     * 进入路演评价
     * @param isAppend
     */
    private void gotoRoad(boolean isAppend) {
        Bundle bundle=getBundle(isAppend);
        bundle.putInt(ConstantUtil.INTENT_INDEX,0);
        gotoActivity(RoadCommentActivity.class, getBundle(isAppend));
    }

    private Bundle getBundle(boolean isAppend){
        Bundle bundle=new Bundle();
        InvestorInfoBean infoBean=new InvestorInfoBean();
        infoBean.setInvestor_id(investorBean.getInvestor_id());
        infoBean.setFund_id(investorBean.getFund_id());
        infoBean.setFund_name(investorBean.getFund_name());
        infoBean.setInvestor_avatar(investorBean.getInvestor_avatar());
        infoBean.setTitle(investorBean.getInvestor_title());
        infoBean.setInvestor_name(investorBean.getInvestor_name());
        infoBean.setInvestor_uid(investorBean.getInvestor_uid());
        infoBean.setTags(investorBean.getTags());
        infoBean.setHas_comment(investorBean.getHas_comment());
        infoBean.setHas_roadshow(investorBean.getHas_roadshow());
        infoBean.setHas_score(investorBean.getHas_score());
        infoBean.setScore_value(investorBean.getScore_value());
        infoBean.setComment_id(investorBean.getComment_id());
        infoBean.setComment_title(investorBean.getComment_title());
        infoBean.setAppend(isAppend);
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,infoBean);
        return bundle;
    }
}
