package com.qtin.sexyvc.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;
import com.qtin.sexyvc.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MyBaseFragment<P extends Presenter> extends BaseFragment<P> {
    protected CustomApplication customApplication;
    @Override
    protected void ComponentInject() {
        customApplication = (CustomApplication)mActivity.getApplication();
        setupFragmentComponent(customApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = CustomApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        this.customApplication =null;
    }

    public void gotoActivity(Class<? extends Activity> activityClass){
        Intent intent=new Intent(mActivity,activityClass);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    public void gotoActivity(Class<? extends Activity> activityClass,Bundle bundle){
        Intent intent=new Intent(mActivity,activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }
}
