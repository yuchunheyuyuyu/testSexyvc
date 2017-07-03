package com.qtin.sexyvc.ui.investor;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.investor.bean.ItemCaseBean;
import com.qtin.sexyvc.ui.investor.bean.ItemBaseBean;
import com.qtin.sexyvc.ui.investor.bean.ItemTagBean;
import com.qtin.sexyvc.ui.investor.di.DaggerInvestorDetailComponent;
import com.qtin.sexyvc.ui.investor.di.InvestorDetailModule;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class InvestorDetailActivity extends MyBaseActivity<InvestorDetailPresent> implements InvestorDetailContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.headerLine)
    View headerLine;
    @BindView(R.id.headContainer)
    RelativeLayout headContainer;
    private long investor_id;

    private InvestorDetailAdapter mAdapter;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerInvestorDetailComponent.builder().appComponent(appComponent).investorDetailModule(new InvestorDetailModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.investor_detail_activity;
    }

    @Override
    protected void initData() {
        investor_id = getIntent().getExtras().getLong("investor_id");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(investor_id,0);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new InvestorDetailAdapter(this,data);
        recyclerView.setAdapter(mAdapter);

        mPresenter.query(investor_id,0);
    }

    @Override
    public void showLoading(){
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
    public void hideLoading(){
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

    @OnClick({R.id.ivLeft, R.id.ivShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                break;
        }
    }

    @Override
    public void querySuccess(CallBackBean backBean) {
        data.clear();

        data.add(backBean.getInvestor());
        //路演
        data.add(backBean.getInvestor().getRoad_show());
        //基本信息
        ItemBaseBean introBean=new ItemBaseBean();
        introBean.setInvestor_intro(backBean.getInvestor().getInvestor_intro());
        data.add(introBean);
        //行业标签
        ItemTagBean tagBean=new ItemTagBean();
        tagBean.setDomain_list(backBean.getInvestor().getDomain_list());
        tagBean.setStage_list(backBean.getInvestor().getStage_list());
        data.add(tagBean);
        //案例
        ItemCaseBean caseBean=new ItemCaseBean();
        caseBean.setCase_list(backBean.getInvestor().getCase_list());
        caseBean.setCase_number(backBean.getInvestor().getCase_number());
        caseBean.setComment_number(backBean.getInvestor().getComment_number());
        data.add(caseBean);

        if(backBean.getComments()!=null&&backBean.getComments().getList()!=null){
            data.addAll(backBean.getComments().getList());
        }
        mAdapter.notifyDataSetChanged();

    }
}
