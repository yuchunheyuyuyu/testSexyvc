package com.qtin.sexyvc.ui.main.fraghome;

import android.content.Intent;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.main.fraghome.di.DaggerFragHomeComponent;
import com.qtin.sexyvc.ui.main.fraghome.di.FragHomeModule;

/**
 * Created by ls on 17/4/14.
 */

public class FragHome extends MyBaseFragment<FragHomePresent> implements FragHomeContract.View {

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragHomeComponent.builder().appComponent(appComponent).fragHomeModule(new FragHomeModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_home;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showLoading() {

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
