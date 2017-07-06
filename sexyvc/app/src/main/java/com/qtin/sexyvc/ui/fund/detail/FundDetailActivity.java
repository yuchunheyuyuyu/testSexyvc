package com.qtin.sexyvc.ui.fund.detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import com.qtin.sexyvc.ui.fund.detail.di.DaggerFundDetailComponent;
import com.qtin.sexyvc.ui.fund.detail.di.FundDetailModule;
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
public class FundDetailActivity extends MyBaseActivity<FundDetailPresent> implements FundDetailContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.headerLine)
    View headerLine;
    @BindView(R.id.headContainer)
    RelativeLayout headContainer;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();
    private FundDetailAdapter mAdapter;

    private long fund_id;
    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFundDetailComponent.builder().appComponent(appComponent).fundDetailModule(new FundDetailModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fund_detail_activity;
    }

    @Override
    protected void initData() {

        fund_id=getIntent().getExtras().getLong("fund_id");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(fund_id, ConstantUtil.DEFALUT_ID);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new FundDetailAdapter(this,data);
        recyclerView.setAdapter(mAdapter);

        maxDistance= (int) DeviceUtils.dpToPixel(this,110);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mDistance+=dy;
                float percent=mDistance*1f/maxDistance>1?1:mDistance*1f/maxDistance;

                int alpha = (int) (percent * 255);
                Log.e("透明度","=======透明度====="+percent);
                //整个背景
                int argb = Color.argb(alpha, 255, 255, 255);
                headContainer.setBackgroundColor(argb);
                //间隔线
                int lineColor=Color.argb(alpha,224,224,226);
                headerLine.setBackgroundColor(lineColor);
                //标题
                int titleColor=Color.argb(alpha,59,67,87);
                tvTitle.setTextColor(titleColor);

                //返回键
                if(mDistance==0){
                    ivLeft.setSelected(false);
                    ivLeft.setAlpha(255);
                }else{
                    ivLeft.setSelected(true);
                    ivLeft.setAlpha(alpha);
                }
            }
        });

        mPresenter.query(fund_id, ConstantUtil.DEFALUT_ID);
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
    public void querySuccess(FundDetailBackBean bean) {
        data.clear();
        if(bean.getFund()!=null){
            data.add(bean.getFund());
            tvTitle.setText(StringUtil.formatString(bean.getFund().getFund_name()));
        }

        if(bean.getComments()!=null&&bean.getComments().getList()!=null){
            data.addAll(bean.getComments().getList());
        }
        mAdapter.notifyDataSetChanged();
    }
}
