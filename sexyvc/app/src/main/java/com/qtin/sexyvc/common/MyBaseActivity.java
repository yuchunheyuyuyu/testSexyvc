package com.qtin.sexyvc.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;
import com.qtin.sexyvc.mvp.test.progress.LoadingDialog;
import com.umeng.message.PushAgent;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MyBaseActivity<P extends Presenter> extends BaseActivity<P> {
    protected CustomApplication customApplication;
    private LoadingDialog loadingDialog;
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
    }
}
