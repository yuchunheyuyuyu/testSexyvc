package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.ui.main.fragInvestor.di.DaggerFragInvestorComponent;
import com.qtin.sexyvc.ui.main.fragInvestor.di.FragInvestorModule;
import com.qtin.sexyvc.ui.widget.BannerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/4/14.
 */
public class FragInvestor extends MyBaseFragment<FragInvestorPresent> implements FragInvestorContract.View {


    @BindView(R.id.bannerView)
    BannerView bannerView;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragInvestorComponent.builder().appComponent(appComponent).fragInvestorModule(new FragInvestorModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.frag_investor;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        ArrayList<BannerEntity> banners=new ArrayList<>();
        for(int i=0;i<5;i++){
            banners.add(new BannerEntity());
        }
        bannerView.setData(banners);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
