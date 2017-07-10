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
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.set.di.DaggerSetGroupComponent;
import com.qtin.sexyvc.ui.follow.set.di.SetGroupModule;
import com.qtin.sexyvc.ui.request.ChangeGroupRequest;
import com.qtin.sexyvc.utils.ConstantUtil;
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
    private long investor_id;

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
        investor_id=getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
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
                                    mPresenter.add(content);
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
        mPresenter.query(investor_id);
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
                if(data==null||data.isEmpty()){
                    return;
                }

                ChangeGroupRequest request=new ChangeGroupRequest();
                request.setInvestor_id(investor_id);

                ArrayList<Long> group_ids=new ArrayList<>();
                for(ConcernGroupEntity entity:data){
                    group_ids.add(entity.getGroup_id());
                }

                request.setGroup_ids(group_ids);
                mPresenter.changeGroup(request);

                break;
        }
    }

    @Override
    public void querySuccess(GroupEntity entity) {
        data.clear();
        if(entity!=null&&entity.getList()!=null){
            data.addAll(entity.getList());

            //设置是否已经在改组的状态
            if(entity.getJoin_groups()!=null&&!entity.getJoin_groups().isEmpty()){
                for(int i=0;i<data.size();i++){
                    ConcernGroupEntity group=data.get(i);
                    for(int j=0;j<entity.getJoin_groups().size();j++){
                        if(group.getGroup_id()==entity.getJoin_groups().get(j)){
                            group.setSelected(true);
                        }
                    }
                }
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addSuccess(long group_id, String group_name) {
        ConcernGroupEntity entity=new ConcernGroupEntity();
        entity.setGroup_name(group_name);
        entity.setGroup_id(group_id);
        data.add(entity);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeSuccess() {

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
