package com.qtin.sexyvc.ui.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.review.di.DaggerReviewComponent;
import com.qtin.sexyvc.ui.review.di.ReviewModule;
import com.qtin.sexyvc.ui.road.success.SuccessActivity;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class ReviewActivity extends MyBaseActivity<ReviewPresent> implements ReviewContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ratingScore)
    RatingBar ratingScore;
    @BindView(R.id.etCommentTitle)
    EditText etCommentTitle;
    @BindView(R.id.tvCommentTitle)
    TextView tvCommentTitle;
    @BindView(R.id.etCommentContent)
    EditText etCommentContent;
    @BindView(R.id.ivSwitch)
    ImageView ivSwitch;
    @BindView(R.id.tvAgreement)
    TextView tvAgreement;

    private InvestorInfoBean investorInfoBean;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerReviewComponent.builder().appComponent(appComponent).reviewModule(new ReviewModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.review_activity;
    }

    @Override
    protected void initData() {
        investorInfoBean = getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);

        if(investorInfoBean.getHas_comment()==1){
            tvCommentTitle.setVisibility(View.VISIBLE);
            etCommentTitle.setVisibility(View.GONE);
            tvCommentTitle.setText(StringUtil.formatString(investorInfoBean.getComment_title()));
            etCommentContent.setHint(getResources().getString(R.string.hint_review_comment_plus));
        }else{
            tvCommentTitle.setVisibility(View.GONE);
            etCommentTitle.setVisibility(View.VISIBLE);
            etCommentContent.setHint(getResources().getString(R.string.hint_review_content));
        }

        ratingScore.setRating(investorInfoBean.getScore_value());
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.commit));
        tvTitle.setText(getResources().getString(R.string.evaluate)+investorInfoBean.getInvestor_name());

    }

    @Override
    public void showLoading() {
        showDialog("正在提交");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
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

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.ivSwitch, R.id.tvAgreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                String content=etCommentContent.getText().toString();
                if(investorInfoBean.getHas_comment()==1){
                    if(StringUtil.isBlank(content)){
                        showMessage("追评内容不能为空");
                        return;
                    }
                    mPresenter.appendInvestor(content,investorInfoBean.getComment_id());
                }else{
                    String title=etCommentTitle.getText().toString();
                    if(StringUtil.isBlank(title)){
                        showMessage("标题不能为空");
                        return;
                    }
                    if(StringUtil.isBlank(content)){
                        showMessage("评论内容不能为空");
                        return;
                    }
                    int is_anon=0;
                    if(ivSwitch.isSelected()){
                        is_anon=1;
                    }
                    mPresenter.commentInvestor(title,content,investorInfoBean.getInvestor_id(),investorInfoBean.getFund_id(),is_anon);
                }
                break;
            case R.id.ivSwitch:
                if(ivSwitch.isSelected()){
                    ivSwitch.setSelected(false);
                }else{
                    ivSwitch.setSelected(true);
                }
                break;
            case R.id.tvAgreement:

                break;
        }
    }

    @Override
    public void onCommentSuccess(long comment_id, String comment_title) {
        CommentEvent event=new CommentEvent();
        event.setComment_id(comment_id);
        event.setComment_title(comment_title);
        EventBus.getDefault().post(event,ConstantUtil.COMMENT_SUCCESS);

        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
        gotoActivity(SuccessActivity.class,bundle);

        Observable.just(1)
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        finish();
                    }
                });
    }

    @Override
    public void onAppendSuccess() {
        finish();
    }
}
