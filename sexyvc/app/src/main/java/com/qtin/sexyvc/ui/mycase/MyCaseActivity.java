package com.qtin.sexyvc.ui.mycase;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.DeleteCaseRequest;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.investor.CaseAdapter2;
import com.qtin.sexyvc.ui.mycase.add.AddCaseActivity;
import com.qtin.sexyvc.ui.mycase.di.DaggerMyCaseComponent;
import com.qtin.sexyvc.ui.mycase.di.MyCaseModule;
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
public class MyCaseActivity extends MyBaseActivity<MyCasePresent> implements MyCaseContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;

    private ArrayList<CaseBean> data=new ArrayList<>();
    private CaseAdapter2 mAdapter;

    public static final int REQUEST_CODE_ADD_CASE=0x002b;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMyCaseComponent.builder().appComponent(appComponent).myCaseModule(new MyCaseModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.my_case_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的投资案例");
        tvRight.setSelected(false);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.edit));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.queryMyCase();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mAdapter=new CaseAdapter2(this,data);
        mAdapter.setOnAddProjectListener(new CaseAdapter2.OnAddProjectListener() {
            @Override
            public void onClick() {
                gotoActivityForResult(AddCaseActivity.class,REQUEST_CODE_ADD_CASE);
            }
        });
        mAdapter.setOnDeleteCaseListener(new CaseAdapter2.OnDeleteCaseListener() {
            @Override
            public void onClickDelete(int position) {
                DeleteCaseRequest request=new DeleteCaseRequest();
                ArrayList<Long> case_ids=new ArrayList<Long>();
                case_ids.add(data.get(position).getCase_id());
                request.setCase_ids(case_ids);
                mPresenter.deleteCase(request);
            }
        });
        recyclerView.setAdapter(mAdapter);
        mPresenter.queryMyCase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        switch(requestCode){
            case REQUEST_CODE_ADD_CASE:
                CaseBean bean=data.getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
                if(bean!=null){
                    addLastData(false);
                    this.data.add(bean);
                    addLastData(true);
                    mAdapter.notifyDataSetChanged();
                }
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
    public void deleteSuccess(long case_id) {
        for(CaseBean bean:data){
            if(case_id==bean.getCase_id()){
                data.remove(bean);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra(ConstantUtil.INTENT_NUM,data.size());
        setResult(0,intent);
        super.finish();
    }

    @Override
    public void querySuccess(ListBean<CaseBean> listBean) {
        data.clear();
        data.addAll(listBean.getList());
        addLastData(true);
        mAdapter.notifyDataSetChanged();
    }

    private void addLastData(boolean isAdd){
        //先删除添加项目的选项
        for(CaseBean bean:data){
            if(bean.getCase_id()==ConstantUtil.DEFALUT_ID){
                data.remove(bean);
            }
        }

        if(isAdd){
            CaseBean bean=new CaseBean();
            bean.setCase_id(ConstantUtil.DEFALUT_ID);
            data.add(bean);
        }
    }

    @Override
    public void showNetErrorView() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContentView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.GONE) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.ivErrorStatus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(tvRight.isSelected()){
                    tvRight.setSelected(false);
                    tvRight.setText(getString(R.string.edit));
                    mAdapter.setShowDelete(false);
                    addLastData(true);
                }else{
                    tvRight.setSelected(true);
                    tvRight.setText(getString(R.string.complete));
                    mAdapter.setShowDelete(true);
                    addLastData(false);
                }
                break;
            case R.id.ivErrorStatus:
                break;
        }
    }
}
