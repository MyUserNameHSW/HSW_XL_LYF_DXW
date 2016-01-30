package com.lyy.util;

import java.io.File;
import java.io.FileInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

public class ChangeImg {
	public static Bitmap loadBitmap(String url, int width, int height) {
		Bitmap bitmap = null;
		if (url == null || !new File(url).exists())
			return bitmap;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(url, opts);
			opts.inSampleSize = calculateSampleSize(opts, width, height);
			opts.inJustDecodeBounds = false;
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			bitmap = BitmapFactory.decodeStream(new FileInputStream(url), null,
					opts);
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	private static int calculateSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
}
