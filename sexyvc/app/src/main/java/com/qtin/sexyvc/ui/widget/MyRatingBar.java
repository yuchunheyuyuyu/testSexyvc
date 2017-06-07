package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.qtin.sexyvc.R;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by ls on 17/6/7.
 */
public class MyRatingBar extends View {

    private float stepSize;//步长
    private float rating;//评分
    private boolean isIndicator;//是否只是显示
    private int starSize;//星星的大小
    private int backSize;//圆角矩形的大小

    public MyRatingBar(Context context) {
        super(context);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray array=getContext().obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        stepSize=array.getFloat(R.styleable.MyRatingBar_ls_stepSize,0.5f);
        rating=array.getFloat(R.styleable.MyRatingBar_ls_rating,1);
        isIndicator=array.getBoolean(R.styleable.MyRatingBar_ls_isIndicator,true);
        starSize=array.getDimensionPixelSize(R.styleable.MyRatingBar_ls_starSize,40);

    }

    /**
     * Convenience method to convert a value in the given dimension to pixels.
     * @param value
     * @param dimen
     * @return
     */
    private float valueToPixels(float value, @Dimension int dimen) {
        switch (dimen) {
            case Dimension.DP:
                return applyDimension(COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
            case Dimension.SP:
                return applyDimension(COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
            default:
                return value;
        }
    }
}
