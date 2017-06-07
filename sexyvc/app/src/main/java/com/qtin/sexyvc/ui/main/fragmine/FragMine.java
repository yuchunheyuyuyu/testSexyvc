package com.qtin.sexyvc.ui.main.fragmine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.main.fragmine.di.DaggerFragMineComponent;
import com.qtin.sexyvc.ui.main.fragmine.di.FragmineModule;

import butterknife.BindView;

/**
 * Created by ls on 17/4/14.
 */
public class FragMine extends MyBaseFragment<FragMinePresent> implements FragMineContract.View{

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

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
        tvTitle.setText(getResources().getString(R.string.title_my_center));
        ivLeft.setVisibility(View.GONE);
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
