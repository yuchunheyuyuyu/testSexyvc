package com.qtin.sexyvc.ui.user.project.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectAdapter;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;
import com.qtin.sexyvc.ui.user.project.my.di.DaggerMyProjectComponent;
import com.qtin.sexyvc.ui.user.project.my.di.MyProjectModule;
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
public class MyProjectActivity extends MyBaseActivity<MyProjectPresent> implements MyProjectContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ProjectBean> data=new ArrayList<>();
    private ProjectAdapter mAdapter;

    private ArrayList<FilterEntity> domainData = new ArrayList<>();
    private ArrayList<FilterEntity> stageData = new ArrayList<>();
    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段

    private boolean isDomainDownload;
    private boolean isStageDownload;

    private static final int REQUEST_CODE_EDIT=0x11a;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMyProjectComponent.builder().appComponent(appComponent).myProjectModule(new MyProjectModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_refresh_list_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_my_project));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new ProjectAdapter(this,data);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                Bundle bundle=new Bundle();
                bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT,true);
                bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,data.get(position));
                gotoActivityForResult(AddProjectActivity.class,bundle,REQUEST_CODE_EDIT);
            }
        });
        recyclerView.setAdapter(mAdapter);
        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }

        switch(requestCode){
            case REQUEST_CODE_EDIT:
                ProjectBean bean=data.getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
                this.data.clear();
                this.data.add(bean);
                mAdapter.notifyDataSetChanged();

                break;
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

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void querySuccess(ProjectEntity entity) {
        data.addAll(entity.getList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_DOMAIN:
                domainData.clear();
                domainData.addAll(list);
                isDomainDownload=true;
                query();
                break;
            case TYPE_STAGE:
                stageData.clear();

                FilterEntity entity=new FilterEntity();
                entity.setType_id(0);
                entity.setType_name("未融资");

                stageData.add(entity);

                stageData.addAll(list);
                isStageDownload=true;
                query();
                break;
        }
    }

    private void query(){
        if(isStageDownload&&isDomainDownload){
            mPresenter.query();
        }
    }

    public String getProjectInfo(int position){
        String unit=data.get(position).getLast_currency()==1?"人民币":"美金";

        StringBuilder sb=new StringBuilder();
        sb.append(getDomainText(position));
        sb.append(" | ");
        sb.append(getStageText(position));

        if(data.get(position).getLast_financial_amount()>0){
            sb.append("(");

            String moneyValue=data.get(position).getLast_financial_amount()/10000f+"";
            sb.append(moneyValue);
            sb.append("万");
            sb.append(unit);
            sb.append(")");
        }
        return sb.toString();
    }

    private String getDomainText(int position){
        if(domainData!=null){
            for(int i=0;i<domainData.size();i++){
                if(domainData.get(i).getType_id()==data.get(position).getDomain_id()){
                    return domainData.get(i).getType_name();
                }
            }
        }
        return "";
    }

    private String getStageText(int position){
        if(stageData!=null){
            for(int i=0;i<stageData.size();i++){
                if(stageData.get(i).getType_id()==data.get(position).getLast_stage_id()){
                   return stageData.get(i).getType_name();
                }
            }
        }
        return "";
    }

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                break;
        }
    }
}
