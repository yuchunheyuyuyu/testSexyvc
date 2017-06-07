package com.qtin.sexyvc.mvp.test.progress;

import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/3/7.
 */

public class ProgressActivity extends MyBaseActivity {
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar4;
    @BindView(R.id.progressBar5)
    ProgressBar progressBar5;
    @BindView(R.id.progressBar6)
    ProgressBar progressBar6;
    @BindView(R.id.progressBar7)
    ProgressBar progressBar7;
    @BindView(R.id.progressBar8)
    ProgressBar progressBar8;
    @BindView(R.id.progressBar9)
    ProgressBar progressBar9;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_progress_activity;
    }

    @Override
    protected void initData() {

        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar1.setIndeterminateDrawable(doubleBounce);

        RotatingPlane rotatingPlane = new RotatingPlane();
        progressBar2.setIndeterminateDrawable(rotatingPlane);

        Wave wave = new Wave();
        progressBar3.setIndeterminateDrawable(wave);

        WanderingCubes wanderingCubes = new WanderingCubes();
        progressBar4.setIndeterminateDrawable(wanderingCubes);

        Pulse pulse = new Pulse();
        progressBar5.setIndeterminateDrawable(pulse);

        ChasingDots chasingDots = new ChasingDots();
        progressBar6.setIndeterminateDrawable(chasingDots);

        ThreeBounce threeBounce = new ThreeBounce();
        progressBar7.setIndeterminateDrawable(threeBounce);

        Circle circle = new Circle();
        progressBar8.setIndeterminateDrawable(circle);

        FadingCircle fadingCircle = new FadingCircle();
        progressBar9.setIndeterminateDrawable(fadingCircle);

    }


    @OnClick(R.id.btnShowLoading)
    public void onClick() {

        showDialog("加载中...");

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        dialogDismiss();
                    }
                });
    }
}
