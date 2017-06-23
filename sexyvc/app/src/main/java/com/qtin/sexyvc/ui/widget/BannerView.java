package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 17/6/7.
 */
public class BannerView extends FrameLayout {
    private final int defalutIndicatorMarginBottom=8;
    private int indicatorMarginBottom=defalutIndicatorMarginBottom;

    private ViewPager mViewPager;
    private LinearLayout mIndicatorContainer;
    private Context context;
    private List<View> views=new ArrayList<>();//每一页的内容
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private TextView tvTitle;

    private final int indicator_unselect_width=4;
    private final int indicator_selected_width=20;
    private final int indicator_height=4;
    private int mCurrentPage;

    private boolean isContainText;//是否包含文字描述

    private static final String KEY_INDEX = "key_index";
    private static final String KEY_DEFAULT = "key_default";

    public BannerView(@NonNull Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initXml(context,attrs,defStyleAttr);
        init(context);
    }

    private void init(Context context){
        //添加viewPager
        LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager=new ViewPager(context);
        mViewPager.setLayoutParams(params);
        addView(mViewPager);

        if(isContainText){
            tvTitle=new TextView(context);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            tvTitle.setBackgroundResource(R.color.banner_text_back);
            tvTitle.setTextColor(context.getResources().getColor(R.color.white90));
            tvTitle.setPadding(dpTpPx(16),dpTpPx(8),dpTpPx(16),dpTpPx(20));

            LayoutParams tvParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            tvParams.gravity=Gravity.BOTTOM;
            tvTitle.setLayoutParams(tvParams);

            addView(tvTitle);
        }

        //添加indicator外部的容器
        LayoutParams p=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mIndicatorContainer=new LinearLayout(context);
        p.gravity=Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        //p.setMargins(0,0,0,indicatorMarginBottom);
        mIndicatorContainer.setLayoutParams(p);
        //mIndicatorContainer.setPadding(0,0,0,indicatorMarginBottom);
        addView(mIndicatorContainer);
    }

    private void initXml( Context context, AttributeSet attrs,int defStyleAttr){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.BannerView,defStyleAttr,0);
        indicatorMarginBottom=array.getDimensionPixelSize(R.styleable.BannerView_indicator_margin_bottom,defalutIndicatorMarginBottom);
        isContainText=array.getBoolean(R.styleable.BannerView_is_container_title,false);
        array.recycle();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(KEY_DEFAULT,super.onSaveInstanceState());
        bundle.putFloat(KEY_INDEX,mCurrentPage);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            mCurrentPage=bundle.getInt(KEY_INDEX);
            mViewPager.setCurrentItem(mCurrentPage);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize,heightSize);
    }

    public void setData(final ArrayList<BannerEntity> data){
        views.clear();
        if(data==null||data.isEmpty()){
            return;
        }
        if(isContainText){
            tvTitle.setText(data.get(0).getTitle());
        }


        for(int i=0;i<data.size();i++){
            ImageView iv=new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CustomApplication application= (CustomApplication) context.getApplicationContext();
            mImageLoader=application.getAppComponent().imageLoader();
            mImageLoader.loadImage(application, GlideImageConfig
                    .builder()
                    .url(CommonUtil.getAbsolutePath(data.get(i).getImg_url()))
                    .imageView(iv)
                    .build());

            views.add(iv);
        }

        PagerAdapter pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return views==null?0:views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage=position;
                if(isContainText){
                    tvTitle.setText(data.get(position).getTitle());
                }

                int childNum=mIndicatorContainer.getChildCount();
                if(childNum>0){
                    for(int i=0;i<childNum;i++){
                        View child=mIndicatorContainer.getChildAt(i);

                        if(i==position){
                            child.setSelected(true);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dpTpPx(indicator_selected_width),dpTpPx(indicator_height));
                            params.setMargins(0,0,dpTpPx(4),dpTpPx(defalutIndicatorMarginBottom));
                            child.setLayoutParams(params);
                        }else{
                            child.setSelected(false);
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dpTpPx(indicator_unselect_width),dpTpPx(indicator_height));
                            params.setMargins(0,0,dpTpPx(4),dpTpPx(defalutIndicatorMarginBottom));
                            child.setLayoutParams(params);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addIndicator(data.size());
    }

    /**
     * 添加indicator
     * @param count
     */
    private void addIndicator(int count){
        mIndicatorContainer.removeAllViews();
        for(int i=0;i<count;i++){
            View view=new View(context);
            view.setBackgroundResource(R.drawable.indicator_selector);
            if(i==0){
                view.setSelected(true);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dpTpPx(indicator_selected_width),dpTpPx(indicator_height));
                params.setMargins(0,0,dpTpPx(4),dpTpPx(defalutIndicatorMarginBottom));
                view.setLayoutParams(params);
            }else{
                view.setSelected(false);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dpTpPx(indicator_unselect_width),dpTpPx(indicator_height));
                params.setMargins(0,0,dpTpPx(4),dpTpPx(defalutIndicatorMarginBottom));
                view.setLayoutParams(params);
            }
            mIndicatorContainer.addView(view);
        }
    }
    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
