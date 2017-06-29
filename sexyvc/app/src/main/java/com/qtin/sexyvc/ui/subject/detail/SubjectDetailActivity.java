package com.qtin.sexyvc.ui.subject.detail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.DetailClickListener;
import com.qtin.sexyvc.ui.subject.SubjectDetailAdapter;
import com.qtin.sexyvc.ui.subject.bean.DetailBean;
import com.qtin.sexyvc.ui.subject.bean.SubjectContentEntity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.bean.ReplyBean;
import com.qtin.sexyvc.ui.subject.detail.di.DaggerSubjectDetailComponent;
import com.qtin.sexyvc.ui.subject.detail.di.SubjectDetailModule;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class SubjectDetailActivity extends MyBaseActivity<SubjectDetailPresent> implements SubjectDetailContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.actionContainer)
    LinearLayout actionContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private long subject_id;

    private boolean hasLoadedAllItems;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private SubjectDetailAdapter mAdapter;
    private ArrayList<DataTypeInterface> data = new ArrayList<>();
    private long reply_id;

    private EditText etInputComment;
    private Dialog replyDialog;

    private final static long DEFALUT_REPLY_ID=0;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectDetailComponent.builder().appComponent(appComponent).subjectDetailModule(new SubjectDetailModule(this)).build().inject(this);
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
        subject_id = getIntent().getExtras().getLong("subject_id");
        String title = getIntent().getExtras().getString("title");
        tvTitle.setText(title);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.query(subject_id, DEFALUT_REPLY_ID);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectDetailAdapter(this, data);
        mAdapter.setClickListener(new DetailClickListener() {
            @Override
            public void onClickDetailPraise(int position) {
                int handle_type = 0;
                SubjectContentEntity subjectDetailEntity = (SubjectContentEntity) data.get(0);
                if (subjectDetailEntity.getHas_praise() == 0) {
                    handle_type = 1;
                } else {
                    handle_type = 0;
                }
                mPresenter.praise(position, ConstantUtil.OBJECT_TYPE_SUBJECT, subject_id, handle_type);
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
                showReplyDialog(position, entity.getReply_id(),"回复@"+entity.getU_nickname());
            }
        });
        recyclerView.setAdapter(mAdapter);
        initPaginate();

        mPresenter.query(subject_id, DEFALUT_REPLY_ID);
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.query(subject_id, reply_id);
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
        finish();
    }

    @OnClick({R.id.ivLeft, R.id.ivShare, R.id.actionContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivShare:
                break;
            case R.id.actionContainer:
                showReplyDialog(-1, DEFALUT_REPLY_ID,"评论");
                break;
        }
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
    public void querySuccess(long reply_id, DetailBean detailBean) {
        if (reply_id == DEFALUT_REPLY_ID) {
            data.clear();
            if (detailBean.getDetail() != null) {
                data.add(detailBean.getDetail());
            }
        }
        if (detailBean.getReplies() != null) {
            ArrayList<ReplyBean> list = detailBean.getReplies().getList();
            if (list != null && !list.isEmpty()) {
                data.addAll(list);
                this.reply_id = list.get(list.size() - 1).getReply_id();
            }
        }

        if (data.size() - 1 < detailBean.getReplies().getTotal()) {
            hasLoadedAllItems = false;
        } else {
            hasLoadedAllItems = true;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void praiseSuccess(int position) {
        if (position == -1) {
            SubjectContentEntity subjectDetailEntity = (SubjectContentEntity) data.get(0);
            if (subjectDetailEntity.getHas_praise() == 0) {

                subjectDetailEntity.setHas_praise(1);
                subjectDetailEntity.setPraise_count(subjectDetailEntity.getPraise_count() + 1);
            } else {
                subjectDetailEntity.setHas_praise(0);
                subjectDetailEntity.setPraise_count(subjectDetailEntity.getPraise_count() - 1);
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
    public void replySuccess(int position, long reply_id, String content) {

        if (replyDialog != null && replyDialog.isShowing()) {
            replyDialog.dismiss();
        }

        ReplyBean entity = new ReplyBean();
        entity.setLike(0);
        entity.setHas_praise(0);
        entity.setCreate_time(System.currentTimeMillis() / 1000);
        entity.setReply_id(reply_id);
        if (mPresenter.getUserInfo() != null) {
            entity.setU_nickname(mPresenter.getUserInfo().getU_nickname());
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
        mAdapter.notifyDataSetChanged();
        if (replyDialog != null && replyDialog.isShowing()) {
            replyDialog.dismiss();
        }
    }

    @Override
    public void dialogWaitLoading() {
        showDialog("处理中...");
    }

    @Override
    public void dialogWaitDismiss() {
        dialogDismiss();
    }

    public void showReplyDialog(final int position, final long reply_id,String hint) {
        View view = LayoutInflater.from(this).inflate(R.layout.reply_dialog, null);
        etInputComment = (EditText) view.findViewById(R.id.etInputComment);
        etInputComment.setHint(hint);
        View tvPublishComment = view.findViewById(R.id.tvPublishComment);

        tvPublishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputComment.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("评论内容不能为空");
                    return;
                }
                mPresenter.reply(position,subject_id, reply_id, content);
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
}
