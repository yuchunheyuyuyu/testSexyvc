package com.qtin.sexyvc.ui.road.show;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.ui.bean.DetailClickListener;
import com.qtin.sexyvc.ui.bean.ReplyBean;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.road.show.bean.RoadDetailBean;
import com.qtin.sexyvc.ui.road.show.bean.RoadShowBean;
import com.qtin.sexyvc.ui.road.show.di.DaggerRoadDetailComponent;
import com.qtin.sexyvc.ui.road.show.di.RoadDetailModule;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class RoadDetailActivity extends MyBaseActivity<RoadDetailPresent> implements RoadDetailContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvFooter)
    TextView tvFooter;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;

    private final static long DEFALUT_REPLY_ID = 0;

    private List<DataTypeInterface> data= new ArrayList<>();
    private RoadDetailAdapter mAdapter;
    private long roadId;
    private static final int page_size=15;
    private long last_id= ConstantUtil.DEFALUT_ID;

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private RoadDetailBean detailBean;
    private int total;
    //回应dialog
    private EditText etInputComment;
    private Dialog replyDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRoadDetailComponent.builder().appComponent(appComponent).roadDetailModule(new RoadDetailModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initData() {
        roadId=getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
        tvFooter.setText(getString(R.string.reply_comment));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                last_id=ConstantUtil.DEFALUT_ID;
                mPresenter.queryRoadDetail(roadId,page_size,last_id);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new RoadDetailAdapter(this,data);
        mAdapter.setClickListener(new DetailClickListener() {
            @Override
            public void onClickDetailPraise(int position) {
                int handle_type = 0;
                if (detailBean.getHas_praise() == 0) {
                    handle_type = 1;
                } else {
                    handle_type = 0;
                }
                mPresenter.praise(position, ConstantUtil.OBJECT_TYPE_ROAD, roadId, handle_type);
            }

            @Override
            public void onClickDetailReply(int position) {

            }

            @Override
            public void onClickItemPraise(int position) {
                int handle_type = 0;
                ReplyBean entity = (ReplyBean) data.get(position);
                if (entity.getHas_praise() == 0) {
                    handle_type = 1;
                } else {
                    handle_type = 0;
                }
                mPresenter.praise(position, ConstantUtil.OBJECT_TYPE_REPLY, entity.getReply_id(), handle_type);
            }

            @Override
            public void onClickItemReply(int position) {
                ReplyBean entity = (ReplyBean) data.get(position);
                showReplyDialog(position, entity.getReply_id(), "回复@" + entity.getU_nickname());
            }

            @Override
            public void onClickInvestor() {
                Bundle bundle = new Bundle();
                bundle.putLong("investor_id", detailBean.getInvestor_id());
                gotoActivity(InvestorDetailActivity.class, bundle);
            }
        });
        recyclerView.setAdapter(mAdapter);
        initPaginate();
        mPresenter.queryRoadDetail(roadId,page_size,last_id);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.queryRoadDetail(roadId, page_size,last_id);
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
        UiUtils.showToastShort(this,message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.ivLeft, R.id.ivShare, R.id.actionContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                if (detailBean != null) {
                    final UMWeb web = new UMWeb(Api.SHARE_BASE_URL+Api.SHARE_ROAD + roadId);
                    web.setTitle("对" + detailBean.getInvestor_name()+"路演的评价");//标题
                    web.setDescription(detailBean.getInvestor_name() + "的路演是被人这样评价的…");
                    web.setThumb(new UMImage(this, CommonUtil.getAbsolutePath(detailBean.getInvestor_avatar())));  //缩略图

                    showShareDialog(new onShareClick() {
                        @Override
                        public void onClickShare(int platForm) {

                            dismissShareDialog();
                            switch (platForm) {
                                case ConstantUtil.SHARE_WECHAT:
                                    new ShareAction(RoadDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_WX_CIRCLE:
                                    new ShareAction(RoadDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_QQ:
                                    new ShareAction(RoadDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.QQ)
                                            .setCallback(shareListener).share();
                                    break;
                                case ConstantUtil.SHARE_SINA:
                                    new ShareAction(RoadDetailActivity.this)
                                            .withMedia(web)
                                            .setPlatform(SHARE_MEDIA.SINA)
                                            .setCallback(shareListener).share();
                                    break;
                            }
                        }
                    });
                }
                break;
            case R.id.actionContainer:
                showReplyDialog(-1, DEFALUT_REPLY_ID, "回应评论");
                break;
        }
    }

    @Override
    public void querySuccess(long last_id, RoadShowBean roadShowBean) {
        if(last_id==ConstantUtil.DEFALUT_ID){
            data.clear();
            detailBean=roadShowBean.getDetail();
            data.add(roadShowBean.getDetail());
            total=roadShowBean.getReplies().getTotal();

            if(roadShowBean.getDetail()!=null){
                tvTitle.setText(String.format(getResources().getString(R.string.title_road_detail),
                        StringUtil.formatString(roadShowBean.getDetail().getInvestor_name())));
            }
        }
        if(roadShowBean.getReplies()!=null&&roadShowBean.getReplies().getList()!=null
                &&!roadShowBean.getReplies().getList().isEmpty()){

            data.addAll(roadShowBean.getReplies().getList());
            last_id=roadShowBean.getReplies().getList().get(0).getReply_id();
        }
        if(data.size()-1<total){
            hasLoadedAllItems=false;
        }else{
            hasLoadedAllItems=true;
        }
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void praiseSuccess(int position) {
        if (position == -1) {
            RoadDetailBean contentBean = (RoadDetailBean) data.get(0);
            if (contentBean.getHas_praise() == 0) {

                contentBean.setHas_praise(1);
                contentBean.setLike(contentBean.getLike() + 1);
            } else {
                contentBean.setHas_praise(0);
                contentBean.setLike(contentBean.getLike() - 1);
            }
        } else {
            ReplyBean entity = (ReplyBean) data.get(position);
            if (entity.getHas_praise() == 0) {
                entity.setHas_praise(1);
                entity.setLike(entity.getLike() + 1);
            } else {
                entity.setHas_praise(0);
                entity.setLike(entity.getLike() - 1);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replySuccess(int position, long reply_id, String content, int is_anon) {
        if (replyDialog != null && replyDialog.isShowing()) {
            replyDialog.dismiss();
        }

        ReplyBean entity = new ReplyBean();
        entity.setLike(0);
        entity.setIs_anon(is_anon);
        entity.setU_auth_state(mPresenter.getUserInfo().getU_auth_state());
        entity.setU_auth_type(mPresenter.getUserInfo().getU_auth_type());
        entity.setHas_praise(0);
        entity.setCreate_time(System.currentTimeMillis() / 1000);
        entity.setReply_id(reply_id);

        if (mPresenter.getUserInfo() != null) {
            if(is_anon==0){
                entity.setU_nickname(mPresenter.getUserInfo().getU_nickname());
            }else{
                entity.setU_nickname(getString(R.string.defalut_nick));
            }
            entity.setU_avatar(mPresenter.getUserInfo().getU_avatar());
        }
        if (position == -1) {
            entity.setParent_id(0);
            entity.setReply_content(content);
        } else {
            ReplyBean replyEntity = (ReplyBean) data.get(position);
            entity.setParent_id(replyEntity.getReply_id());
            entity.setReply_content("@" + replyEntity.getU_nickname() + ":" + content);
        }
        data.add(1, entity);
        detailBean.setReply_count(detailBean.getReply_count() + 1);
        mAdapter.notifyDataSetChanged();
        if (replyDialog != null && replyDialog.isShowing()) {
            replyDialog.dismiss();
        }
    }

    public void showReplyDialog(final int position, final long reply_id, String hint) {
        View view = LayoutInflater.from(this).inflate(R.layout.reply_dialog, null);
        etInputComment = (EditText) view.findViewById(R.id.etInputComment);
        etInputComment.setHint(hint);
        View tvPublishComment = view.findViewById(R.id.tvPublishComment);
        View anonymousContainer = view.findViewById(R.id.anonymousContainer);
        final ImageView ivAnonymous = (ImageView) view.findViewById(R.id.ivAnonymous);

        anonymousContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAnonymous.isSelected()) {
                    ivAnonymous.setSelected(false);
                } else {
                    ivAnonymous.setSelected(true);
                }
            }
        });

        tvPublishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputComment.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("评论内容不能为空");
                    return;
                }
                int is_anon=0;
                if(ivAnonymous.isSelected()){
                    is_anon=1;
                }else{
                    is_anon=0;
                }
                mPresenter.reply(position, roadId, reply_id, content,is_anon);
            }
        });
        replyDialog = new Dialog(this);
        replyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        replyDialog.setContentView(view);
        Window inputWindow = replyDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        replyDialog.setCanceledOnTouchOutside(true);
        replyDialog.show();

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        etInputComment.setFocusable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) etInputComment.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }

    @Override
    public void dialogWaitLoading() {
        showDialog("处理中...");
    }

    @Override
    public void dialogWaitDismiss() {
        dialogDismiss();
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
