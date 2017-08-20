package com.qtin.sexyvc.ui.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.login.account.create.success.CreateSuccessActivity;
import com.qtin.sexyvc.ui.recommend.bean.RecommendBean;
import com.qtin.sexyvc.ui.recommend.di.DaggerRecommendComponent;
import com.qtin.sexyvc.ui.recommend.di.RecommendModule;
import com.qtin.sexyvc.ui.request.FollowRequest;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class RecommendActivity extends MyBaseActivity<RecommendPresent> implements RecommendContract.View {


    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecommendAdapter mAdapter;
    private ArrayList<RecommendBean> data=new ArrayList<>();

    private UserInfoEntity userInfoEntity;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder().appComponent(appComponent).recommendModule(new RecommendModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.recommend_activity;
    }

    @Override
    protected void initData() {
        userInfoEntity=getIntent().getExtras().getParcelable(UserInfoActivity.INTENT_USER);

        ivLeft.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.title_recommend));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.skip));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAdapter=new RecommendAdapter(this,data);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(data.get(position).isSelected()){
                    data.get(position).setSelected(false);
                }else{
                    data.get(position).setSelected(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(mAdapter);
        mPresenter.queryRecommend();
    }

    @Override
    public void showLoading() {
        showDialog("加载数据");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
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

    @OnClick({R.id.tvRight, R.id.tvConcern})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                Bundle bundle=new Bundle();
                bundle.putParcelable(UserInfoActivity.INTENT_USER,userInfoEntity);
                gotoActivity(CreateSuccessActivity.class,bundle);
                break;
            case R.id.tvConcern:
                FollowRequest entity=new FollowRequest();
                ArrayList<Long> group_ids = new ArrayList<Long>();
                ArrayList<Long> investor_ids = new ArrayList<Long>();
                for(RecommendBean bean:data){
                    if(bean.isSelected()){
                        investor_ids.add(bean.getInvestor_id());
                    }
                }
                entity.setGroup_ids(group_ids);
                entity.setInvestor_ids(investor_ids);
                mPresenter.followInvestor(entity);
                break;
        }
    }

    @Override
    public void querySuccess(ListBean<RecommendBean> listBean) {
        data.clear();
        if(listBean.getList()!=null){
            data.addAll(listBean.getList());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void followSuccess() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(UserInfoActivity.INTENT_USER,userInfoEntity);
        gotoActivity(CreateSuccessActivity.class,bundle);
    }

    @Override
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }
}
