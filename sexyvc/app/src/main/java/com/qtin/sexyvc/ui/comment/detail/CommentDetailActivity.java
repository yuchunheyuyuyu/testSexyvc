package com.qtin.sexyvc.ui.comment.detail;

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
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentBean;
import com.qtin.sexyvc.ui.comment.detail.di.CommentDetailModule;
import com.qtin.sexyvc.ui.comment.detail.di.DaggerCommentDetailComponent;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class CommentDetailActivity extends MyBaseActivity<CommentDetailPresent> implements CommentDetailContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvFooter)
    TextView tvFooter;

    private String comment_id;
    private EditText etInputComment;
    private Dialog replyDialog;

    private CommentDetailAdapter mAdapter;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommentDetailComponent.builder().appComponent(appComponent).commentDetailModule(new CommentDetailModule(this)).build().inject(this);
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
        comment_id = getIntent().getExtras().getString("comment_id");
        tvFooter.setText(getResources().getString(R.string.reply_comment));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
                break;
        }
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
                //reply(position, reply_id, content);
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
    public void praiseSuccess(int position) {

    }

    @Override
    public void replySuccess(int position, long reply_id, String content) {

    }

    @Override
    public void dialogWaitLoading() {

    }

    @Override
    public void dialogWaitDismiss() {

    }

    @Override
    public void querySuccess(CommentBean commentBean) {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }
}
