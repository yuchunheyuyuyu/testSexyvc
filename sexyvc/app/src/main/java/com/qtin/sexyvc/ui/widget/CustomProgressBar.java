package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qtin.sexyvc.R;

/**
 * Created by ls on 17/6/20.
 */

public class CustomProgressBar extends FrameLayout {

    private int max=100;
    private int mWidth;
    private int progress;

    public CustomProgressBar(@NonNull Context context) {
        this(context,null);
    }

    public CustomProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgressBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View viewBack=new View(context);
        LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewBack.setLayoutParams(params);
        viewBack.setBackgroundResource(R.drawable.custom_progressbar_background);
        addView(viewBack);

        View viewFore=new View(context);
        LayoutParams foreParams=new LayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT);
        viewFore.setLayoutParams(foreParams);
        viewFore.setBackgroundResource(R.drawable.custom_progressbar_foreground);
        addView(viewFore);
    }


    public void setProgress(int progress){
        this.progress=progress;
        View view=getChildAt(1);
        LayoutParams params= (LayoutParams) view.getLayoutParams();
        int width=progress*mWidth/max;
        params.width=width;
        view.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        setProgress(progress);
    }
}
