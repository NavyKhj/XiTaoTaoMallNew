package com.shangcheng.common_module.utils.imageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;


import com.shangcheng.common_module.R;
import com.shangcheng.common_module.utils.FileUtils;
import com.shangcheng.common_module.utils.T;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class Note:
 * use this class to load image,single instance
 */
public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private GlideImageLoaderStrategy mStrategy;

    private String[] imageTypes = new String[] { ".jpg",".png", ".jpeg","webp"};


    public ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    public void loadImage(Context context, ImageView imageView, String url){
        ImageLoader imageLoader = new ImageLoader.Builder()
                .url(url)
                .imgView( imageView)
                .placeHolder(R.mipmap.bc_ic_placeholder_large)
                .errorHolder(R.mipmap.bc_ic_placeholder_large)
                .scaleType(1)
                .build();
        mStrategy.loadImage(context, imageLoader);
    }
    public void loadImage(Context context, ImageView imageView, String url, int image){
        ImageLoader imageLoader = new ImageLoader.Builder()
                .url(url)
                .imgView( imageView)
                .placeHolder(image)
                .errorHolder(image)
                .scaleType(1)
                .build();
        mStrategy.loadImage(context, imageLoader);
    }
    public void loadImage(Context context, ImageLoader img) {
        mStrategy.loadImage(context, img);
    }

    public void downloadImage(Context context, String path, DownloadDelegate delegate) {
        mStrategy.downloadImage(context, path,delegate);
    }

    public void setImageLoadingListener(GlideImageLoaderStrategy.OnImageLoadingListener imageLoadingListener) {
        mStrategy.setImageLoadingListener(imageLoadingListener);
    }

    /**
     * 保存到相册
     */
    public void saveImage(Context context, Bitmap bitmap){
        // 创建目录
        File appDir = new File(Environment.getExternalStorageDirectory(), "CompanyHelperImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String imageType = "jpeg"; //获取图片类型
        String fileName = System.currentTimeMillis() + "." + imageType;
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        //保存图片
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            T.show("图片下载成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        if (bmp == null){
            T.show( "保存出错了...");
            return;
        }
        String path = getExtraPath("DCIM");
        // 首先保存图片
        File appDir = new File(path, "Camera");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            T.show( "文件未发现");
            e.printStackTrace();
        } catch (IOException e) {
            T.show( "保存出错了...");
            e.printStackTrace();
        }catch (Exception e){
            T.show( "保存出错了...");
            e.printStackTrace();
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = FileUtils.restrictedAccess(context,intent,file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
    private static String getExtraPath(String folder) {
        String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + folder;
        File file = new File(storagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return storagePath;
    }
    //给图片添加水印后保存
    public static void addWaterToGallery(Context context, Bitmap bmp, String water) {
        if (bmp == null){
            T.show( "保存出错了...");
            return;
        }
        bmp=addWaterMarking(bmp,water);
        String path = getExtraPath("DCIM");
        // 首先保存图片
        File appDir = new File(path, "Camera");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            T.show( "文件未发现");
            e.printStackTrace();
        } catch (IOException e) {
            T.show( "保存出错了...");
            e.printStackTrace();
        }catch (Exception e){
            T.show( "保存出错了...");
            e.printStackTrace();
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = FileUtils.restrictedAccess(context,intent,file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    //设置图片水印----文字水印
    public static Bitmap addWaterMarking(Bitmap bitmap, String water) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap icon = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
        Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上
        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);// 过滤一些
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, height);// 创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
        textPaint.setTextSize(18);// 字体大小 可以调整
        //textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
        textPaint.setColor(Color.parseColor("#aeffffff"));// 采用的颜色
        //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
        canvas.drawText(water, width  - textPaint.measureText(water, 0, water.length())-20, height-20, textPaint);// 绘制上去字
        canvas.save();
        canvas.restore();
        return icon;
    }
}
