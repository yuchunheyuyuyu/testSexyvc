package com.qtin.sexyvc.ui.road.success;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.rate.RateActivity;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.success.di.DaggerRoadSuccessComponent;
import com.qtin.sexyvc.ui.road.success.di.RoadSuccessModule;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.utils.ConstantUtil;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class SuccessActivity extends MyBaseActivity<RoadSuccessPresent> implements RoadSuccessContract.View {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvVertify)
    TextView tvVertify;
    @BindView(R.id.tvEditComment)
    TextView tvEditComment;
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
        DaggerRoadSuccessComponent.builder().appComponent(appComponent).roadSuccessModule(new RoadSuccessModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.road_success_activity;
    }

    @Override
    protected void initData() {
        investorInfoBean = getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
        ivLeft.setVisibility(View.GONE);
        tvTitle.setText(getResources().getString(R.string.title_road_success));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.complete));

        tvName.setText(String.format(getResources().getString(R.string.format_road_success),""+investorInfoBean.getInvestor_name()));

        UserInfoEntity userInfoEntity=mPresenter.getUserInfo();
        if(userInfoEntity!=null){
            if(userInfoEntity.getU_auth_state()== ConstantUtil.AUTH_STATE_UNPASS){
                tvVertify.setVisibility(View.VISIBLE);
            }else{
                tvVertify.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {

        if(investorInfoBean!=null){
            if(investorInfoBean.getHas_comment()==0){
                tvEditComment.setVisibility(View.VISIBLE);
            }else{
                tvEditComment.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 进入评分
     */
    private void gotoScore(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
        gotoActivity(RateActivity.class,bundle);
    }

    /**
     * 进入评论或者追评
     */
    private void gotoComment(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
        gotoActivity(ReviewActivity.class,bundle);
    }


    @OnClick({R.id.tvRight, R.id.tvVertify, R.id.tvEditComment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                finish();
                break;
            case R.id.tvVertify:
                Bundle bundle=new Bundle();
                bundle.putParcelable(UserInfoActivity.INTENT_USER,mPresenter.getUserInfo());
                bundle.putBoolean("isNeedGotoMain",false);
                gotoActivity(UserInfoActivity.class,bundle);
                break;
            case R.id.tvEditComment:
                if(investorInfoBean.getHas_score()==0){
                    gotoScore();
                }else{
                    gotoComment();
                }
                break;
        }
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
}
