package com.qtin.sexyvc.ui.user.position;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.user.photo.PhotoActivity;
import com.qtin.sexyvc.ui.user.position.di.DaggerPositionComponent;
import com.qtin.sexyvc.ui.user.position.di.PositionModule;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class PositionActivity extends MyBaseActivity<PositionPresent> implements PositionContract.View {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.tvAuthType)
    TextView tvAuthType;
    @BindView(R.id.ivIdentity)
    ImageView ivIdentity;
    @BindView(R.id.tvInstitution)
    TextView tvInstitution;
    @BindView(R.id.tvDuties)
    TextView tvDuties;

    private UserInfoEntity userInfo;

    private Dialog dialog;
    public static final int INDENTITY_VERTIFY_CODE = 0x01a;

    private Dialog warnDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPositionComponent.builder().appComponent(appComponent).positionModule(new PositionModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.position_activity;
    }

    @Override
    protected void initData() {

        userInfo = getIntent().getExtras().getParcelable(UserInfoActivity.INTENT_USER);

        tvTitle.setText(getResources().getString(R.string.title_position));
        tvRight.setVisibility(View.GONE);
        tvRight.setText(getResources().getString(R.string.save));

        tvAuthType.setText(getAuthType(userInfo.getU_auth_type()));

        setPosition();
        setAuthState();
    }

    //设置认证状态
    private void setAuthState() {
        if (userInfo.getU_auth_state() == ConstantUtil.AUTH_STATE_PASS) {
            if (userInfo.getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                ivIdentity.setImageResource(R.drawable.tag_approve_fc);
            } else if (userInfo.getU_auth_type() == ConstantUtil.AUTH_TYPE_INVESTOR) {
                ivIdentity.setImageResource(R.drawable.tag_approve_vc);
            } else if (userInfo.getU_auth_type() == ConstantUtil.AUTH_TYPE_FA) {
                ivIdentity.setImageResource(R.drawable.tag_approve_fa);
            } else {
                //暂时缺"其他"图片
                ivIdentity.setImageResource(R.drawable.tag_approve_fc);
            }
        } else if (userInfo.getU_auth_state() == ConstantUtil.AUTH_STATE_COMMITING) {
            ivIdentity.setImageResource(R.drawable.approve_reviewing);
        } else {
            ivIdentity.setImageResource(R.drawable.approve_not_done_red);
        }
    }

    private void setPosition() {
        if (StringUtil.isBlank(userInfo.getU_company())) {
            tvInstitution.setText(getString(R.string.not_fill));
        } else {
            tvInstitution.setText(userInfo.getU_company());
        }

        if (StringUtil.isBlank(userInfo.getU_title())) {
            tvDuties.setText(getString(R.string.not_fill));
        } else {
            tvDuties.setText(userInfo.getU_title());
        }
    }

    private String getAuthType(int type) {
        String typeStr = "";
        switch (type) {
            case ConstantUtil.AUTH_TYPE_UNKNOWN:
                break;
            case ConstantUtil.AUTH_TYPE_FOUNDER:
                typeStr = getString(R.string.auth_type_founder);
                break;
            case ConstantUtil.AUTH_TYPE_INVESTOR:
                typeStr = getString(R.string.auth_type_investor);
                break;
            case ConstantUtil.AUTH_TYPE_FA:
                typeStr = getString(R.string.auth_type_fa);
                break;
            case ConstantUtil.AUTH_TYPE_OTHER:
                typeStr = getString(R.string.auth_type_other);
                break;
        }
        return typeStr;
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
        UiUtils.showToastShort(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case INDENTITY_VERTIFY_CODE:
                int u_auth_state = data.getExtras().getInt("u_auth_state");
                String url = data.getExtras().getString("url");
                userInfo.setU_auth_state(u_auth_state);
                userInfo.setBusiness_card(url);
                setAuthState();
                break;
            case ModifyActivity.MODIFY_TITLE:
                String u_title=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                userInfo.setU_title(u_title);
                setPosition();
                break;
            case ModifyActivity.MODIFY_COMPANY:
                String u_company=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                userInfo.setU_company(u_company);
                setPosition();
                break;
        }
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra(ConstantUtil.INTENT_PARCELABLE,userInfo);
        setResult(0,intent);
        mPresenter.saveUsrInfo(userInfo);
        super.finish();
    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.authTypeContainer, R.id.institutionContainer,
            R.id.dutiesContainer, R.id.identifyContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if (userInfo.getU_auth_type() == 0) {
                    showMessage("请选择身份类型");
                    return;
                }
                if(StringUtil.isBlank(userInfo.getU_company())){
                    showMessage("请填写所在机构");
                    return;
                }

                if(StringUtil.isBlank(userInfo.getU_title())){
                    showMessage("请填写担任职务");
                    return;
                }

                mPresenter.editAuthType(userInfo.getU_auth_type());
                break;
            case R.id.authTypeContainer:
                if(checkAuthStatus()){
                    chooseTypeDialog();
                }

                break;
            case R.id.institutionContainer:
                if(checkAuthStatus()){
                    Bundle institution = new Bundle();
                    institution.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_COMPANY);
                    institution.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_company());
                    gotoActivityForResult(ModifyActivity.class, institution, ModifyActivity.MODIFY_COMPANY);
                }

                break;
            case R.id.dutiesContainer:
                if(checkAuthStatus()){
                    Bundle duties = new Bundle();
                    duties.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_TITLE);
                    duties.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_title());
                    gotoActivityForResult(ModifyActivity.class, duties, ModifyActivity.MODIFY_TITLE);
                }
                break;
            case R.id.identifyContainer:
                gotoIdentify();
                break;
        }
    }

    private void gotoIdentify(){
        Bundle identify = new Bundle();
        identify.putString(ConstantUtil.INTENT_URL, userInfo.getBusiness_card());
        identify.putInt("u_auth_state", userInfo.getU_auth_state());
        gotoActivityForResult(PhotoActivity.class, identify, INDENTITY_VERTIFY_CODE);
    }

    private boolean checkAuthStatus(){
        if(userInfo.getU_auth_state()==ConstantUtil.AUTH_STATE_UNPASS){
            return true;
        }else{
            showCancleDialog();
            return false;
        }
    }

    @Override
    public void editAuthTypeSuccess(int u_auth_type) {
        userInfo.setU_auth_type(u_auth_type);
        tvAuthType.setText(getAuthType(userInfo.getU_auth_type()));

        /**  Observable.just(1)
         .observeOn(AndroidSchedulers.mainThread())
         .delay(10, TimeUnit.MILLISECONDS)
         .subscribe(new Action1<Integer>() {
        @Override public void call(Integer integer) {
        Intent intent = new Intent();
        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_auth_type());
        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE2, userInfo.getU_company());
        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE3, userInfo.getU_title());

        setResult(0, intent);
        finish();
        }
        });*/
    }

    @Override
    public void cancleAuthSuccess() {
        userInfo.setU_auth_state(ConstantUtil.AUTH_STATE_UNPASS);
        userInfo.setBusiness_card("");
        setAuthState();
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
     * 性别
     */
    private void chooseTypeDialog() {

        View view = View.inflate(this, R.layout.choose_auth_type_dialog, null);
        AutoUtils.autoSize(view);

        view.findViewById(R.id.tvTypeFounder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.editAuthType(ConstantUtil.AUTH_TYPE_FOUNDER);
            }
        });
        view.findViewById(R.id.tvTypeInvestor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.editAuthType(ConstantUtil.AUTH_TYPE_INVESTOR);
            }
        });
        ;
        view.findViewById(R.id.tvTypeFA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.editAuthType(ConstantUtil.AUTH_TYPE_FA);
            }
        });
        ;
        view.findViewById(R.id.tvTypeOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ;
        view.findViewById(R.id.cancleSelected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window regionWindow = dialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * 取消认证diglog
     */
    protected void showCancleDialog() {

        View view = View.inflate(this, R.layout.warn_dialog, null);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        TextView tvCancle= (TextView) view.findViewById(R.id.tvCancle);

        tvDialogTitle.setText(getString(R.string.can_not_modify));
        tvContent.setText(getString(R.string.can_not_modify_reason));
        tvCancle.setText(getString(R.string.i_known));

        view.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warnDialog != null && warnDialog.isShowing()) {
                    warnDialog.dismiss();
                }
            }
        });

        view.findViewById(R.id.tvComfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warnDialog != null && warnDialog.isShowing()) {
                    warnDialog.dismiss();
                }
                //mPresenter.cancelAuth();
                gotoIdentify();
            }
        });
        warnDialog = new Dialog(this);
        warnDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        warnDialog.setContentView(view);
        Window regionWindow = warnDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warnDialog.setCanceledOnTouchOutside(true);
        warnDialog.show();
    }
}
