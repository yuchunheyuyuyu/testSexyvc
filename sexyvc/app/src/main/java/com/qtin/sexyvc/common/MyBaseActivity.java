package com.qtin.sexyvc.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.mvp.test.progress.LoadingDialog;
import com.qtin.sexyvc.ui.bean.DialogType;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.NameLengthFilter;
import com.umeng.message.PushAgent;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MyBaseActivity<P extends Presenter> extends BaseActivity<P> {
    protected CustomApplication customApplication;
    private LoadingDialog loadingDialog;
    private Dialog twoButtondialog;
    private Dialog inputDialog;
    private Dialog selectPhotoDialog;
    private Dialog oneButtonDialog;

    private Dialog shareDialog;

    @Override
    protected void ComponentInject() {
        customApplication = (CustomApplication) getApplication();
        setupActivityComponent(customApplication.getAppComponent());
    }

    protected void showDialog(String msg){
        loadingDialog=new LoadingDialog(this,msg);
        loadingDialog.show();
    }

    protected void dialogDismiss(){
        if(loadingDialog!=null){
            loadingDialog.close();
        }
    }

    public static interface OnClickErrorListener{
        void onClick();
    }

    protected void showErrorView(final OnClickErrorListener onClickErrorListener){
        findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.emptyLayout).setVisibility(View.GONE);
        findViewById(R.id.recyclerView).setVisibility(View.GONE);
        findViewById(R.id.ivErrorStatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickErrorListener.onClick();
            }
        });
    }

    protected void showEmptyView(){
        findViewById(R.id.errorLayout).setVisibility(View.GONE);
        findViewById(R.id.emptyLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.recyclerView).setVisibility(View.GONE);
    }

    protected void showNormalContentView(){
        findViewById(R.id.errorLayout).setVisibility(View.GONE);
        findViewById(R.id.emptyLayout).setVisibility(View.GONE);
        findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.customApplication = null;
        this.loadingDialog=null;
    }

    public void gotoActivity(Class<? extends Activity> activityClass){
        Intent intent=new Intent(this,activityClass);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    public void gotoActivityReverse(Class<? extends Activity> activityClass){
        Intent intent=new Intent(this,activityClass);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_exit_to_left,R.anim.activity_exit_to_right);
    }

    public void gotoActivityFade(Class<? extends Activity> activityClass,Bundle bundle){
        Intent intent=new Intent(this,activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }


    public void gotoActivityFade(Class<? extends Activity> activityClass){
        Intent intent=new Intent(this,activityClass);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    protected void gotoActivityFadeForResult(Class<? extends Activity> activityClass, Bundle bundle,int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
    protected void gotoActivityFadeForResult(Class<? extends Activity> activityClass,int requestCode) {
        Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }


    public void gotoActivity(Class<? extends Activity> activityClass,Bundle bundle){
        Intent intent=new Intent(this,activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    protected void gotoActivityForResult(Class<? extends Activity> activityClass, Bundle bundle,int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }
    protected void gotoActivityForResult(Class<? extends Activity> activityClass, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        if(isNeedFinishAnim()){
            overridePendingTransition(R.anim.activity_enter_from_left,R.anim.activity_exit_to_right);
        }
    }

    protected boolean isNeedFinishAnim(){
        return true;
    }

    /**
     * 两个按钮的dialog
     */
    protected void showTwoButtonDialog(String title, String stringLeft, String stringRight, final TwoButtonListerner listerner) {

        View view = View.inflate(this, R.layout.two_button_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        TextView btnLeft= (TextView) view.findViewById(R.id.btnLeft);
        TextView btnRight= (TextView) view.findViewById(R.id.btnRight);

        tvDialogTitle.setText(title);
        btnLeft.setText(stringLeft);
        btnRight.setText(stringRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.leftClick();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.rightClick();
            }
        });

        AutoUtils.autoSize(view);
        twoButtondialog = new Dialog(this);
        twoButtondialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        twoButtondialog.setContentView(view);
        Window regionWindow = twoButtondialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        twoButtondialog.setCanceledOnTouchOutside(true);
        twoButtondialog.show();
    }

    /**
     * 分享的dialog
     */
    public static interface onShareClick{
        void onClickShare(int platForm);
    }

    protected void showShareDialog(final onShareClick listerner) {

        View view = View.inflate(this, R.layout.share_dialog, null);
        view.findViewById(R.id.wechatContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onClickShare(ConstantUtil.SHARE_WECHAT);
            }
        });
        view.findViewById(R.id.wxCircleContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onClickShare(ConstantUtil.SHARE_WX_CIRCLE);
            }
        });
        view.findViewById(R.id.qqContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onClickShare(ConstantUtil.SHARE_QQ);
            }
        });
        view.findViewById(R.id.sinaContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onClickShare(ConstantUtil.SHARE_SINA);
            }
        });
        view.findViewById(R.id.cancleShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissShareDialog();
            }
        });

        shareDialog = new Dialog(this);
        shareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        shareDialog.setContentView(view);
        Window regionWindow = shareDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation
        );
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.show();
    }

    protected void dismissShareDialog(){
        if(shareDialog!=null&&shareDialog.isShowing()){
            shareDialog.dismiss();
        }
    }

    protected void dismissTwoButtonDialog(){
        if(twoButtondialog!=null&&twoButtondialog.isShowing()){
            twoButtondialog.dismiss();
        }
    }

    public static interface TwoButtonListerner {
        void leftClick();
        void rightClick();
    }

    public static interface OneButtonListerner {
        void onOptionSelected();
        void onCancle();
    }

    protected void showBottomDialog(String secondColor,String first,String second,String cancle,final SelecteListerner listerner) {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        view.findViewById(R.id.tvHint).setVisibility(View.GONE);
        view.findViewById(R.id.lineHint).setVisibility(View.GONE);

        TextView btn_report = (TextView) view.findViewById(R.id.btn_report);
        TextView btn_error = (TextView) view.findViewById(R.id.btn_error);
        TextView cancleSelected = (TextView) view.findViewById(R.id.cancleSelected);

        btn_report.setText(StringUtil.formatString(first));
        btn_error.setText(StringUtil.formatString(second));
        try{
            if(!StringUtil.isBlank(secondColor)){
                btn_error.setTextColor(Color.parseColor(secondColor));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        cancleSelected.setText(StringUtil.formatString(cancle));

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onFirstClick();
            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onSecondClick();
            }
        });
        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onCancle();
            }
        });
        selectPhotoDialog = new Dialog(this);
        selectPhotoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPhotoDialog.setContentView(view);
        Window regionWindow = selectPhotoDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectPhotoDialog.setCanceledOnTouchOutside(true);
        selectPhotoDialog.show();
    }

    protected void showInputDialog(String title, String warn,String stringLeft, String stringRight, final InputListerner listerner) {

        View view = View.inflate(this, R.layout.input_dialog, null);
        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        TextView tvWarn=(TextView) view.findViewById(R.id.tvWarn);
        Button btnLeft= (Button) view.findViewById(R.id.btnLeft);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);
        final EditText etContent= (EditText) view.findViewById(R.id.etContent);

        //etContent.setHint(getString(R.string.char_count_limit));
        InputFilter[] filters = { new NameLengthFilter(16) };
        etContent.setFilters(filters);

        tvTitle.setText(title);
        btnLeft.setText(warn);
        btnLeft.setText(stringLeft);
        btnRight.setText(stringRight);


        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.cancle();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isBlank(etContent.getText().toString())){
                    UiUtils.showToastShort(MyBaseActivity.this,"组名不能为空");
                    return;
                }
                listerner.onComfirm(etContent.getText().toString());
            }
        });

        AutoUtils.autoSize(view);
        inputDialog = new Dialog(this);
        inputDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputDialog.setContentView(view);
        Window regionWindow = inputDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inputDialog.setCanceledOnTouchOutside(true);
        inputDialog.show();
    }

    protected void showBottomOneDialog(String optionStr,final OneButtonListerner listerner) {
        View view = View.inflate(this, R.layout.bottom_one_button_dialog, null);
        TextView btnOption= (TextView) view.findViewById(R.id.btnOption);
        TextView cancleSelected= (TextView) view.findViewById(R.id.cancleSelected);
        btnOption.setText(optionStr);

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onOptionSelected();
            }
        });

        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onCancle();
            }
        });

        AutoUtils.autoSize(view);
        oneButtonDialog = new Dialog(this);
        oneButtonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        oneButtonDialog.setContentView(view);
        Window regionWindow = oneButtonDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        oneButtonDialog.setCanceledOnTouchOutside(true);
        oneButtonDialog.show();
    }

    private Dialog comfirmDialog;
    protected void showComfirmDialog(String title,String content,String button,final ComfirmListerner listerner) {

        View view = View.inflate(this, R.layout.one_button_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        TextView tvContent= (TextView) view.findViewById(R.id.tvContent);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);

        if(StringUtil.isBlank(content)){
            tvContent.setVisibility(View.GONE);
        }else{
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }


        tvDialogTitle.setText(title);
        btnRight.setText(button);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onComfirm();
            }
        });

        AutoUtils.autoSize(view);
        comfirmDialog = new Dialog(this);
        comfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        comfirmDialog.setContentView(view);
        Window regionWindow = comfirmDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        comfirmDialog.setCanceledOnTouchOutside(true);
        comfirmDialog.show();
    }

    protected void dismissComfirmDialog(){
        if(comfirmDialog!=null&&comfirmDialog.isShowing()){
            comfirmDialog.dismiss();
        }
    }
    public static interface ComfirmListerner {
        void onComfirm();
    }


    protected void dismissInputDialog(){
        if(inputDialog!=null&&inputDialog.isShowing()){
            inputDialog.dismiss();
        }
    }

    protected void dismissBottomDialog(){
        if(selectPhotoDialog!=null&&selectPhotoDialog.isShowing()){
            selectPhotoDialog.dismiss();
        }
    }

    protected void dismissBottomOneButtonDialog(){
        if(oneButtonDialog!=null&&oneButtonDialog.isShowing()){
            oneButtonDialog.dismiss();
        }
    }
    public static interface SelecteListerner {
        void onFirstClick();
        void onSecondClick();
        void onCancle();
    }
    public static interface InputListerner {
        void onComfirm(String content);
        void cancle();
    }

    protected void dismissHintDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }

    private Dialog dialog;
    protected void showHintDialog(final String phone,final DialogType dialogType,final ComfirmListerner comfirmListerner){

        int neverShow=-1;
        if(dialogType==DialogType.TYPE_IDENTITY){
            neverShow= DataHelper.getIntergerSF(this,phone+"never_show_identity");
        }else if(dialogType==DialogType.TYPE_PROJECT){
            neverShow= DataHelper.getIntergerSF(this,phone+"never_show_project");
        }else if(dialogType==DialogType.TYPE_COMMENT){
            neverShow= DataHelper.getIntergerSF(this,phone+"never_show_comment");
        }

        if(neverShow==1){
            return;
        }

        View view = View.inflate(this, R.layout.app_hint_dialog, null);
        view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissHintDialog();
            }
        });
        view.findViewById(R.id.tvNeverShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissHintDialog();
                if(dialogType==DialogType.TYPE_IDENTITY){
                    DataHelper.SetIntergerSF(MyBaseActivity.this,phone+"never_show_identity",1);
                }else if(dialogType==DialogType.TYPE_PROJECT){
                    DataHelper.SetIntergerSF(MyBaseActivity.this,phone+"never_show_project",1);
                }else if(dialogType==DialogType.TYPE_COMMENT){
                    DataHelper.SetIntergerSF(MyBaseActivity.this,phone+"never_show_comment",1);
                }
            }
        });

        ImageView ivContent= (ImageView) view.findViewById(R.id.ivContent);
        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        TextView tvWarn= (TextView) view.findViewById(R.id.tvWarn);
        TextView tvAction= (TextView) view.findViewById(R.id.tvAction);

        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmListerner.onComfirm();
                dismissHintDialog();
            }
        });

        if(dialogType==DialogType.TYPE_IDENTITY){
            ivContent.setImageResource(R.drawable.img_approve_fw);
            tvTitle.setText(getString(R.string.dialog_identity_title));
            tvWarn.setText(getString(R.string.dialog_identity_warn));
            tvAction.setText(getString(R.string.dialog_identity_action));
        }else if(dialogType==DialogType.TYPE_PROJECT){
            ivContent.setImageResource(R.drawable.img_project_fw);
            tvTitle.setText(getString(R.string.dialog_project_title));
            tvWarn.setText(getString(R.string.dialog_project_warn));
            tvAction.setText(getString(R.string.dialog_project_action));
        }else if(dialogType==DialogType.TYPE_COMMENT){
            ivContent.setImageResource(R.drawable.img_comment_fw);
            tvTitle.setText(getString(R.string.dialog_comment_title));
            tvWarn.setText(getString(R.string.dialog_comment_warn));
            tvAction.setText(getString(R.string.dialog_comment_action));
        }

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window regionWindow = dialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        dialog.show();
    }
}
