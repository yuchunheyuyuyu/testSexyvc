package com.qtin.sexyvc.ui.follow.set;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.set.di.DaggerSetGroupComponent;
import com.qtin.sexyvc.ui.follow.set.di.SetGroupModule;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SetGroupActivity extends MyBaseActivity<SetGroupPresent> implements SetGroupContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SetGroupAdapter mAdapter;
    private ArrayList<ConcernGroupEntity> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSetGroupComponent.builder().appComponent(appComponent).setGroupModule(new SetGroupModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.set_group_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_set_group));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.complete));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter=new SetGroupAdapter(this,data);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(position==data.size()){
                    showInputDialog(getResources().getString(R.string.add_group),
                            getResources().getString(R.string.input_new_group_name),
                            getResources().getString(R.string.cancle),
                            getResources().getString(R.string.good),
                            new InputListerner() {
                                @Override
                                public void onComfirm(String content) {
                                    dismissInputDialog();
                                    ConcernGroupEntity entity=new ConcernGroupEntity();
                                    entity.setGroup_name(content);
                                    data.add(entity);
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void cancle() {
                                    dismissInputDialog();
                                }
                            }
                    );
                }else{
                    data.get(position).changeStatus();
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter.notifyDataSetChanged();
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
