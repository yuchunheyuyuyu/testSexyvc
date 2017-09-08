package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.CommonUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/7.
 */
public class InvestorView extends FrameLayout {

    public static interface OnInvestorItemClickListener{
        void onInvestorClickItem(int position);
    }

    public static interface onInvestorChangeListener{
        void onPageChange(int position);
    }

    private onInvestorChangeListener onInvestorChangeListener;
    private OnInvestorItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnInvestorItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnInvestorChangeListener(InvestorView.onInvestorChangeListener onInvestorChangeListener) {
        this.onInvestorChangeListener = onInvestorChangeListener;
    }

    private ViewPager mViewPager;
    private Context context;
    private List<View> views = new ArrayList<>();//每一页的内容
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private int mCurrentPage;

    private static final String KEY_INDEX = "key_index";
    private static final String KEY_DEFAULT = "key_default";

    private int mTouchSlop;

    public InvestorView(@NonNull Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public InvestorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvestorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        //添加viewPager
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.investorViewPager);
        addView(view);
        setViewPagerScrollSpeed();
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());
        bundle.putFloat(KEY_INDEX, mCurrentPage);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentPage = bundle.getInt(KEY_INDEX);
            mViewPager.setCurrentItem(mCurrentPage);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
    }

    public void setData(final ArrayList<InvestorEntity> data) {
        views.clear();
        if (data == null || data.isEmpty()) {
            return;
        }

        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_investor, null);
            CustomApplication application = (CustomApplication) context.getApplicationContext();
            mImageLoader = application.getAppComponent().imageLoader();

            ViewHolder holder = new ViewHolder(view);
            bindData(holder, data.get(i), i);
            views.add(view);
        }

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views == null ? 0 : views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
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
        mViewPager.setOnTouchListener(new OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        if (xDiff < mTouchSlop && yDiff <= mTouchSlop)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {
                            int item = mViewPager.getCurrentItem();
                            if (onItemClickListener != null) {
                                onItemClickListener.onInvestorClickItem(item);
                            }
                        }
                        break;
                }
                return false;
            }
        });

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                if(onInvestorChangeListener!=null){
                    onInvestorChangeListener.onPageChange(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 800;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    public void bindData(ViewHolder holder, InvestorEntity entity, final int position) {
        mImageLoader.loadImage(context.getApplicationContext(), GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());
        holder.tvName.setText(StringUtil.formatString(entity.getInvestor_name()));
        holder.tvFundName.setText(StringUtil.formatString(entity.getInvestor_title()));
        holder.tvInvestorTitle.setText(StringUtil.formatString(entity.getInvestor_title()));
        if (entity.getInvestor_uid() > 0) {
            holder.ivAnthStatus.setVisibility(View.VISIBLE);
        } else {
            holder.ivAnthStatus.setVisibility(View.GONE);
        }

        holder.llInvestorFocus.setVisibility(View.GONE);
        holder.llInvestorFeedBack.setVisibility(View.GONE);
        holder.llInvestorFollow.setVisibility(View.GONE);
        holder.llInvestorScore.setVisibility(View.GONE);


        if (position == 0) {
            holder.tvTopTitle.setText(context.getString(R.string.home_whole_best));
            holder.ivLeft.setImageResource(R.drawable.hots_title_1);
            holder.ivRight.setImageResource(R.drawable.hots_title_1);

            holder.llInvestorScore.setVisibility(View.VISIBLE);
            holder.tvRatingScore.setText("" + entity.getScore());
            holder.ratingScore.setRating(entity.getScore());

        } else if (position == 1) {
            holder.tvTopTitle.setText(context.getString(R.string.home_feedback_fastest));
            holder.ivLeft.setImageResource(R.drawable.hots_title_2_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_2_r);

            holder.llInvestorFeedBack.setVisibility(View.VISIBLE);
            holder.pbFeedbackSpeed.setProgress(countRoadPercent(entity.getFeedback_agree(), entity.getFeedback_against()));

        } else if (position == 2) {
            holder.tvTopTitle.setText(context.getString(R.string.home_most_funs));
            holder.ivLeft.setImageResource(R.drawable.hots_title_3_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_3_r);

            holder.llInvestorFollow.setVisibility(View.VISIBLE);
            holder.tvFollowNum.setText("" + entity.getFollow_number());

        } else if (position == 3) {
            holder.tvTopTitle.setText(context.getString(R.string.home_whole_focus));
            holder.ivLeft.setImageResource(R.drawable.hots_title_4_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_4_r);

            holder.llInvestorFocus.setVisibility(View.VISIBLE);
            holder.tvCommentNum.setText("" + entity.getComment_number());
        }
    }

    private int countRoadPercent(int feedback_agree, int feedback_against) {

        feedback_agree += 5;
        feedback_against += 5;

        int totle = feedback_agree + feedback_against;
        if (totle == 0) {
            return 0;
        } else {
            return feedback_agree * 100 / totle;
        }
    }

    static class ViewHolder {
        @BindView(R.id.ivLeft)
        ImageView ivLeft;
        @BindView(R.id.tvTopTitle)
        TextView tvTopTitle;
        @BindView(R.id.ivRight)
        ImageView ivRight;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvFundName)
        TextView tvFundName;
        @BindView(R.id.tvInvestorTitle)
        TextView tvInvestorTitle;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.llInvestorFocus)
        LinearLayout llInvestorFocus;
        @BindView(R.id.tvFollowNum)
        TextView tvFollowNum;
        @BindView(R.id.llInvestorFollow)
        LinearLayout llInvestorFollow;
        @BindView(R.id.pbFeedbackSpeed)
        ProgressBar pbFeedbackSpeed;
        @BindView(R.id.llInvestorFeedBack)
        LinearLayout llInvestorFeedBack;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvRatingScore)
        TextView tvRatingScore;
        @BindView(R.id.llInvestorScore)
        LinearLayout llInvestorScore;
        @BindView(R.id.llWholeContent)
        View llWholeContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
