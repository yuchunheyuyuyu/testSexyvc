package com.qtin.sexyvc.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.mvp.test.progress.LoadingDialog;
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
     * 是否清除缓存框
     */
    protected void showTwoButtonDialog(String title, String stringLeft, String stringRight, final TwoButtonListerner listerner) {

        View view = View.inflate(this, R.layout.two_button_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        Button btnLeft= (Button) view.findViewById(R.id.btnLeft);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);

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
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        oneButtonDialog.setCanceledOnTouchOutside(true);
        oneButtonDialog.show();
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
    public static interface SelecteListerner {
        void onFirstClick();
        void onSecondClick();
        void onCancle();
    }
    public static interface InputListerner {
        void onComfirm(String content);
        void cancle();
    }
}
