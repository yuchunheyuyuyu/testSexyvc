package com.qtin.sexyvc.ui.main.fragconcern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.OnLongItemClickListener;
import com.qtin.sexyvc.ui.create.investor.CreateInvestorActivity;
import com.qtin.sexyvc.ui.follow.list.ConcernListActivity;
import com.qtin.sexyvc.ui.follow.search.ConcernSearchActivity;
import com.qtin.sexyvc.ui.main.fragconcern.di.ConcernGroupAdapter;
import com.qtin.sexyvc.ui.main.fragconcern.di.ConcernModule;
import com.qtin.sexyvc.ui.main.fragconcern.di.DaggerConcernComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ConcernGroupAdapter adapter;
    private ArrayList<ConcernGroupEntity> data = new ArrayList<>();
    private int page = 1;
    private int page_size = Integer.MAX_VALUE;

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

    private void initView() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(page, page_size);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new ConcernGroupAdapter(mActivity, data);

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("group_name", data.get(position).getGroup_name());
                bundle.putLong("group_id", data.get(position).getGroup_id());
                gotoActivity(ConcernListActivity.class, bundle);
            }
        });
        adapter.setLongItemClickListener(new OnLongItemClickListener() {
            @Override
            public void onLongClickItem(final int position) {

                if (position == 0) {
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
                                                ConcernGroupEntity group = data.get(position);
                                                mPresenter.edit(position, group.getGroup_id(), content, 1);
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

    private void deleteGroup(final int position) {
        String title = String.format(getResources().getString(R.string.comfirm_delete_group), data.get(position).getGroup_name());

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
                        mPresenter.edit(position, group.getGroup_id(), group.getGroup_name(), 0);
                    }
                });
    }

    private void showInput(String title, String warn, String stringLeft, String stringRight, InputListerner listerner) {
        showInputDialog(title, warn, stringLeft, stringRight, listerner);
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
                showBottomDialog(getString(R.string.input_investor_by_hand), getString(R.string.add_group)
                        , getString(R.string.cancle), new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                gotoActivity(CreateInvestorActivity.class);
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                showInput(getResources().getString(R.string.add_group),
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
                                                if(data!=null){
                                                    for(ConcernGroupEntity entity:data){
                                                        if(content.trim().equals(entity.getGroup_name())){
                                                            showMessage("该组已存在");
                                                            return;
                                                        }
                                                    }
                                                }

                                                dismissInputDialog();
                                                mPresenter.add(content);
                                            }

                                            @Override
                                            public void cancle() {
                                                dismissInputDialog();
                                            }
                                        }
                                );
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });


                break;
            case R.id.searchContainer:
                gotoActivity(ConcernSearchActivity.class);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取数据
        mPresenter.query(page, page_size);
    }

    @Override
    public void querySuccess(GroupEntity groupEntity) {

        if(page==1){
            data.clear();

            ConcernGroupEntity entity=new ConcernGroupEntity();
            entity.setGroup_name("全部关注");
            entity.setMember_count(groupEntity.getContact_count());

            data.add(0,entity);
        }
        ArrayList<ConcernGroupEntity>list=groupEntity.getList();
        if(list!=null){
            data.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess(int position) {
        data.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void editSuccess(int position, String group_name) {
        ConcernGroupEntity group = data.get(position);
        group.setGroup_name(group_name);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addSuccess(long group_id, String group_name) {
        ConcernGroupEntity group = new ConcernGroupEntity();
        group.setGroup_id(group_id);
        group.setGroup_name(group_name);
        data.add(group);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startRefresh() {
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
    public void endRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
