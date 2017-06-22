package com.qtin.sexyvc.ui.main.fragconcern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.OnLongItemClickListener;
import com.qtin.sexyvc.ui.concern.list.ConcernListActivity;
import com.qtin.sexyvc.ui.concern.search.ConcernSearchActivity;
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

    private ConcernGroupAdapter adapter;
    private ArrayList<ConcernGroupEntity> data=new ArrayList<>();
    private int page=1;
    private int page_size=Integer.MAX_VALUE;

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
        //获取数据
        mPresenter.query(page,page_size);
    }

    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter=new ConcernGroupAdapter(mActivity,data);

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                Bundle bundle=new Bundle();
                bundle.putString("group_name",data.get(position).getGroup_name());
                bundle.putLong("group_id",data.get(position).getGroup_id());
                gotoActivity(ConcernListActivity.class,bundle);
            }
        });
        adapter.setLongItemClickListener(new OnLongItemClickListener() {
            @Override
            public void onLongClickItem(final int position) {

                if(position==0){
                    return;
                }

                showBottomDialog(getResources().getString(R.string.modify_group_name), getResources().getString(R.string.delele_group),
                        getResources().getString(R.string.cancle), new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                showInput(getResources().getString(R.string.modify_group_name),
                                        getResources().getString(R.string.edit_group_name),
                                        getResources().getString(R.string.cancle),
                                        getResources().getString(R.string.good),
                                        new InputListerner() {
                                            @Override
                                            public void onComfirm(String content) {
                                                dismissInputDialog();
                                                ConcernGroupEntity group=data.get(position);
                                                mPresenter.edit(position,group.getGroup_id(),content,1);
                                            }

                                            @Override
                                            public void cancle() {
                                                dismissInputDialog();
                                            }
                                        }
                                );
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                deleteGroup(position);
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void deleteGroup(final int position){
        String title=String.format(getResources().getString(R.string.comfirm_delete_group),data.get(position).getGroup_name());

        showTwoButtonDialog(title, getResources().getString(R.string.cancle)
                , getResources().getString(R.string.comfirm), new TwoButtonListerner() {
                    @Override
                    public void leftClick() {
                        dismissTwoButtonDialog();
                    }

                    @Override
                    public void rightClick() {
                        dismissTwoButtonDialog();
                        ConcernGroupEntity group = data.get(position);
                        mPresenter.edit(position,group.getGroup_id(),group.getGroup_name(),0);
                    }
                });
    }

    private void showInput(String title, String warn,String stringLeft, String stringRight,InputListerner listerner){
        showInputDialog(title, warn, stringLeft, stringRight,listerner);
    }

    @Override
    public void showLoading() {
        showLoadingDialog("处理中...");
    }

    @Override
    public void hideLoading() {
        loadingDialogDismiss();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(mActivity, StringUtil.formatString(message));
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivAdd, R.id.searchContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAdd:
                showInput(getResources().getString(R.string.add_group),
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
                break;
            case R.id.searchContainer:
                gotoActivity(ConcernSearchActivity.class);
                break;
        }
    }


    @Override
    public void querySuccess(ArrayList<ConcernGroupEntity> data) {
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess(int position) {
        data.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void editSuccess(int position,String group_name) {
        ConcernGroupEntity group=data.get(position);
        group.setGroup_name(group_name);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addSuccess(long group_id,String group_name) {
        ConcernGroupEntity group=new ConcernGroupEntity();
        group.setGroup_id(group_id);
        group.setGroup_name(group_name);
        data.add(group);
        adapter.notifyDataSetChanged();
    }
}
