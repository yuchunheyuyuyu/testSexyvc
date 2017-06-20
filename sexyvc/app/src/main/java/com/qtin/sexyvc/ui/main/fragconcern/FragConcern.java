package com.qtin.sexyvc.ui.main.fragconcern;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.main.fragconcern.di.ConcernGroupAdapter;
import com.qtin.sexyvc.ui.main.fragconcern.di.ConcernModule;
import com.qtin.sexyvc.ui.main.fragconcern.di.DaggerConcernComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */

public class FragConcern extends MyBaseFragment<ConcernPresent> implements ConcernContract.View {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<ConcernGroupEntity> data=new ArrayList<>();

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerConcernComponent.builder().appComponent(appComponent).concernModule(new ConcernModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_concern;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        ConcernGroupAdapter adapter=new ConcernGroupAdapter(mActivity,data);
        recyclerView.setAdapter(adapter);

        data.add(new ConcernGroupEntity("全部关注",122));
        data.add(new ConcernGroupEntity("VR 投资人",22));
        data.add(new ConcernGroupEntity("体育投资人",50));
        data.add(new ConcernGroupEntity("人人车",50));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick({R.id.ivAdd, R.id.tvEdit, R.id.searchContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAdd:
                break;
            case R.id.tvEdit:
                break;
            case R.id.searchContainer:
                break;
        }
    }
}
