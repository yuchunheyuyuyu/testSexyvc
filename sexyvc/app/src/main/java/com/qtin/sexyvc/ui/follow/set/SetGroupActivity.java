package com.qtin.sexyvc.ui.follow.set;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ChangeFundGroupRequest;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.follow.set.di.DaggerSetGroupComponent;
import com.qtin.sexyvc.ui.follow.set.di.SetGroupModule;
import com.qtin.sexyvc.ui.request.ChangeContactGroupRequest;
import com.qtin.sexyvc.ui.request.ChangeInvestorGroupRequest;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    private long id;
    private int type;

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
        id=getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
        type=getIntent().getExtras().getInt(ConstantUtil.INTENT_TYPE_SET_GROUP);
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
                                    if("全部关注".equals(content)){
                                        showMessage("该组已存在");
                                        return;
                                    }
                                    if(data!=null&&!data.isEmpty()){

                                        for(ConcernGroupEntity entity:data){
                                            if(content.equals(entity.getGroup_name())){
                                                showMessage("该组已存在");
                                                return;
                                            }
                                        }
                                    }
                                    dismissInputDialog();

                                    if(type==ConstantUtil.TYPE_SET_GROUP_FUND){
                                        mPresenter.addFundGroup(content);
                                    }else{
                                        mPresenter.add(content);
                                    }
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
        if(type==ConstantUtil.TYPE_SET_GROUP_INVESTOR){
            mPresenter.query(id,ConstantUtil.DEFALUT_ID);
        }else if(type==ConstantUtil.TYPE_SET_GROUP_FUND){
            mPresenter.queryFundGroup(id);
        }else {
            mPresenter.query(ConstantUtil.DEFALUT_ID,id);
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

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                ArrayList<Long> group_ids=new ArrayList<>();
                if(data!=null&&!data.isEmpty()){
                    for(ConcernGroupEntity entity:data){
                        if(entity.isSelected()){
                            group_ids.add(entity.getGroup_id());
                        }
                    }
                }

                if(type==ConstantUtil.TYPE_SET_GROUP_INVESTOR){
                    ChangeInvestorGroupRequest request=new ChangeInvestorGroupRequest();
                    request.setInvestor_id(id);
                    request.setGroup_ids(group_ids);
                    mPresenter.changeGroup(request);
                }else if(type==ConstantUtil.TYPE_SET_GROUP_FUND){
                    ChangeFundGroupRequest request=new ChangeFundGroupRequest();
                    request.setObject_type(ConstantUtil.OBJECT_TYPE_FUND);
                    request.setObject_id(id);
                    request.setGroup_ids(group_ids);
                    mPresenter.changeFundGroup(request);
                }else{
                    ChangeContactGroupRequest request=new ChangeContactGroupRequest();
                    request.setContact_id(id);
                    request.setGroup_ids(group_ids);
                    mPresenter.changeContactGroup(request);
                }

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
        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        finish();
                    }
                });
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
