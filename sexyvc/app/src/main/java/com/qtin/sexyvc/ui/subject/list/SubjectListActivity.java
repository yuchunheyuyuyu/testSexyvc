package com.qtin.sexyvc.ui.subject.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.subject.SujectListAdapter;
import com.qtin.sexyvc.ui.subject.bean.SubjectBannerEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectBean;
import com.qtin.sexyvc.ui.subject.bean.SubjectListEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectListInterface;
import com.qtin.sexyvc.ui.subject.detail.SubjectDetailActivity;
import com.qtin.sexyvc.ui.subject.list.di.DaggerSubjectListComponent;
import com.qtin.sexyvc.ui.subject.list.di.SubjectListModule;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class SubjectListActivity extends MyBaseActivity<SubjectListPresent> implements SubjectListContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.headerLine)
    View headerLine;
    @BindView(R.id.headContainer)
    RelativeLayout headContainer;

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private SujectListAdapter mAdapter;
    private ArrayList<SubjectListInterface> data = new ArrayList<>();
    private long subject_id;

    private int mDistance;//滚动的距离
    private int maxDistance;//监测最大的

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectListComponent.builder().appComponent(appComponent).subjectListModule(new SubjectListModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.subject_list_activity;
    }


    @Override
    protected void initData() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(0, 1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SujectListAdapter(this, data);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                if(data.get(position) instanceof SubjectListEntity){
                    Bundle bundle=new Bundle();
                    SubjectListEntity entity= (SubjectListEntity) data.get(position);
                    bundle.putLong("subject_id",entity.getSubject_id());
                    bundle.putString("title",entity.getTitle());
                    gotoActivity(SubjectDetailActivity.class,bundle);
                }

            }
        });
        recyclerView.setAdapter(mAdapter);

        initPaginate();
        mPresenter.query(0, 1);

        maxDistance= (int) DeviceUtils.dpToPixel(this,140);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mDistance+=dy;
                float percent=mDistance*1f/maxDistance>1?1:mDistance*1f/maxDistance;

                int alpha = (int) (percent * 255);
                Log.e("透明度","=======透明度====="+percent);
                //整个背景
                int argb = Color.argb(alpha, 255, 255, 255);
                headContainer.setBackgroundColor(argb);
                //间隔线
                int lineColor=Color.argb(alpha,224,224,226);
                headerLine.setBackgroundColor(lineColor);
                //标题
                int titleColor=Color.argb(alpha,59,67,87);
                tvTitle.setTextColor(titleColor);

                //返回键
                if(mDistance==0){
                    ivLeft.setSelected(false);
                    ivLeft.setAlpha(255);
                }else{
                    ivLeft.setSelected(true);
                    ivLeft.setAlpha(alpha);
                }
            }
        });
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.query(subject_id, 0);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(recyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void showLoading() {
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
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void querySuccess(int need_banner, SubjectBean subjectBean) {
        if (need_banner == 1) {
            data.clear();
            SubjectBannerEntity bannerEntity = new SubjectBannerEntity();
            bannerEntity.setBanners(subjectBean.getBanners());
            data.add(bannerEntity);
        }
        if (subjectBean.getSubjects() != null) {
            ArrayList<SubjectListEntity> list = subjectBean.getSubjects().getList();
            if (list != null && !list.isEmpty()) {
                data.addAll(subjectBean.getSubjects().getList());
                subject_id = list.get(list.size() - 1).getSubject_id();
            }
        }

        if (data.size() - 1 < subjectBean.getSubjects().getTotal()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        mAdapter.notifyDataSetChanged();
    }
}
