package com.jess.arms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

public class UploadPhotoUtil {
	/**
	 * 取得缩略图
	 */
	public static Bitmap decodeSampledBitmapFromPath(String path, int width,int height, boolean isScale) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		if (isScale) {
			options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options,width, height);
		}
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		return bitmap;
	}
	
	public static Bitmap getUpLoadImage(String path, int width, int height, boolean isScale) {
		
		Bitmap bitMap = decodeSampledBitmapFromPath(path, width, height,isScale);
		// 图片允许最大空间 单位：KB
		double maxSize = 2*1024.00;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			Bitmap lbitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),bitMap.getHeight() / Math.sqrt(i));
			if(lbitMap!=null){
				bitMap.recycle();
				return lbitMap;
			}
		}
		return bitMap;
	}

	/**
	 * 图片的缩放方法
	 *
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight) {
		if(bgimage==null){
			return null;
		}
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,(int) height, matrix, true);
		
		return bitmap;
	}
}
