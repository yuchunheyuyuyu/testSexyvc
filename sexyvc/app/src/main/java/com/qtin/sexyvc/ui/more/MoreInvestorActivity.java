package com.qtin.sexyvc.ui.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.main.fragInvestor.InvestorAdapter;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/7/7.
 */

public class MoreInvestorActivity extends MyBaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<InvestorEntity> originData;
    private InvestorAdapter mAdapter;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_list_white_activity;
    }

    @Override
    protected void initData() {
        originData=getIntent().getExtras().getParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY);
        String title=getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        tvTitle.setText(title);

        if(originData!=null){
            data.addAll(originData);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new InvestorAdapter(this,data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(data.get(position) instanceof InvestorEntity){
                    Bundle bundle = new Bundle();
                    bundle.putLong("investor_id", ((InvestorEntity)data.get(position)).getInvestor_id());
                    gotoActivity(InvestorDetailActivity.class, bundle);
                }
            }
        });
    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
