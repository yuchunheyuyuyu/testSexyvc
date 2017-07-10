package com.qtin.sexyvc.ui.follow.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.detail.ConcernDetailActivity;
import com.qtin.sexyvc.ui.follow.list.ConcernListAdapter;
import com.qtin.sexyvc.ui.follow.search.di.ConcernSearchModule;
import com.qtin.sexyvc.ui.follow.search.di.DaggerConcernSearchComponent;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ConcernSearchActivity extends MyBaseActivity<ConcernSearchPresent> implements ConcernSearchContract.View {

    @BindView(R.id.tvCancle)
    TextView tvCancle;
    @BindView(R.id.etSearch)
    ClearableEditText etSearch;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private List<ConcernListEntity> data=new ArrayList<>();
    private ConcernListAdapter mAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerConcernSearchComponent.builder().appComponent(appComponent).concernSearchModule(new ConcernSearchModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.concern_search_activity;
    }

    @Override
    protected void initData() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new ConcernListAdapter(this,data);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(position==ConstantUtil.ACTION_CLEAR_HISTORY){
                    mPresenter.clearHistory();
                    data.clear();
                    mAdapter.notifyDataSetChanged();
                }else{
                    //先保存
                    mPresenter.insertConcern(data.get(position));
                    //再跳转
                    Bundle bundle=new Bundle();
                    bundle.putLong("contact_id",data.get(position).getContact_id());
                    bundle.putLong("investor_id",data.get(position).getInvestor_id());
                    gotoActivity(ConcernDetailActivity.class,bundle);
                }
            }
        });
        recycleView.setAdapter(mAdapter);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ConcernSearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    search();
                    return true;
                }
                return false;
            }
        });

        //搜索的历史
        List<ConcernListEntity> local=mPresenter.queryLocalHistory();
        if(local!=null&&!local.isEmpty()){
            ConcernListEntity entity=new ConcernListEntity();
            entity.setContact_id(ConstantUtil.DEFALUT_ID);
            data.add(entity);
            data.addAll(local);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void search(){
        String keyWord=etSearch.getText().toString();
        if(StringUtil.isBlank(keyWord)){
            showMessage("关键词不能为空");
            return;
        }
        mPresenter.searchConcern(keyWord);
    }


    @Override
    public void showLoading() {
        showDialog("正在搜索");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
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

    @OnClick(R.id.tvCancle)
    public void onClick() {
        finish();
    }

    @Override
    public void searchSuccess(ConcernEntity entity) {
        data.clear();
        if(entity.getList()!=null){
            data.addAll(entity.getList());
        }
        mAdapter.notifyDataSetChanged();
    }
}
