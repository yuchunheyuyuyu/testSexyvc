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
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.user.position.di.DaggerPositionComponent;
import com.qtin.sexyvc.ui.user.position.di.PositionModule;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    @BindView(R.id.tvPosition)
    TextView tvPosition;

    private String u_company;
    private String u_title;
    private int u_auth_type;

    private Dialog dialog;
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

        u_company=getIntent().getExtras().getString("u_company");
        u_title=getIntent().getExtras().getString("u_title");
        u_auth_type=getIntent().getExtras().getInt("u_auth_type");

        tvTitle.setText(getResources().getString(R.string.title_position));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.save));

        tvAuthType.setText(getAuthType(u_auth_type));

        setPosition();
    }

    private void setPosition(){
        String position="";
        if(StringUtil.isBlank(u_title)&&StringUtil.isBlank(u_company)){
            position=getResources().getString(R.string.not_fill);
        }else{
            position=""+u_company+" "+u_title;
        }
        tvPosition.setText(position);
    }

    private String getAuthType(int type){
        String typeStr="";
        switch (type){
            case ConstantUtil.AUTH_TYPE_UNKNOWN:
                break;
            case ConstantUtil.AUTH_TYPE_FOUNDER:
                typeStr=getResources().getString(R.string.auth_type_founder);
                break;
            case ConstantUtil.AUTH_TYPE_INVESTOR:
                typeStr=getResources().getString(R.string.auth_type_investor);
                break;
            case ConstantUtil.AUTH_TYPE_FA:
                typeStr=getResources().getString(R.string.auth_type_fa);
                break;
            case ConstantUtil.AUTH_TYPE_OTHER:
                typeStr=getResources().getString(R.string.auth_type_other);
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
        UiUtils.showToastShort(this,message);
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
        switch (requestCode){
            case ModifyActivity.MODIFY_POSITION:
                if(data!=null){
                    u_company=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    u_title=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2));
                    setPosition();
                }
                break;
        }
    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.authTypeContainer, R.id.positionContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(u_auth_type==0){
                    showMessage("请选择身份");
                    return;
                }
                mPresenter.editPosition(u_auth_type,u_company,u_title);
                break;
            case R.id.authTypeContainer:
                chooseTypeDialog();
                break;
            case R.id.positionContainer:

                if(u_auth_type==0){
                    showMessage("请选择身份");
                }else{
                    Bundle position=new Bundle();
                    position.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_POSITION);
                    position.putString(ModifyActivity.MODIFY_INTENT_VALUE1,u_company);
                    position.putString(ModifyActivity.MODIFY_INTENT_VALUE2,u_title);
                    gotoActivityForResult(ModifyActivity.class,position, ModifyActivity.MODIFY_POSITION);
                }
                break;
        }
    }

    @Override
    public void editSuccess() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Intent intent = new Intent();
                        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE1, u_auth_type);
                        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE2, u_company);
                        intent.putExtra(ModifyActivity.MODIFY_INTENT_VALUE3, u_title);

                        setResult(0, intent);
                        finish();
                    }
                });
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
                u_auth_type=ConstantUtil.AUTH_TYPE_FOUNDER;
                tvAuthType.setText(getAuthType(u_auth_type));
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tvTypeInvestor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_auth_type=ConstantUtil.AUTH_TYPE_INVESTOR;
                tvAuthType.setText(getAuthType(u_auth_type));
                dialog.dismiss();
            }
        });;
        view.findViewById(R.id.tvTypeFA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_auth_type=ConstantUtil.AUTH_TYPE_FA;
                tvAuthType.setText(getAuthType(u_auth_type));
                dialog.dismiss();
            }
        });;
        view.findViewById(R.id.tvTypeOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_auth_type=ConstantUtil.AUTH_TYPE_OTHER;
                tvAuthType.setText(getAuthType(u_auth_type));
                dialog.dismiss();
            }
        });;
        view.findViewById(R.id.cancleSelected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });;

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
}
