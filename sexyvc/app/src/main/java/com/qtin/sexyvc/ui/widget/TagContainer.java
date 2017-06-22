package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.TagEntity;

import java.util.ArrayList;
import java.util.List;
import static com.jess.arms.utils.DeviceUtils.getDisplayMetrics;

/**
 * Created by ls on 17/6/9.
 */
public class TagContainer extends ViewGroup {

    private List<View> lineViews = new ArrayList<>();

    public TagContainer(Context context) {
        super(context);
    }

    public TagContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TagContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStringValue(ArrayList<TagEntity> values){
        removeAllViews();
        if(values==null||values.isEmpty()){
            return;
        }
        for(int i=0;i<values.size();i++){

            if(values.get(i)==null||values.get(i).getTag_name().trim().length()==0){
               break;
            }

            TextView textView=new TextView(getContext());
            textView.setTextSize(10);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.black30));
            textView.setPadding(dpToPixel(6),0,dpToPixel(6),0);
            MarginLayoutParams params=new MarginLayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0,0,dpToPixel(8),0);
            textView.setLayoutParams(params);
            addView(textView);
        }
        //requestLayout();
        // invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        lineViews.clear();

        int cCount = getChildCount();

        int width = getWidth();//整个控件的宽度
        int lineWidth = 0;//该行的宽度
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            if (childWidth + lineWidth+ lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                child.setVisibility(View.VISIBLE);
            }else{
                lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            }
            lineViews.add(child);
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();

        for(int i=0;i<lineViews.size();i++){
            View child = lineViews.get(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);
            left += child.getMeasuredWidth() + lp.leftMargin+ lp.rightMargin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(sizeWidth,sizeHeight);
    }

    private int dpToPixel(float dp) {
        return (int) (dp * (getDisplayMetrics(getContext()).densityDpi / 160F));
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
