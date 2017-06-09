package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtin.sexyvc.R;

import java.util.ArrayList;

import static com.jess.arms.utils.DeviceUtils.getDisplayMetrics;

/**
 * Created by ls on 17/6/9.
 */
public class TagContainer extends LinearLayout {

    private int mWidth;
    private int hasUseWidth;

    public TagContainer(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }

    public TagContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    public TagContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM);
    }

    public void setStringValue(ArrayList<String> values,int width){
        mWidth=width;
        removeAllViews();
        if(values==null&&values.isEmpty()){
            setVisibility(View.GONE);
        }
        for(int i=0;i<values.size();i++){

            if(values.get(i)==null||values.get(i).trim().length()==0){
               break;
            }

            TextView textView=new TextView(getContext());
            textView.setTextSize(10);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.black30));
            textView.setPadding(dpToPixel(6),0,dpToPixel(6),0);
            LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0,0,dpToPixel(8),0);
            textView.setLayoutParams(params);

            hasUseWidth+=(textView.getWidth()+dpToPixel(8));

            if(hasUseWidth<mWidth){
                textView.setText(values.get(i));
                textView.setBackground(getResources().getDrawable(R.drawable.tag_shape_selector));
                addView(textView);
            }else{
                textView.setText("...");
                addView(textView);
                return;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
    }

    private int dpToPixel(float dp) {
        return (int) (dp * (getDisplayMetrics(getContext()).densityDpi / 160F));
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
