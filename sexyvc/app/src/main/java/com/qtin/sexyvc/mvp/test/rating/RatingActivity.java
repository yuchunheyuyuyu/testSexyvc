package com.qtin.sexyvc.mvp.test.rating;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;

import butterknife.BindView;

/**
 * Created by ls on 17/2/27.
 */

public class RatingActivity extends MyBaseActivity {

    @BindView(R.id.ratingBar1)
    SimpleRatingBar ratingBar1;
    @BindView(R.id.ratingBar2)
    SimpleRatingBar ratingBar2;
    @BindView(R.id.ratingBar3)
    SimpleRatingBar ratingBar3;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        ratingBar1.setIndicator(true);
        ratingBar1.setRating(2.5f);

        ratingBar3.getAnimationBuilder()
                .setRepeatCount(0)
                .setRepeatMode(ValueAnimator.RESTART)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .setRatingTarget(4)
                .setDuration(2500)
                .start();
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_rating_activity;
    }

    @Override
    protected void initData() {

    }
}
