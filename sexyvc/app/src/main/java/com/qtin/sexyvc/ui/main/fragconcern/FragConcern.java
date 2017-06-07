package com.qtin.sexyvc.ui.main.fragconcern;

import android.content.Intent;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.main.fragconcern.di.ConcernModule;
import com.qtin.sexyvc.ui.main.fragconcern.di.DaggerConcernComponent;

/**
 * Created by ls on 17/4/26.
 */

public class FragConcern extends MyBaseFragment<ConcernPresent> implements ConcernContract.View {
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerConcernComponent.builder().appComponent(appComponent).concernModule(new ConcernModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_concern;
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
