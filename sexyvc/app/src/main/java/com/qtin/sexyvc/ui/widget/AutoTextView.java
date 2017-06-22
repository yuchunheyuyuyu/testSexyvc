package com.qtin.sexyvc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.main.fraghome.bean.NewsBean;

import java.util.ArrayList;

public class AutoTextView extends TextSwitcher implements
		ViewSwitcher.ViewFactory {

	private float mHeight;
	private Context mContext;
	private Rotate3dAnimation mInUp;
	private Rotate3dAnimation mOutUp;
	
	private Rotate3dAnimation mInDown;
	private Rotate3dAnimation mOutDown;
	private ArrayList<NewsBean> news;
	private int mCurrentIndex;
	private final int DURATING_TIME=3000;
	private final int INTERVAL_TIME=3000;

	public AutoTextView(Context context) {
		this(context, null);
	}

	private Handler handler=new Handler();
	private Runnable runnable=new Runnable() {
		@Override
		public void run() {
			int index=mCurrentIndex%news.size();
			setText(news.get(index).getContent());
			previous();
			mCurrentIndex++;
			handler.postDelayed(this,INTERVAL_TIME);
		}
	};


	public void setDate(ArrayList<NewsBean> strings){
		if(strings==null||strings.isEmpty()){
			return;
		}
		news=strings;
		handler.removeCallbacks(runnable);
		handler.post(runnable);
	}

	public AutoTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.auto3d);
		mHeight = a.getDimension(R.styleable.auto3d_textSize, 14);
		a.recycle();
		mContext = context;
		init();
	}

	private void init() {
		setFactory(this);
		mInUp = createAnim(-90, 0 , true, true);
		mOutUp = createAnim(0, 90, false, true);
		mInDown = createAnim(90, 0 , true , false);
		mOutDown = createAnim(0, -90, false, false);
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
	}
	
	private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp){
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        rotation.setDuration(800);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
	}

	@Override
	public View makeView() {
		TextView t = new TextView(mContext);
		t.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
		t.setGravity(Gravity.CENTER_VERTICAL);
		//t.setTextSize(mHeight);
		t.setSingleLine();
		t.setEllipsize(TextUtils.TruncateAt.END);
		return t;
	}
	public void previous(){
		if(getInAnimation() != mInDown){
			setInAnimation(mInDown);
		}
		if(getOutAnimation() != mOutDown){
			setOutAnimation(mOutDown);
		}
	}
	public void next(){
		if(getInAnimation() != mInUp){
			setInAnimation(mInUp);
		}
		if(getOutAnimation() != mOutUp){
			setOutAnimation(mOutUp);
		}
	}
	
	 class Rotate3dAnimation extends Animation {
		    private final float mFromDegrees;
		    private final float mToDegrees;
		    private float mCenterX;
		    private float mCenterY;
		    private final boolean mTurnIn;
		    private final boolean mTurnUp;
		    private Camera mCamera;

		    public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
		        mFromDegrees = fromDegrees;
		        mToDegrees = toDegrees;
		        mTurnIn = turnIn;
		        mTurnUp = turnUp;
		    }

		    @Override
		    public void initialize(int width, int height, int parentWidth, int parentHeight) {
		        super.initialize(width, height, parentWidth, parentHeight);
		        mCamera = new Camera();
		        mCenterY = getHeight() / 2;
		        mCenterX = getWidth() / 2;
		    }
		    
		    @Override
		    protected void applyTransformation(float interpolatedTime, Transformation t) {
		        final float fromDegrees = mFromDegrees;
		        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

		        final float centerX = mCenterX ;
		        final float centerY = mCenterY ;
		        final Camera camera = mCamera;
		        final int derection = mTurnUp ? 1: -1;

		        final Matrix matrix = t.getMatrix();

		        camera.save();
		        if (mTurnIn) {
		            camera.translate(0.0f, derection *mCenterY * (interpolatedTime - 1.0f), 0.0f);
		        } else {
		            camera.translate(0.0f, derection *mCenterY * (interpolatedTime), 0.0f);
		        }
		        camera.rotateX(degrees);
		        camera.getMatrix(matrix);
		        camera.restore();

		        matrix.preTranslate(-centerX, -centerY);
		        matrix.postTranslate(centerX, centerY);
		    }
	 }
}
