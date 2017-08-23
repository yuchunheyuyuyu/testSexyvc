package com.qtin.sexyvc.popupwindow;

import android.app.Activity;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.qtin.sexyvc.R;

/**
 * Created by ls on 17/8/11.
 */
public class GuidePopupwindow {
    private PopupWindow popupWindow;
    private OnPopupWindowClickListener onPopupWindowClickListener;

    public GuidePopupwindow(OnPopupWindowClickListener onPopupWindowClickListener) {
        this.onPopupWindowClickListener = onPopupWindowClickListener;
    }

    public void show(Activity context,int index){
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int width = frame.width();
        int height = frame.height();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view=null;
        if(index==0){
            view = layoutInflater.inflate(R.layout.guide_popupwindow_a, null);
        }else if(index==1){
            view = layoutInflater.inflate(R.layout.guide_popupwindow_b, null);
        }else if(index==2){
            view = layoutInflater.inflate(R.layout.guide_popupwindow_c, null);
        }else if(index==3){
            view = layoutInflater.inflate(R.layout.guide_popupwindow_d, null);
        }else{
            view = layoutInflater.inflate(R.layout.guide_popupwindow_e, null);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPopupWindowClickListener.onClick();
            }
        });
        popupWindow = new PopupWindow(view, width, height);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setFocusable(true);
        View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        popupWindow.setAnimationStyle(R.style.popupwindow_fade_animation);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss(){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow=null;
        }
    }
}
