package com.qtin.sexyvc.ui.more;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.investor.CaseAdapter2;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/7/7.
 */

public class MoreCaseActivity extends MyBaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<CaseBean> data;
    private CaseAdapter2 mAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.more_case_activity;
    }

    @Override
    protected void initData() {
        String title=getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        data = getIntent().getExtras().getParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY);

        tvTitle.setText(StringUtil.formatString(title));
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));

        mAdapter=new CaseAdapter2(this,data);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
