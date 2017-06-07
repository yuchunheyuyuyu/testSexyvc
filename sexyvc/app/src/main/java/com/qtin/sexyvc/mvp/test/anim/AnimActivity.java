package com.qtin.sexyvc.mvp.test.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jess.arms.base.BaseActivity;
import com.qtin.sexyvc.R;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/3/27.
 */

public class AnimActivity extends BaseActivity {
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    View button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button6)
    Button button6;

    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    private LayoutTransition mTransitioner;
    private int i;

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected void ComponentInject() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_anim_activity;
    }

    @Override
    protected void initData() {
        mTransitioner = new LayoutTransition();
        //layoutTransition.setAnimator(LayoutTransition.APPEARING, ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f));
        llContainer.setLayoutTransition(mTransitioner);
        setupCustomAnimations();
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button6, R.id.button7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(button1, "alpha", 1f, 0f, 1f);
                animator1.setDuration(3000);
                animator1.start();
                break;
            case R.id.button2:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(button2, "rotation", 0f, 360f);
                animator2.setDuration(1500);
                animator2.setInterpolator(new AccelerateDecelerateInterpolator());
                animator2.start();
                break;
            case R.id.button3:
                float curTranslationX = button3.getTranslationX();
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(button3, "translationX", curTranslationX, -500, curTranslationX);
                animator3.setDuration(1500);
                animator3.setInterpolator(new BounceInterpolator());
                animator3.start();
                break;
            case R.id.button4:
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(button4, "scaleY", 1f, 3f, 1f);
                animator4.setDuration(1500);
                animator4.start();
                break;
            case R.id.button6:
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(button6, "translationX", -500f, 0f);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(button6, "rotationX", 0f, 1080f);
                ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(button6, "alpha", 1f, 0f, 1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(rotate).after(moveIn).with(fadeInOut);
                animatorSet.setDuration(3000);
                animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
                break;
            case R.id.button7:
                addView();
                break;
        }
    }

    private void addView() {
        final Button button = new Button(this);
        llContainer.addView(button);
        i++;
        button.setText("第" + i + "个按钮");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContainer.removeView(button);
            }
        });
    }

    // 生成自定义动画
    private void setupCustomAnimations() {
        // 动画：CHANGE_APPEARING
        // Changing while Adding
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);

        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX,
                pvhScaleY).setDuration(
                mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));

        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
        changeIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setScaleX(1f);
                view.setScaleY(1f);
            }
        });

        // 动画：CHANGE_DISAPPEARING
        // Changing while Removing
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator
                .ofPropertyValuesHolder(this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation)
                .setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,changeOut);
        changeOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotation(0f);
            }
        });

        // 动画：APPEARING
        // Adding
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "scaleY",1f, 3f,1f).setDuration(
                mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationY(0f);
            }
        });

        // 动画：DISAPPEARING
        // Removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f,
                90f).setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationX(0f);
            }
        });
    }
}
