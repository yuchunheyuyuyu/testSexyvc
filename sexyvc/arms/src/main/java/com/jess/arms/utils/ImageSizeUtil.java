package com.jess.arms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.jess.arms.base.BaseApplication;

import java.lang.reflect.Field;

/**
 * @author ShengLiu 图片尺寸处理
 */
public class ImageSizeUtil {
	/**
	 * 计算
	 */
	public static int caculateInSampleSize(Options options, int reqWidth,int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;

		if (width > reqWidth || height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);

			inSampleSize = Math.max(widthRadio, heightRadio);
		}
		return inSampleSize;
	}

	/**
	 * 得到ImageView的尺寸
	 */
	public static ImageSize getImageViewSize(ImageView imageView) {

		ImageSize imageSize = new ImageSize();
		DisplayMetrics displayMetrics = imageView.getContext().getResources()
				.getDisplayMetrics();

		LayoutParams lp = imageView.getLayoutParams();

		int width = imageView.getWidth();
		if (width <= 0) {
			width = lp.width;
		}
		if (width <= 0) {
			width = getImageViewFieldValue(imageView, "mMaxWidth");
		}
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}

		int height = imageView.getHeight();
		if (height <= 0) {
			height = lp.height;
		}
		if (height <= 0) {
			height = getImageViewFieldValue(imageView, "mMaxHeight");
		}
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}
		imageSize.width = width;
		imageSize.height = height;

		return imageSize;
	}

	public static class ImageSize {
		int width;
		int height;
	}

	private static int getScaleNum() {
		int maxWidthHeigth = 800;
		int dis_height = BaseApplication.screenSize.y;
		int dis_width = BaseApplication.screenSize.x;

		int max = Math.min(maxWidthHeigth, Math.max(dis_height, dis_width));
		return max;
	}

	/**
	 * 创建上传缩略图
	 * 
	 * @param src
	 * @return
	 */
	public static Bitmap createScaleBitmap(Bitmap src) {
		Matrix m = new Matrix();
		int max = getScaleNum();
		if (src != null) {
			int srcWidth = src.getWidth();
			int srcHeight = src.getHeight();
			float scale = (max / (float) (Math.max(srcWidth, srcHeight)));
			m.postScale(scale, scale);
			Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
					src.getHeight(), m, true);
			return bitmap;
		}
		return null;
	}

	/**
	 * created by LiuSheng
	 * 
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
		}
		return value;
	}
}
