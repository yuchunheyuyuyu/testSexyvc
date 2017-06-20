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
import android.widget.TextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_enter_from_left,R.anim.activity_exit_to_right);
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
}
