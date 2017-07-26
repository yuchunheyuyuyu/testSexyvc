package com.qtin.sexyvc.ui.search.action;

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
import com.qtin.sexyvc.ui.bean.KeyWordBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.search.action.di.DaggerSearchActionComponent;
import com.qtin.sexyvc.ui.search.action.di.SearchActionModule;
import com.qtin.sexyvc.ui.search.result.SearchResultActivity;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SearchActionActivity extends MyBaseActivity<SearchActionPresent> implements SearchActionContract.View {

    @BindView(R.id.tvChange)
    TextView tvChange;
    @BindView(R.id.etSearch)
    ClearableEditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String key_word;
    private int searchType= ConstantUtil.TYPE_INVESTOR;

    private boolean isForResult=false;

    private SearchActionAdapter mAdapter;
    private ArrayList<KeyWordBean> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchActionComponent.builder().appComponent(appComponent).searchActionModule(new SearchActionModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.search_action_activity;
    }

    @Override
    protected void initData() {
        isForResult=getIntent().getExtras().getBoolean(ConstantUtil.INTENT_IS_FOR_RESULT);
        key_word=getIntent().getExtras().getString(ConstantUtil.KEY_WORD_INTENT);
        searchType=getIntent().getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);

        if(searchType==ConstantUtil.TYPE_FUND){
            tvChange.setText(getResources().getString(R.string.fund));
        }else{
            tvChange.setText(getResources().getString(R.string.investor));
        }

        etSearch.setText(key_word);
        etSearch.setSelection(key_word.length());
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActionActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    search();
                    return true;
                }
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new SearchActionAdapter(this,data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(position==-1){
                    mPresenter.delete();
                    data.clear();
                    mAdapter.notifyDataSetChanged();
                }else{
                    KeyWordBean bean=new KeyWordBean();
                    bean.setKeyWord(data.get(position).getKeyWord());
                    mPresenter.insertKeys(bean);

                    gotoResult(data.get(position).getKeyWord());
                }
            }
        });

        mPresenter.getKeys();
    }

    private void search(){
        String key=etSearch.getText().toString().trim();
        if(!StringUtil.isBlank(key)){
            KeyWordBean bean=new KeyWordBean();
            bean.setKeyWord(key);
            mPresenter.insertKeys(bean);
        }

        gotoResult(key);
    }

    @Override
    protected boolean isNeedFinishAnim() {
        return false;
    }

    private void gotoResult(String word){

        if(isForResult){
            Intent intent=new Intent();
            intent.putExtra(ConstantUtil.KEY_WORD_INTENT,word);
            intent.putExtra(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,searchType);
            setResult(0,intent);
            finish();
        }else{
            Bundle bundle=new Bundle();
            bundle.putString(ConstantUtil.KEY_WORD_INTENT,word);
            bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,searchType);
            gotoActivity(SearchResultActivity.class,bundle);
            finish();
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick({R.id.changeContainer, R.id.tvClose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeContainer:
                if(searchType==ConstantUtil.TYPE_FUND){
                    searchType=ConstantUtil.TYPE_INVESTOR;
                    tvChange.setText(getResources().getString(R.string.investor));
                    etSearch.setHint(getResources().getString(R.string.hint_search_investor));
                }else{
                    searchType=ConstantUtil.TYPE_FUND;
                    tvChange.setText(getResources().getString(R.string.fund));
                    etSearch.setHint(getResources().getString(R.string.hint_search_fund));
                }

                break;
            case R.id.tvClose:
                finish();
                break;
        }
    }

    @Override
    public void querySuccess(ArrayList<KeyWordBean> keys) {
        data.addAll(keys);
        mAdapter.notifyDataSetChanged();
    }
}
