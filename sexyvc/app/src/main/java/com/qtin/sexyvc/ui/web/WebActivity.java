package com.qtin.sexyvc.ui.web;

import android.content.Intent;
import android.webkit.WebView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.PageBean;
import com.qtin.sexyvc.ui.web.di.DaggerWebComponent;
import com.qtin.sexyvc.ui.web.di.WebModule;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class WebActivity extends MyBaseActivity<WebPresent> implements WebContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;
    private String alias_name;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerWebComponent.builder().appComponent(appComponent).webModule(new WebModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.web_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText("");
        alias_name=getIntent().getExtras().getString(ConstantUtil.INTENT_URL);
        mPresenter.queryPage(alias_name);
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

    @Override
    public void querySuccess(PageBean pageBean) {
        webView.loadData(CommonUtil.getHtmlData(pageBean.getPage_content()), "text/html; charset=UTF-8", "utf-8");
        tvTitle.setText(StringUtil.formatString(pageBean.getPage_name()));
    }

    @Override
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
