package com.qtin.sexyvc.ui.choose;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.FastBlur;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.anim.MyAnimations;
import com.qtin.sexyvc.utils.ConstantUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ls on 17/6/29.
 */
public class ChooseActivity extends Activity {

    @BindView(R.id.ivBackGround)
    ImageView ivBackGround;
    @BindView(R.id.ivRoadComment)
    ImageView ivRoadComment;
    @BindView(R.id.ivEditComment)
    ImageView ivEditComment;
    @BindView(R.id.switchContainer)
    RelativeLayout switchContainer;
    @BindView(R.id.ivChoose)
    ImageView ivChoose;
    @BindView(R.id.tvRoadComment)
    TextView tvRoadComment;
    @BindView(R.id.tvEditComment)
    TextView tvEditComment;
    @BindView(R.id.tvChooseTitle)
    TextView tvChooseTitle;

    private static final int SHOW_DELAY_TIME = 100;
    private static final int DISMISS_DELAY_TIME = 400;
    private static final int ANIM_DURATION = 300;
    private static final int MAX_DURATION = 400;

    private Unbinder mUnbinder;

    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_comment_popupwindow);
        mUnbinder = ButterKnife.bind(this);
        ivBackGround.setImageBitmap(FastBlur.getBlurByColor("#b3333333"));
        showAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
    }

    private void textViewShow(TextView tv) {
        Animation animation = MyAnimations.getFadeInAnimation(ANIM_DURATION);
        tv.setVisibility(View.VISIBLE);
        tv.startAnimation(animation);
    }

    public void dismiss() {
        //文字的淡出
        textViewDismiss(tvChooseTitle);
        textViewDismiss(tvEditComment);
        textViewDismiss(tvRoadComment);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.putExtra(ConstantUtil.COMMENT_TYPE_INTENT,type);
                setResult(0,intent);
                finish();
            }
        }, DISMISS_DELAY_TIME);
    }

    private void showAnim(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 图标的动画
                MyAnimations.startAnimationsIn(switchContainer, ANIM_DURATION);
                // 加号的动画
                ivChoose.startAnimation(MyAnimations.getRotateAnimation(0, -180, ANIM_DURATION));
                //文字的淡入
                textViewShow(tvChooseTitle);
                textViewShow(tvEditComment);
                textViewShow(tvRoadComment);
            }
        },SHOW_DELAY_TIME);
    }

    private void textViewDismiss(final TextView textView) {
        Animation animation = MyAnimations.getFadeOutAnimation(ANIM_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(animation);
    }

    @OnClick({R.id.ivRoadComment, R.id.ivEditComment, R.id.ivChoose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRoadComment:
                type= ConstantUtil.COMMENT_TYPE_ROAD;
                ivChoose.startAnimation(MyAnimations.getRotateAnimation(-180, 0, ANIM_DURATION));
                ivRoadComment.startAnimation(MyAnimations.getMaxAnimation(MAX_DURATION));
                ivEditComment.startAnimation(MyAnimations.getMiniAnimation(ANIM_DURATION));
                dismiss();
                break;
            case R.id.ivEditComment:
                type= ConstantUtil.COMMENT_TYPE_EDIT;
                ivChoose.startAnimation(MyAnimations.getRotateAnimation(-180, 0, ANIM_DURATION));
                ivEditComment.startAnimation(MyAnimations.getMaxAnimation(MAX_DURATION));
                ivRoadComment.startAnimation(MyAnimations.getMiniAnimation(ANIM_DURATION));
                dismiss();
                break;
            case R.id.ivChoose:
                type= ConstantUtil.COMMENT_TYPE_NONE;
                // 图标的动画
                MyAnimations.startAnimationsOut(switchContainer, ANIM_DURATION);
                // 加号的动画
                ivChoose.startAnimation(MyAnimations.getRotateAnimation(-180, 0, ANIM_DURATION));
                //消失
                dismiss();
                break;
        }
    }
}
