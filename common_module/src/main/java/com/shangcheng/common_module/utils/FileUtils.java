package com.shangcheng.common_module.utils;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.shangcheng.common_module.baseApplication.app;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by jingchao on 2017-4-17.
 */

public class FileUtils {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has appropriate permission) or
     * on device's file system depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue #989)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }


    /**
     * android7.0
     * 目录限制访问（文件 file:// URI类型 的 Intent FileProvider）setDataAndType
     * @param context
     * @param intent
     * @param file
     * @return
     */
    public static Uri restrictedAccess(Context context, Intent intent, File file){
        Uri uri;
        //判断版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //provider authorities
            uri = FileProvider.getUriForFile(context,  context.getApplicationContext().getPackageName(), file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 获取SDCard上目录的路径
     * @param folder
     * @return
     */
    public static String getExtraPath(String folder) {
        String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folder;
        File file = new File(storagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return storagePath;
    }

    /**
     * 取SD卡路径
     **/
    public static String getSDCardPath(Context context) {
        String sdDir;
        boolean sdCardExist = TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        String[] strs = app.getApplication().getPackageName().split("\\.");
        String pkgName = strs[strs.length-1];
        // 获取根目录
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().toString()+"/"+pkgName;
        } else {
            sdDir = context.getCacheDir().getPath()+"/"+pkgName;
        }
        return sdDir;
    }
    /**
     * 获得图片缓存目录
     **/
    public static String getImageCacheDirectory(Context context) {
        String dir = getSDCardPath(context) + "/Image";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }
    /**
     * 获得文件下载目录
     **/
    public static String getDownloadFileDirectory(Context context) {
        String dir = getSDCardPath(context) + "/File";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }

    /**
     * 获得群文件下载目录
     **/
    public static String getDownloadImGroupFileDirectory(Context context) {
        String dir = getSDCardPath(context) + "/File/groupfile";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }

    /**
     * 获得文件下载目录
     **/
    public static String getVideoFileDirectory(Context context) {
        String dir = getSDCardPath(context) + "/Video";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }

    /**
     * 获得相机拍照目录
     **/
    public static String getCameraDirectory(Context context) {
        String dir = getSDCardPath(context) + "/Camera";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }
    /**
     * 获得录音目录
     **/
    public static String getAudioDirectory(Context context) {
        String dir = getSDCardPath(context) + "/Audio";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }
    /**
     * 获取图片名称及保存路径
     *
     * @param isInsertToGallery 是否插入相册
     * @return
     */
    public static File getImageFile(boolean isInsertToGallery, Context context) {
        String date = new DateFormat().format("yyyyMMdd_hh_mm_ss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String path = null;
        if (isInsertToGallery) {
            path = "/sdcard/DCIM/Camera";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        } else {
            path = getImageCacheDirectory(context);
        }

        File file = new File(path + "/" + date);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    //文件重命名
    public static String getFileName(String filePath) {
        String name= System.currentTimeMillis()+"";
        try {
            int lastNum=filePath.lastIndexOf(".");
            if(lastNum!=-1){
                String last= filePath.substring(lastNum, filePath.length());
                name= System.currentTimeMillis()+last;
            }
        }catch (Exception e){

        }
        return name;
    }

    /**
     * 判断文件夹下是否有该名字的文件
     * @param dir 文件夹路径
     * @param fileName 文件名
     * @return
     */
    public static boolean hasFile(String dir, String fileName) {
        File file = new File(dir);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile() && f.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 删除文件
     */
    public static void deleteCacheFile(File file) {
        if (file.exists()) {
            String filename = file.getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".gif")) {
                file.delete();
            }
        }
    }
}
