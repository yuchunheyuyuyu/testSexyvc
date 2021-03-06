package com.qtin.sexyvc.ui.main.fragmine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.InfluencyBean;
import com.qtin.sexyvc.ui.bean.UnReadBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.main.fragmine.di.DaggerFragMineComponent;
import com.qtin.sexyvc.ui.main.fragmine.di.FragmineModule;
import com.qtin.sexyvc.ui.user.influency.InfluencyActivity;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.user.message.MessageActivity;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.ui.user.project.my.MyProjectActivity;
import com.qtin.sexyvc.ui.user.sent.SentActivity;
import com.qtin.sexyvc.ui.user.setting.SettingActivity;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/4/14.
 */
public class FragMine extends MyBaseFragment<FragMinePresent> implements FragMineContract.View {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivIdentity)
    ImageView ivIdentity;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    @BindView(R.id.lineIntroduce)
    View lineIntroduce;
    @BindView(R.id.tvIntroduce)
    TextView tvIntroduce;
    @BindView(R.id.myProjectContainer)
    LinearLayout myProjectContainer;
    @BindView(R.id.lineProject)
    View lineProject;
    @BindView(R.id.ivMessage)
    ImageView ivMessage;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.tvProjectStatus)
    TextView tvProjectStatus;
    @BindView(R.id.tvInfluencyNum)
    TextView tvInfluencyNum;
    @BindView(R.id.influencyContainer)
    LinearLayout influencyContainer;
    @BindView(R.id.lineInfluency)
    View lineInfluency;
    @BindView(R.id.messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.mySentContainer)
    LinearLayout mySentContainer;

    private UserInfoEntity userInfo;

    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragMineComponent.builder().appComponent(appComponent).fragmineModule(new FragmineModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_mine;
    }

    @Override
    protected void init() {
        mImageLoader = customApplication.getAppComponent().imageLoader();

        tvTitle.setText(getResources().getString(R.string.title_my_center));
        ivLeft.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
        mPresenter.queryUnRead();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick({R.id.myProjectContainer, R.id.messageContainer, R.id.ivRight, R.id.cardViewInfo,
            R.id.mySentContainer,R.id.influencyContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.influencyContainer:
                if (userInfo != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE, userInfo);
                    gotoActivity(InfluencyActivity.class,bundle);
                }
                break;
            case R.id.mySentContainer:
                gotoActivity(SentActivity.class);
                break;
            case R.id.cardViewInfo:
                if (userInfo != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(UserInfoActivity.INTENT_USER, userInfo);
                    bundle.putBoolean("isNeedGotoMain", false);
                    gotoActivity(UserInfoActivity.class, bundle);
                }
                break;
            case R.id.myProjectContainer:
                if (userInfo != null) {
                    if (userInfo.getHas_project() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT, false);
                        gotoActivity(AddProjectActivity.class, bundle);
                    } else {
                        gotoActivity(MyProjectActivity.class);
                    }
                }
                break;
            case R.id.messageContainer:
                gotoActivity(MessageActivity.class);
                break;
            case R.id.ivRight:
                gotoActivity(SettingActivity.class);
                break;
        }
    }

    @Override
    public void requestSuccess(UserInfoEntity entity) {
        //只有创始人才有影响值
        if(entity.getU_auth_type()==ConstantUtil.AUTH_TYPE_FOUNDER){
            mPresenter.queryInfluency();
        }

        this.userInfo = entity;
        //昵称
        tvName.setText(StringUtil.isBlank(entity.getU_nickname()) ? getResources().getString(R.string.nick_defalut) : entity.getU_nickname());
        //自我介绍
        if (StringUtil.isBlank(entity.getU_signature())) {
            lineIntroduce.setVisibility(View.GONE);
            tvIntroduce.setVisibility(View.GONE);
        } else {
            lineIntroduce.setVisibility(View.VISIBLE);
            tvIntroduce.setVisibility(View.VISIBLE);
            tvIntroduce.setText(entity.getU_signature());
        }
        //公司及职位，身份认证，暂无
        if (entity.getU_auth_state() == ConstantUtil.AUTH_STATE_PASS) {
            if (entity.getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                ivIdentity.setImageResource(R.drawable.tag_approve_fc);
            } else if (entity.getU_auth_type() == ConstantUtil.AUTH_TYPE_INVESTOR) {
                ivIdentity.setImageResource(R.drawable.tag_approve_vc);
            } else if (entity.getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {
                ivIdentity.setImageResource(R.drawable.tag_approve_fa);
            }
        } else if (entity.getU_auth_state() == ConstantUtil.AUTH_STATE_COMMITING) {
            ivIdentity.setImageResource(R.drawable.approve_reviewing);
        } else {
            ivIdentity.setImageResource(R.drawable.approve_not_done);
        }

        if (entity.getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
            myProjectContainer.setVisibility(View.VISIBLE);
            lineProject.setVisibility(View.VISIBLE);

            influencyContainer.setVisibility(View.VISIBLE);
            lineInfluency.setVisibility(View.VISIBLE);

        } else {
            myProjectContainer.setVisibility(View.GONE);
            lineProject.setVisibility(View.GONE);

            influencyContainer.setVisibility(View.GONE);
            lineInfluency.setVisibility(View.GONE);
        }

        if (StringUtil.isBlank(entity.getU_company())) {
            tvCompany.setText(getResources().getString(R.string.compant_defalut));
        } else {
            tvCompany.setText(entity.getU_company());
        }

        if (StringUtil.isBlank(entity.getU_title())) {
            tvPosition.setText(getResources().getString(R.string.position_defalut));
        } else {
            tvPosition.setText(entity.getU_title());
        }

        if (entity.getHas_project() == 0) {
            tvProjectStatus.setText(getString(R.string.not_fill));
        } else {
            tvProjectStatus.setText("");
        }

        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_blank)
                .placeholder(R.drawable.avatar_blank
                )
                .url(CommonUtil.getAbsolutePath(entity.getU_avatar()))
                .transformation(new CropCircleTransformation(mActivity))
                .imageView(ivAvatar)
                .build());

    }

    @Override
    public void requestFail() {

    }

    @Override
    public void queryUnReadSuccess(UnReadBean unReadBean) {
        if (unReadBean.getTotal_no_read() > 0) {
            ivMessage.setSelected(true);
        } else {
            ivMessage.setSelected(false);
        }
    }

    @Override
    public void queryInfluencySuccess(InfluencyBean influencyBean) {
        int influencyScore=0;
        if(influencyBean.getAuth_passed()==1){
            influencyScore+=200;
        }
        if(influencyBean.getHas_project()==1){
            influencyScore+=200;
        }
        influencyScore+=(100*influencyBean.getComment_number()+100*influencyBean.getRoadshow_number());
        influencyScore+=(10*influencyBean.getComment_praised_number());

        tvInfluencyNum.setText(""+influencyScore);
    }
}
