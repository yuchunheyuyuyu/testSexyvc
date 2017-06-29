package com.qtin.sexyvc.popupwindow;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.FastBlur;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.anim.MyAnimations;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChoosePopupwindow implements OnClickListener {

    private ViewHolder holder;
    private static final int SHOW_DELAY_TIME=100;
    private static final int DISMISS_DELAY_TIME=600;
    private static final int ANIM_DURATION=300;
    private static final int MAX_DURATION=400;

    public static interface OnChooseListener {
        void onChooseLoad();
        void onChooseEdit();
    }

    private OnChooseListener listener;
    private PopupWindow popupWindow;

    public ChoosePopupwindow(OnChooseListener listener) {
        super();
        this.listener = listener;
    }

    public void show(Activity context) {
        if (popupWindow == null) {

        }

        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int width = frame.width();
        int height = frame.height();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.option_comment_popupwindow, null);

        holder=new ViewHolder(view);

        //背景
        holder.ivBackGround.setImageBitmap(FastBlur.getBlurByColor("#b3333333"));
        holder.ivChoose.setOnClickListener(this);
        holder.ivEditComment.setOnClickListener(this);
        holder.ivRoadComment.setOnClickListener(this);
        popupWindow = new PopupWindow(view, width, height);
        popupWindow.setFocusable(true);

        View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        popupWindow.setAnimationStyle(R.style.dialog_fade_animation);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        showAnim();
    }

    private void showAnim(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 图标的动画
                MyAnimations.startAnimationsIn(holder.switchContainer, ANIM_DURATION);
                // 加号的动画
                holder.ivChoose.startAnimation(MyAnimations.getRotateAnimation(0, -225, ANIM_DURATION));
                //文字的淡入
                textViewShow(holder.tvChooseTitle);
                textViewShow(holder.tvEditComment);
                textViewShow(holder.tvRoadComment);
            }
        },SHOW_DELAY_TIME);
    }

    private void textViewShow(TextView tv){
        Animation animation=MyAnimations.getFadeInAnimation(ANIM_DURATION);
        tv.setVisibility(View.VISIBLE);
        tv.startAnimation(animation);
    }

    public void dismiss() {
        //文字的淡出
        textViewDismiss(holder.tvChooseTitle);
        textViewDismiss(holder.tvEditComment);
        textViewDismiss(holder.tvRoadComment);

        if (popupWindow != null && popupWindow.isShowing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    popupWindow.dismiss();
                }
            },DISMISS_DELAY_TIME);
        }
    }

    private void textViewDismiss(final TextView textView){
        Animation animation=MyAnimations.getFadeOutAnimation(ANIM_DURATION);
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



    public void destroy() {
        popupWindow = null;
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEditComment:
                holder.ivChoose.startAnimation(MyAnimations.getRotateAnimation(-225, 0, ANIM_DURATION));
                holder.ivEditComment.startAnimation(MyAnimations.getMaxAnimation(MAX_DURATION));
                holder.ivRoadComment.startAnimation(MyAnimations.getMiniAnimation(ANIM_DURATION));
                listener.onChooseEdit();
                break;
            case R.id.ivRoadComment:
                holder.ivChoose.startAnimation(MyAnimations.getRotateAnimation(-225, 0, ANIM_DURATION));
                holder.ivRoadComment.startAnimation(MyAnimations.getMaxAnimation(MAX_DURATION));
                holder.ivEditComment.startAnimation(MyAnimations.getMiniAnimation(ANIM_DURATION));
                listener.onChooseLoad();
                break;
            case R.id.ivChoose:
                // 图标的动画
                MyAnimations.startAnimationsOut(holder.switchContainer, ANIM_DURATION);
                // 加号的动画
                holder.ivChoose.startAnimation(MyAnimations.getRotateAnimation(-225, 0, ANIM_DURATION));
                //消失
                dismiss();
                break;
        }
    }

    static class ViewHolder {
        @BindView(R.id.ivChoose)
        ImageView ivChoose;
        @BindView(R.id.tvRoadComment)
        TextView tvRoadComment;
        @BindView(R.id.tvEditComment)
        TextView tvEditComment;
        @BindView(R.id.ivRoadComment)
        ImageView ivRoadComment;
        @BindView(R.id.ivEditComment)
        ImageView ivEditComment;
        @BindView(R.id.tvChooseTitle)
        TextView tvChooseTitle;
        @BindView(R.id.ivBackGround)
        ImageView ivBackGround;
        @BindView(R.id.switchContainer)
        RelativeLayout switchContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
