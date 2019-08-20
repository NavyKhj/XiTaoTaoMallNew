package com.shangcheng.common_module.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import razerdp.blur.FastBlur;

/**
 * 图片处理Utils
 *
 * @author Administrator
 * @time 2017/1/13
 */

public class ImageUtils {
    private static final Bitmap.Config BITMAP_CONFIG;
    private static final Uri STORAGE_URI;

    public ImageUtils() {
    }

    public static Uri addImage(ContentResolver cr, String path) {
        File file = new File(path);
        String name = file.getName();
        int i = name.lastIndexOf(".");
        String title = name.substring(0, i);
        String filename = title + name.substring(i);
        int[] degree = new int[1];
        return addImage(cr, title, System.currentTimeMillis(), (Location) null, file.getParent(), filename, degree);
    }

    private static Uri addImage(ContentResolver cr, String title, long dateTaken, Location location, String directory, String filename, int[] degree) {
        File file = new File(directory, filename);
        long size = file.length();
        ContentValues values = new ContentValues(9);
        values.put("title", title);
        values.put("_display_name", filename);
        values.put("datetaken", dateTaken);
        values.put("mime_type", "image/jpeg");
        values.put("orientation", degree[0]);
        values.put("_data", file.getAbsolutePath());
        values.put("_size", size);
        if (location != null) {
            values.put("latitude", location.getLatitude());
            values.put("longitude", location.getLongitude());
        }

        return cr.insert(STORAGE_URI, values);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Uri addVideo(ContentResolver cr, String title, long dateTaken, Location location, String directory, String filename) {
        String filePath = directory + "/" + filename;

        try {
            File size = new File(directory);
            if (!size.exists()) {
                size.mkdirs();
            }

            new File(directory, filename);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        long size1 = (new File(directory, filename)).length();
        ContentValues values = new ContentValues(9);
        values.put("title", title);
        values.put("_display_name", filename);
        values.put("datetaken", dateTaken);
        values.put("mime_type", "video/3gpp");
        values.put("_data", filePath);
        values.put("_size", size1);
        if (location != null) {
            values.put("latitude", location.getLatitude());
            values.put("longitude", location.getLongitude());
        }

        return cr.insert(STORAGE_URI, values);
    }

    public static Bitmap rotate(Context context, Bitmap bitmap, int degree, boolean isRecycle) {
        Matrix m = new Matrix();
        m.setRotate((float) degree, (float) bitmap.getWidth() / 2.0F, (float) bitmap.getHeight() / 2.0F);

        try {
            Bitmap ex = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            if (isRecycle) {
                bitmap.recycle();
            }

            return ex;
        } catch (OutOfMemoryError var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static int getBitmapExifRotate(String path) {
        short digree = 0;
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(path);
        } catch (IOException var4) {
            var4.printStackTrace();
            return 0;
        }

        if (exif != null) {
            int ori = exif.getAttributeInt("Orientation", 0);
            switch (ori) {
                case 3:
                    digree = 180;
                    break;
                case 6:
                    digree = 90;
                    break;
                case 8:
                    digree = 270;
                    break;
                default:
                    digree = 0;
            }
        }

        return digree;
    }

    public static Bitmap rotateBitmapByExif(Bitmap bitmap, String path, boolean isRecycle) {
        int digree = getBitmapExifRotate(path);
        if (digree != 0) {
            bitmap = rotate((Context) null, bitmap, digree, isRecycle);
        }

        return bitmap;
    }

    public static final Bitmap createBitmapFromPath(String path, Context context) {
        WindowManager manager = (WindowManager) context.getSystemService("window");
        Display display = manager.getDefaultDisplay();
        int screenW = display.getWidth();
        int screenH = display.getHeight();
        return createBitmapFromPath(path, context, screenW, screenH);
    }

    public static final Bitmap createBitmapFromPath(String path, Context context, int maxResolutionX, int maxResolutionY) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = null;
        if (path.endsWith(".3gp")) {
            return ThumbnailUtils.createVideoThumbnail(path, 1);
        } else {
            try {
                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                int e = options.outWidth;
                int height = options.outHeight;
                options.inSampleSize = computeBitmapSimple(e * height, maxResolutionX * maxResolutionY);
                options.inPurgeable = true;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = false;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(path, options);
                return rotateBitmapByExif(bitmap, path, true);
            } catch (OutOfMemoryError var8) {
                options.inSampleSize *= 2;
                bitmap = BitmapFactory.decodeFile(path, options);
                return rotateBitmapByExif(bitmap, path, true);
            } catch (Exception var9) {
                var9.printStackTrace();
                return null;
            }
        }
    }

    public static final Bitmap createBitmapFromPath(byte[] data, Context context) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = null;

        try {
            WindowManager e = (WindowManager) context.getSystemService("window");
            Display display = e.getDefaultDisplay();
            int screenW = display.getWidth();
            int screenH = display.getHeight();
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int width = options.outWidth;
            int height = options.outHeight;
            int maxResolutionX = screenW * 2;
            int maxResolutionY = screenH * 2;
            options.inSampleSize = computeBitmapSimple(width * height, maxResolutionX * maxResolutionY);
            options.inPurgeable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = false;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return bitmap;
        } catch (OutOfMemoryError var12) {
            options.inSampleSize *= 2;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return bitmap;
        } catch (Exception var13) {
            var13.printStackTrace();
            return null;
        }
    }

    public static int computeBitmapSimple(int realPixels, int maxPixels) {
        try {
            if (realPixels <= maxPixels) {
                return 1;
            } else {
                int e;
                for (e = 2; realPixels / (e * e) > maxPixels; e *= 2) {
                    ;
                }

                return e;
            }
        } catch (Exception var3) {
            return 1;
        }
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            try {
                Bitmap e;
                if (drawable instanceof ColorDrawable) {
                    e = Bitmap.createBitmap(2, 2, BITMAP_CONFIG);
                } else {
                    e = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
                }

                Canvas canvas = new Canvas(e);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return e;
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    static {
        BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
        STORAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    @SuppressLint("NewApi")
    public static void blur(Context context, Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        /**
         * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
         */
        float scaleFactor = 1f;//图片缩放比例；
        float radius = 40;//模糊程度

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackground(new BitmapDrawable(context.getResources(), overlay));

    }
    @SuppressLint("NewApi")
    public static void addShadow(Context context, Bitmap bm, int radius, View view) {
        Bitmap bitmap = FastBlur.doBlur(bm,radius,false);
        int[] mBackShadowColors = new int[] { 0x10000000, 0xff757575};
        GradientDrawable mBackShadowDrawableLR = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mBackShadowDrawableLR.setBounds(0, 0, bm.getWidth(), bm.getHeight());
        Canvas canvas = new Canvas(bitmap);
        mBackShadowDrawableLR.draw(canvas);
        view.setBackground(new BitmapDrawable(context.getResources(),bitmap));
    }
    @SuppressLint("NewApi")
    public static Bitmap addBlur(Context context, Bitmap bm, int radius) {
        Bitmap bitmap = FastBlur.doBlur(bm,radius,false);
        int[] mBackShadowColors = new int[] { 0x10000000, 0xff757575};
        GradientDrawable mBackShadowDrawableLR = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mBackShadowDrawableLR.setBounds(0, 0, bm.getWidth(), bm.getHeight());
        Canvas canvas = new Canvas(bitmap);
        mBackShadowDrawableLR.draw(canvas);
        return bitmap;
    }
    @SuppressLint("NewApi")
    public static Bitmap addShadow(Context context, Bitmap bm) {
        int[] mBackShadowColors = new int[] { 0x10000000, 0xff757575};
        GradientDrawable mBackShadowDrawableLR = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mBackShadowDrawableLR.setBounds(0, 0, bm.getWidth(), bm.getHeight());
        Canvas canvas = new Canvas(bm);
        mBackShadowDrawableLR.draw(canvas);
        return bm;
    }
    @SuppressLint("NewApi")
    public static void addShadowImageView(Context context, Bitmap bm, int radius, ImageView view) {
        Bitmap bitmap = FastBlur.doBlur(bm,radius,false);
        int[] mBackShadowColors = new int[] {  0x10000000, 0xff757575};
        GradientDrawable mBackShadowDrawableLR = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mBackShadowDrawableLR.setBounds(0, 0, bm.getWidth(), bm.getHeight());
        Canvas canvas = new Canvas(bitmap);
        mBackShadowDrawableLR.draw(canvas);
        view.setImageDrawable(new BitmapDrawable(context.getResources(),bitmap));
    }


    /**
     * 获取缩略图地址
     * @param url 原图地址
     * @param width 原图宽度
     * @param height 原图高度
     * @param maxLength 最大一边的长度（eg：app.getWidth *0.05）
     * @return 缩略图地址
     */
    public static String getThumbnailUrl(String url, String width, String height, float maxLength) {
        if (url.contains("&thumb=")) {
            return url;
        }
        try {
            int[] widthHeight = {Integer.valueOf(width), Integer.valueOf(height)};//原图宽高
            if (Math.max(widthHeight[0], widthHeight[1]) > maxLength) {
                float maxWH = Float.valueOf(Math.max(widthHeight[0], widthHeight[1]));
                float fa = maxLength / maxWH;
                final int[] tw = new int[]{(int) (widthHeight[0] * fa), (int) (widthHeight[1] * fa)};
                /**
                 * &thumb=dbl_300x   按照宽等比例缩放
                 * &thumb=dbl_x300  按照高等比例缩放
                 * &thumb=dbl_300x300 这种形式是按照短边等比例缩放的
                 **/
                if (tw[0] > tw[1]) {
                    url += "&thumb=" + tw[0] + "x";
                } else {
                    url += "&thumb=x" + tw[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 是否是gif
     */
    public static boolean isGif(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return name.toLowerCase().endsWith(".gif");
    }

   
}
