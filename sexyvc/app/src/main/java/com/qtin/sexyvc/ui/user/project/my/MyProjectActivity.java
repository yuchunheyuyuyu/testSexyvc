package com.qtin.sexyvc.ui.user.project.my;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.widget.autolayout.AutoCardView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.ui.user.project.my.di.DaggerMyProjectComponent;
import com.qtin.sexyvc.ui.user.project.my.di.MyProjectModule;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class MyProjectActivity extends MyBaseActivity<MyProjectPresent> implements MyProjectContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDomainFinance)
    TextView tvDomainFinance;
    @BindView(R.id.tvIntroduce)
    TextView tvIntroduce;
    @BindView(R.id.cardViewProject)
    AutoCardView cardViewProject;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMyProjectComponent.builder().appComponent(appComponent).myProjectModule(new MyProjectModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.my_project_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_my_project));
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

    @OnClick({R.id.ivLeft, R.id.cardViewProject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.cardViewProject:
                gotoActivity(AddProjectActivity.class);
                break;
        }
    }
}
