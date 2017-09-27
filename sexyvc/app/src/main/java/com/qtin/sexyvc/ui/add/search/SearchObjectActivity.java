package com.qtin.sexyvc.ui.add.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.add.search.di.DaggerSearchObjectComponent;
import com.qtin.sexyvc.ui.add.search.di.SearchObjectModule;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.request.InvestorRequest;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.action.RoadCommentActivity;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SearchObjectActivity extends MyBaseActivity<SearchObjectPresent> implements SearchObjectContract.View {

    @BindView(R.id.etSearch)
    ClearableEditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    private int typeComment;

    private SearchObjectAdapter mAdapter;
    private ArrayList<InvestorEntity> data = new ArrayList<>();

    private boolean hasLoadedAllItems;
    private int page = 1;
    private final int page_size = 15;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private com.qtin.sexyvc.ui.investor.bean.InvestorBean investorBean;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchObjectComponent.builder().appComponent(appComponent).searchObjectModule(new SearchObjectModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.search_object_activity;
    }

    @Override
    protected void initData() {
        typeComment = getIntent().getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchObjectAdapter(this, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                mPresenter.queryDetail(data.get(position).getInvestor_id(), ConstantUtil.DEFALUT_ID, page_size, 1);
            }
        });

        //initPaginate();

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchObjectActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    page = 1;
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    page++;
                    search();
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

    private void search() {
        InvestorRequest request = new InvestorRequest();
        request.setPage(page);
        request.setPage_size(page_size);
        //行业
        ArrayList<Long> domains = new ArrayList<>();
        //阶段
        ArrayList<Long> stages = new ArrayList<>();
        request.setDomains(domains);
        request.setStages(stages);
        String keyWord = etSearch.getText().toString().trim();
        request.setKeyword(keyWord);
        request.setSort(0);
        //mPresenter.queryInvestors(request);

        mPresenter.queryObjects(keyWord);
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

    @OnClick(R.id.tvCancle)
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
    public void queryInvestorSuccess(InvestorBean bean) {
        if (page == 1) {
            data.clear();
        }
        if (bean.getList() != null) {
            data.addAll(bean.getList());
        }
        if (bean.getTotal() > data.size()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        if(data.isEmpty()){
            showEmptyView();
        }else{
            showContentView();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryDetailSuccess(CallBackBean backBean) {
        if (backBean.getInvestor() != null) {
            investorBean = backBean.getInvestor();
            if (mPresenter.getUserInfo() != null) {
                //创始人逻辑
                if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER
                        || mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {
                    if (typeComment == ConstantUtil.COMMENT_TYPE_ROAD) {
                        if (investorBean.getHas_roadshow() == 0) {
                            if (investorBean.getHas_score() == 0) {
                                gotoScore(ConstantUtil.INTENT_ROAD_COMMENT);
                            } else {
                                gotoRoad();
                            }
                        } else {
                            String format = getResources().getString(R.string.format_has_road);
                            String title = String.format(format, StringUtil.formatString(investorBean.getInvestor_name()));
                            showComfirmDialog(title, null, getResources().getString(R.string.comfirm), new ComfirmListerner() {
                                @Override
                                public void onComfirm() {
                                    dismissComfirmDialog();
                                }
                            });
                        }
                    } else if (typeComment == ConstantUtil.COMMENT_TYPE_EDIT) {
                        if (investorBean.getHas_score() == 0) {
                            gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                        } else {
                            gotoComment();
                        }
                    } else if (typeComment == ConstantUtil.COMMENT_TYPE_NONE) {
                        showBottomDialog(null, getString(R.string.sent_road_comment), getString(R.string.sent_text_comment), getString(R.string.cancle), new SelecteListerner() {
                            @Override
                            public void onFirstClick() {
                                dismissBottomDialog();
                                if (investorBean.getHas_roadshow() == 0) {
                                    if (investorBean.getHas_score() == 0) {
                                        gotoScore(ConstantUtil.INTENT_ROAD_COMMENT);
                                    } else {
                                        gotoRoad();
                                    }
                                } else {
                                    String format = getResources().getString(R.string.format_has_road);
                                    String title = String.format(format, StringUtil.formatString(investorBean.getInvestor_name()));
                                    UiUtils.showToastShort(SearchObjectActivity.this, title);
                                }
                            }

                            @Override
                            public void onSecondClick() {
                                dismissBottomDialog();
                                if (investorBean.getHas_score() == 0) {
                                    gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                                } else {
                                    gotoComment();
                                }
                            }

                            @Override
                            public void onCancle() {
                                dismissBottomDialog();
                            }
                        });
                    }
                } else {
                    if (investorBean.getHas_score() == 0) {
                        gotoScore(ConstantUtil.INTENT_TEXT_COMMENT);
                    } else {
                        gotoComment();
                    }
                }
            }
        }
    }

    @Override
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }

    /**
     * 进入评分
     */
    private void gotoScore(int intent) {
        gotoActivity(RateActivity.class, getBundle(intent));
    }

    /**
     * 进入评论或者追评
     */
    private void gotoComment() {
        gotoActivity(ReviewActivity.class, getBundle(ConstantUtil.INTENT_TEXT_COMMENT));
    }

    /**
     * 进入路演评价
     */
    private void gotoRoad() {
        Bundle bundle = getBundle(ConstantUtil.INTENT_ROAD_COMMENT);
        bundle.putInt(ConstantUtil.INTENT_INDEX, 0);
        gotoActivity(RoadCommentActivity.class, bundle);
    }

    private Bundle getBundle(int intent) {
        Bundle bundle = new Bundle();
        InvestorInfoBean infoBean = new InvestorInfoBean();
        infoBean.setIntent(intent);
        infoBean.setInvestor_id(investorBean.getInvestor_id());
        infoBean.setFund_id(investorBean.getFund_id());
        infoBean.setFund_name(investorBean.getFund_name());
        infoBean.setInvestor_avatar(investorBean.getInvestor_avatar());
        infoBean.setTitle(investorBean.getInvestor_title());
        infoBean.setInvestor_name(investorBean.getInvestor_name());
        infoBean.setInvestor_uid(investorBean.getInvestor_uid());
        infoBean.setTags(investorBean.getTags());
        infoBean.setHas_comment(investorBean.getHas_comment());
        infoBean.setHas_roadshow(investorBean.getHas_roadshow());
        infoBean.setHas_score(investorBean.getHas_score());
        infoBean.setScore_value(investorBean.getScore_value());
        infoBean.setComment_id(investorBean.getComment_id());
        infoBean.setComment_title(investorBean.getComment_title());
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE, infoBean);
        return bundle;
    }

    @OnClick({R.id.ivEmptyStatus, R.id.ivErrorStatus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEmptyStatus:
                break;
            case R.id.ivErrorStatus:
                page = 1;
                search();
                break;
        }
    }

    @Override
    public void showNetErrorView() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContentView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
        if (emptyLayout.getVisibility() == View.GONE) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }
}
