package com.shangcheng.common_module.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.common.model.ImgEntity;
import com.shangcheng.common_module.utils.imageloader.ImageLoader;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 工具类
 *
 * @author Administrator
 * @time 2016/6/7
 */
public class CommonUtils {

    /**
     * App配置信息
     */
    public static final String PREF_CONFIG = "config_info_v4";
    public static final String CONFIG_KEY = "config_key_v4";

    /**
     * 本地更新信息
     */
    public static final String PREF_LOCAL_UPDATE = "local_info_v4";
    public static final String LOCAL_UPDATE_KEY = "local_key_v4";

    /**
     * 话题信息
     */
    public static final String PREF_TOPIC = "topic_info_v4";
    public static final String TOPIC_KEY = "topic_key_v4";

    /**
     * 发现首页模块顺序信息
     */
    public static final String PREF_DISCOVERY = "discovery_info_v4";
    public static final String DISCOVERY_KEY = "discovery_key_v4";

    /**
     * 用户信息
     */
    public static final String PREF_USER = "user_info_v4";
    public static final String USER_KEY = "user_key_v4";

    /**
     * 推送信息
     */
    public static final String PUSH_MESSAGE = "push_message_v4";
    public static final String PUSH_MESSAGE_NOTICE_ID = "push_message_notice_id_v4";

    /**
     * 设置启动通讯录
     */
    public static final String CONTACTS_OPEN = "contacts_open_v4";
    public static final String CONTACTS_KEY = "is_open_v4";

    /**
     * 设置签到
     */
    public static final String SING_IN_NOTIFY_OPEN = "sing_in_notify_open_v4";
    public static final String SING_IN_NOTIFY_KEY = "sing_in_notify_is_open_v4";

    /**
     * 设置签到时间
     */
    public static final String SING_IN_NOTIFY_TIME = "sing_in_notify_time_v4";
    public static final String SING_IN_NOTIFY_TIME_KEY = "sing_in_notify_time_key_v4";

    /**
     * 设置详情WebView字体
     */
    public static final String WEB_VIEW_FONT_SIZE = "web_view_font_size_v4";
    public static final String WEB_VIEW_FONT_SIZE_KEY = "web_view_font_size_key_v4";

    /**
     * 设置详情朗读语速和模式
     */
    public static final String PREF_READ = "voice_read_v4";
    public static final String READ_SPEED_KEY = "read_speed_key_v4";
    public static final String READ_MODE_KEY = "read_mode_key_v4";
    /**
     * 活动是否已读
     */
    public static final String ACTIVITY_READ = "activity_read_v4";
    /**
     * 是否开启指纹登录
     */
    public static final String FINGERPRINT_LOGIN = "fingerprint_login_v4";

    /**
     * 版本号
     */
    public static final String PREF_VERSION_CODE = "version_code_v4";
    public static final String VERSION_CODE_KEY = "version_code_key_v4";

    /**
     * 测试使用
     */
    public static final String TEST_KEY = "test_key";

    /*是否正在下载APK*/
    public static boolean isDownLoading = false;

    /**
     * 设置本地更新信息
     */
    public static void setLocalInfo(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_LOCAL_UPDATE, Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    /**
     * 获得本地更新信息
     */
    public static String getLocalInfo(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_LOCAL_UPDATE, Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空本地更新信息
     */
    public static void cleanLocalInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_LOCAL_UPDATE, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    /**
     * 设置话题信息
     */
    public static void setTopicInfo(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_TOPIC,
                Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    /**
     * 获得话题信息
     */
    public static String getTopicInfo(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_TOPIC,
                Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空话题信息
     */
    public static void cleanTopicInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_TOPIC,
                Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }


    /**
     * 设置首页主模块顺序
     */
    public static void setDiscoveryModule(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_DISCOVERY,
                Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    /**
     * 发现首页主模块顺序
     */
    public static String getDiscoveryModule(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_DISCOVERY,
                Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空主模块顺序信息
     */
    public static void cleanDiscoveryModule(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_DISCOVERY,
                Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    /**
     * 设置配置信息
     */
    public static void setConfigInfo(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_CONFIG,
                Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    /**
     * 获得配置信息
     */
    public static String getConfigInfo(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_CONFIG,
                Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空配置信息
     */
    public static void cleanConfigInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_CONFIG,
                Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    /**
     * 设置用户信息
     */
    public static void setUserInfo(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_USER,
                Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    /**
     * 获得用户信息
     */
    public static String getUserInfo(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_USER,
                Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空用户信息
     */
    public static void cleanUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_USER,
                Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }


    /**
     * 设置推送信息
     */
    public static void setPushMessage(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PUSH_MESSAGE,
                Context.MODE_PRIVATE);
        preferences.edit().putString(key, (String) value).apply();
    }

    /**
     * 获得推送信息
     */
    public static String getPushMessage(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PUSH_MESSAGE,
                Context.MODE_PRIVATE);
        String value = preferences.getString(key, defValue);
        return value;
    }

    /**
     * 清空单条推送信息
     */
    public static void cleanPushMessage(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PUSH_MESSAGE,
                Context.MODE_PRIVATE);
        preferences.edit().putString(key, "").apply();
    }

    /**
     * 设置通讯录开启状态
     */
    public static void setContactsOpen(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.CONTACTS_OPEN,
                Context.MODE_PRIVATE);
        preferences.edit().putString(CommonUtils.CONTACTS_KEY, (String) value).apply();

    }

    /**
     * 获取通讯录开启状态
     */
    public static String getContactsOpen(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.CONTACTS_OPEN,
                Context.MODE_PRIVATE);
        String value = preferences.getString(CONTACTS_KEY, "");
        return value;

    }

    /**
     * 设置签到提醒
     */
    public static void setSingInNotifyOpen(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .SING_IN_NOTIFY_OPEN, Context.MODE_PRIVATE);
        preferences.edit().putString(CommonUtils.SING_IN_NOTIFY_KEY, (String) value).apply();

    }

    /**
     * 获取签到提醒
     */
    public static String getSingInNotifyOpen(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .SING_IN_NOTIFY_OPEN, Context.MODE_PRIVATE);
        String value = preferences.getString(SING_IN_NOTIFY_KEY, "");
        return value;

    }

    /**
     * 设置签到提醒
     */
    public static void setSingInNotifyTime(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .SING_IN_NOTIFY_TIME, Context.MODE_PRIVATE);
        preferences.edit().putString(CommonUtils.SING_IN_NOTIFY_TIME_KEY, (String) value).apply();

    }

    /**
     * 获取签到提醒
     */
    public static String getSingInNotifyTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .SING_IN_NOTIFY_TIME, Context.MODE_PRIVATE);
        String value = preferences.getString(SING_IN_NOTIFY_TIME_KEY, "");
        return value;

    }

    /**
     * 设置详情WebView字体
     */
    public static void setFontSize(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .WEB_VIEW_FONT_SIZE, Context.MODE_PRIVATE);
        preferences.edit().putInt(CommonUtils.WEB_VIEW_FONT_SIZE_KEY, (int) value).apply();

    }

    /**
     * 获取详情WebView字体
     */
    public static int getFontSize(Context context, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .WEB_VIEW_FONT_SIZE, Context.MODE_PRIVATE);
        int value = preferences.getInt(WEB_VIEW_FONT_SIZE_KEY, defValue);
        return value;

    }

    /**
     * 设置配置信息
     */
    public static void setVersionCode(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_VERSION_CODE, Context.MODE_PRIVATE);
        if (value instanceof Integer) {
            preferences.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    public static int getVersionCode(Context context, String key, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils.PREF_VERSION_CODE, Context.MODE_PRIVATE);
        int value = preferences.getInt(key, defValue);
        return value;
    }

    /**
     * 设置详情朗读速度
     */
    public static void setReadSpeed(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_READ, Context.MODE_PRIVATE);
        preferences.edit().putInt(CommonUtils.READ_SPEED_KEY, (int) value).apply();

    }

    /**
     * 获取详情朗读速度
     */
    public static int getReadSpeed(Context context, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_READ, Context.MODE_PRIVATE);
        int value = preferences.getInt(READ_SPEED_KEY, defValue);
        return value;

    }

    /**
     * 设置详情朗读模式
     */
    public static void setReadMode(Context context, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_READ, Context.MODE_PRIVATE);
        preferences.edit().putInt(CommonUtils.READ_MODE_KEY, (int) value).apply();

    }

    /**
     * 获取详情朗读模式
     */
    public static int getReadMode(Context context, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .PREF_READ, Context.MODE_PRIVATE);
        int value = preferences.getInt(READ_MODE_KEY, defValue);
        return value;

    }

    /**
     * 设置活动是否看过
     */
    public static void setActivityRead(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .ACTIVITY_READ, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();

    }

    /**
     * 获取活动是否看过
     */
    public static String getActivityRead(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .ACTIVITY_READ, Context.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        return value;

    }

    /**
     * 设置是否开启指纹登录
     */
    public static void setFingerprintLogin(Context context, String key, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .FINGERPRINT_LOGIN, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();

    }

    /**
     * 获取是否开启指纹登录
     */
    public static Boolean getFingerprintLogin(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(CommonUtils
                .FINGERPRINT_LOGIN, Context.MODE_PRIVATE);
        Boolean value = preferences.getBoolean(key, false);
        return value;

    }

    /**
     * 系统裁剪图片
     *
     * @param file
     * @param savePath
     * @param aspectX
     * @param aspectY
     */
    public static Intent cropImageUri(Context context, File file, Uri savePath, int aspectX, int
            aspectY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(FileUtils.restrictedAccess(context, intent, file), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, savePath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;

    }

    /**
     * 打开文件
     *
     * @param context
     * @param file    打开文件
     * @param type    不同类型
     * @return
     */
    public static Intent openFile(Context context, File file, String type) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);//intent的默认category值即系Intent.CATEGORY_DEFAULT
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(FileUtils.restrictedAccess(context, intent, file), type);
        return intent;
    }

    /**
     * 本地图片显示路径转换
     */
    public static String changeSDCardPath(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }
        return path;
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断密码格式(纯数字或者纯字母或者纯特殊字符 、大于10字符)
     *
     * @param value
     * @return true 密码强度-弱  false 密码强度-强
     */
    public static boolean matcherPassword(String value) {
        //密码强度
        boolean flag = false;
        //是否单一种类字符
        //int index = 0;
        //String regex1 = "^(?=.*[A-Z]).*$";
        //String regex2 = "^(?=.*[a-z]).*";
        //String regex3 = "^(?=.*[0-9]).*$";
        //String regex4 = "^(?=.*[\\u4e00-\\u9fa5\\\\.\\\\*\\\\)\\\\
        // (\\\\+\\\\$\\\\[\\\\?\\\\\\\\\\\\^\\\\{\\\\|\\\\]\\\\}%%%@\\'\\\",。‘、-【】·！_——=:;
        // ；<>《》‘’“”!#~€£¥•]).*$";
        String regex5 = "^[0-9]*$";
        //boolean isDX = testRegex(regex1, value);  //是否存在大写
        //boolean isXX = testRegex(regex2, value);  //是否存在小写
        //boolean isSZ = testRegex(regex3, value);  //是否存在数字
        //boolean isBD = testRegex(regex4, value);  //是否存在标点
        boolean isOnlyBD = testRegex(regex5, value);  //只能输入数字
        //if(isDX)index++;
        //if(isXX)index++;
        //if(isSZ)index++;
        //if(isBD)index++;
        /*if(index>1){
            flag = false;
        }else if(index==0){ //字符不符合规范
            flag = true;
        }else if(isOnlyBD&&index==1){//只有数字*/
        if (isOnlyBD) {//只有数字
            int length = value.length();
            char[] p = value.toCharArray();
            boolean isSerialUp = true;//默认为连续—升序
            boolean isSerialDown = true;//默认为连续—降序
            int currA = p[0];
            for (int i = 1; i < length; i++) {
                if (currA + 1 == p[i] && isSerialUp) {
                    currA = p[i];
                    isSerialDown = false;
                } else if (currA - 1 == p[i] && isSerialDown) {
                    currA = p[i];
                    isSerialUp = false;
                } else {
                    isSerialUp = false;
                    isSerialDown = false;
                    break;
                }
            }
            flag = isSerialUp || isSerialDown;

            //全部为同一字符 —重复n次
            String regex = value.substring(0, 1) + "{" + value.length() + "}";
            if (value.matches(regex)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 是否匹配正则
     *
     * @param regex
     * @param inputValue
     * @return
     */
    public static boolean testRegex(String regex, String inputValue) {
        return Pattern.compile(regex).matcher(inputValue).matches();
    }

    /**
     * 获取SDCard上目录的路径
     *
     * @param folder
     * @return
     */
    public static String getExtraPath(String folder) {
        String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + folder;
        File file = new File(storagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return storagePath;
    }

    /**
     * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils
                .OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static Bitmap getVideoThumbnail(String videoPath, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        return bitmap;
    }

    /**
     * List<string> 转换为 字符串
     *
     * @param list
     * @param split
     * @return
     */
    public static String listToString(List<String> list, String split) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(split);
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * List<string> 转换为 字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            result.append(numberToLetter(string));
        }
        return result.toString();
    }

    /**
     * 数字转字母
     * 1-26转换为A-Z
     *
     * @param input 从0开始的
     */
    public static String numberToLetter(String input) {
        if (!TextUtils.isDigitsOnly(input)) {
            return input;
        }
        int num = TypeConvertUtils.getInstance().parseInt(input);
        if (num < 0) {
            return null;
        }
        String letter = "";
        //num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);

        return letter;
    }

    /**
     * 字符串 转换为 List<string>
     *
     * @param array
     * @param split
     * @return
     */
    public static List<String> stringToList(String array, String split) {
        String str[] = array.split(split);
        return Arrays.asList(str);
    }

    /**
     * 改变时间格式 (将秒数转为时分秒)
     *
     * @param second 秒
     */
    public static String changeTimeFormat(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        String hh = h <= 9 ? "0" + h : "" + h;
        String dd = d <= 9 ? "0" + d : "" + d;
        String ss = s <= 9 ? "0" + s : "" + s;
        //return hh + ":" + dd + ":" + ss + "";
        return dd + ":" + ss + "";
    }

    /**
     * 设置未读数
     *
     * @param numberText   TextView控件
     * @param unReadNumber 未读数
     *//*
    public static void setNoReadNumber(TextView numberText, String unReadNumber, Context context) {
        if (!TextUtils.isEmpty(unReadNumber) && !TextUtils.equals("0", unReadNumber)) {
            numberText.setVisibility(View.VISIBLE);
            numberText.setText(unReadNumber);
            //设置View不同状态相关
            ViewUtil viewUtil = new ViewUtil(context);
            if (Integer.valueOf(unReadNumber) >= 10) {
                viewUtil.setBackgroundOfVersion(numberText, viewUtil.setShapeDrawable(-1, -1,
                        ViewUtil.ROUND_LARGE, ColorUtil.getAttrColor(R.attr.transparent_color,
                                context), -1));
                if (Integer.valueOf(unReadNumber) > 99) {
                    numberText.setText("99+");
                }
            } else if (Integer.valueOf(unReadNumber) < 10 && Integer.valueOf(unReadNumber) > 0) {
                viewUtil.setBackgroundOfVersion(numberText, viewUtil.setShapeDrawable(-1, -1,
                        -1, ColorUtil.getAttrColor(R.attr.transparent_color, context),
                        GradientDrawable.OVAL));
            } else {
                numberText.setVisibility(View.INVISIBLE);
            }
        } else {
            numberText.setVisibility(View.INVISIBLE);
        }
    }

    public static String setCommentNum(String num) {
        if (TypeConvertUtils.getInstance().parseInt(num) > 99) {
            return "99+";
        } else {
            return num;
        }

    }*/

    /**
     * 获取应用的Uid, 用于验证服务是否启动
     *
     * @param context 上下文
     * @return uid
     */
    public static int getUid(Context context) {
        if (context == null) {
            return -1;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);

        if (manager != null) {
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            if (infos != null && !infos.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo processInfo : infos) {
                    if (processInfo.pid == pid) {
                        return processInfo.uid;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 是否长图
     *
     * @param width
     * @param height
     * @param MAX_LENGTH
     * @param MIN_LENGTH
     * @return
     */
    public static boolean isLongImage(String width, String height, float
            MAX_LENGTH, float MIN_LENGTH) {
        boolean flag;
        float mw = TypeConvertUtils.getInstance().parseFloat(width);
        float mh = TypeConvertUtils.getInstance().parseFloat(height);
        int h;
        int w;
        int maxSize = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * MAX_LENGTH);
        int minSize = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * MIN_LENGTH);
        if (mw > mh) {
            //计算比例
            BigDecimal proportion = new BigDecimal(maxSize).divide(new BigDecimal(mw), 3,
                    BigDecimal.ROUND_HALF_UP);
            //按照比例换算图片高度
            h = new BigDecimal(mh).multiply(proportion).intValue();
            if (h < minSize) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            //计算比例
            BigDecimal proportion = new BigDecimal(maxSize).divide(new BigDecimal(mh), 3,
                    BigDecimal.ROUND_HALF_UP);
            //按照比例换算图片宽度
            w = new BigDecimal(mw).multiply(proportion).intValue();
            if (w < minSize) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 搜索关键字全部变色
     *
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 搜索关键字全部变色
     *
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString matcherSearchTextForIndex(int color, String text, String
            keyword, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(spannableString.subSequence(startIndex, endIndex));
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(color), start + startIndex, end +
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    /**
     * 转换数量
     *
     * @param number 数值
     * @param unit   单位
     * @return
     */
    public static String convertNumber(String number, String unit) {
        String value;
        DecimalFormat df = new DecimalFormat("######0.0");
        Double i;
        try {
            i = TypeConvertUtils.getInstance().parseDouble(number);
        } catch (Exception e) {
            i = 0.0;
        }
        if (i > 99999999) {
            value = df.format(i / 100000000) + "亿" + unit;
        } else if (i > 9999) {
            value = df.format(i / 10000) + "万" + unit;
        } else {
            int n;
            try {
                n = TypeConvertUtils.getInstance().parseInt(number);
            } catch (Exception e) {
                n = 0;
            }
            value = n + unit;
        }
        return value;
    }

    /**
     * 改变缩略图地址
     *
     * @param originalUrl
     * @param width
     * @param height
     * @return
     */

    /*模块尺寸-宽/高
     *
     * 0. 工作信息 3:2 1125*750
     * 1. 学习推荐banner 3:1 970*324
     * 2. 视频轮播 16:9 1054*592
     * 3. 视频列表封面 16:9 1125*750
     * 4. 资料轮播 3:1 1054*352
     * 5. 资料列表封面 16:9 510*286
     * 6. 党员论坛列表封面 3:2 324*216
     * 7. 党员视角列表封面 方图 330*330
     * 8. 党员视角列表封面 长图 390*450
     * 9. 党员视角列表封面 宽图 450*390
     *
     * */
    public static final float[] thumbnailWidth = {1125, 970, 1054, 1125, 1054, 510, 324, 330,
            390, 450};
    public static final float[] thumbnailHeight = {750, 324, 592, 750, 352, 286, 216, 330, 450,
            390};

    public static String changeThumbnailAddress(String originalUrl, float width, float height) {
        int addWidth = (int) width;
        int addHeight = (int) height;
        StringBuffer url = new StringBuffer();
        String type = "thumb";
        url.append(originalUrl);
        url.append("&");
        url.append(type);
        url.append("=");
        url.append(addWidth + "");
        url.append("x");
        url.append(addHeight + "");
        /*source文件夹为文件服务器默认图片逻辑，不拼接大小*/
        if (!TextUtils.isEmpty(originalUrl)
                && originalUrl.contains("/source/")) {
            return originalUrl;
        }
        return url.toString();
    }

    public static String changeThumbnailAddress(String originalUrl, float width, float height,String a) {
        return changeThumbnailAddress(originalUrl, width * ScreenMeasureUtil.getInstance().getWidthPixels(), height * ScreenMeasureUtil.getInstance()
                .getWidthPixels());
    }


    /**
     * 给View设置按压色
     *
     * @param mContext 上下文
     * @param view     需要设背景的view
     * @param radius   圆角
     */
    public static void setBg(Context mContext, View view, int radius) {
        DevShapeUtils.selectorBackground(
                DevShapeUtils.shape(DevShape.RECTANGLE).solid(R.color.normal_bg_color_shallow).radius(radius).build(),
                DevShapeUtils.shape(DevShape.RECTANGLE).solid(R.color.normal_content_background_color).radius(radius).build())
                .into(view);
    }

    /**
     * 给View设置按压色
     *
     * @param mContext 上下文
     * @param view     需要设背景的view
     */
    public static void setBg(Context mContext, View view) {
        setBg(mContext, view, 0);
    }

    /**
     * 设置封面图(3:2比例)
     *
     * @param context
     * @param view
     * @param list
     * @param resId
     */
    public static void setCoverImage(Context context, ImageView view,
                                     List<ImgEntity> list, int resId) {
        ImageLoaderUtil imageLoaderUtil = new ImageLoaderUtil();
        if (list != null && list.size() > 0) {
            ImageLoader imageLoader = new ImageLoader.Builder()
                    .placeHolder(resId)
                    .errorHolder(resId)
                    .url(changeThumbnailAddress(list.get(0).getUrl(), 0.36f, 0.24f,null))
                    .imgView(view)
                    .scaleType(1)
                    .build();
            imageLoaderUtil.loadImage(context, imageLoader);
        } else {
            view.setImageResource(resId);
        }
    }

//    /**
//     * 设置默认占位图
//     *
//     * @param systemId 系统ID
//     */
//    public static int setDefaultPlaceHolder(String systemId) {
//        int[] images = new int[]{R.mipmap.bc_ic_default_placeholder_0, R.mipmap
//                .bc_ic_default_placeholder_1, R.mipmap.bc_ic_default_placeholder_2,
//                R.mipmap.bc_ic_default_placeholder_3, R.mipmap.bc_ic_default_placeholder_4, R
//                .mipmap.bc_ic_default_placeholder_5,
//                R.mipmap.bc_ic_default_placeholder_6, R.mipmap.bc_ic_default_placeholder_7};
//        int max = 8;
//        int index;
//        if (TextUtils.isEmpty(systemId)) {
//            index = 0;
//        } else {
//            index = TypeConvertUtils.getInstance().parseInt(systemId) % max;
//        }
//        return images[index];
//    }

    //-------------------------------------------
    // 首页的BottomNavigationBar-------------------------------------------

    /**
     * This method can be extended to get all android attributes color, string, dimension ...etc
     *
     * @param context          used to fetch android attribute
     * @param androidAttribute attribute codes like R.attr.colorAccent
     * @return in this case color of android attribute
     */
    public static int fetchContextColor(Context context, int androidAttribute) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttribute});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    /**
     * @param context used to get system services
     * @return screenWidth in pixels
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * 图文混排的Html字符串
     *
     * @param contentHtml
     * @return
     */
    public static String convertContenHtml(String contentHtml) {
        if (TextUtils.isEmpty(contentHtml)) {
            return "";
        }
        return "<!DOCTYPE html><html lang=\"zh-cn\"><head><meta charset=\"UTF-8\"></meta>" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1," +
                "maximum-scale=1," +
                "minimum-scale=1,user-scalable=no\"></meta><style>*{margin:0}" +
                "body{font-size:16px;line-height:170%;}div{padding:20px;}iframe{display:block;" +
                "width:100%; " +
                "height:%fpx; h1,h2,h3,h4,h5,h6:padding:10px;}p{margin: 10px 0}div " +
                "p:first-child{margin-top:0}img{display:block;width:100%;height:auto;}" +
                "</style></head><body><div style=\"text-align: justify;\" id=\"webview-content\">" + contentHtml +
                "</div><script type=\"text/javascript\">var node =" +
                " document.querySelectorAll(\"#webview-content style\");if(node.length > 0){node" +
                ".forEach(function(i, v){ i.remove()})}</script></body></html>"
                ;
    }

    /**
     * 中文utf8
     *
     * @param s
     * @return
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    b = Character.toString(c).getBytes("utf-8");
                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        //转换为unsigned integer  无符号integer
                    /*if (k < 0)
                        k += 256;*/
                        k = k < 0 ? k + 256 : k;
                        //返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
                        //该值以十六进制（base16）转换为ASCII数字的字符串
                        sb.append(Integer.toHexString(k).toUpperCase());

                        // url转置形式
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 插入图片到相册及更新
     *
     * @param bmp
     * @param mContext
     * @return 文件地址
     */
    public static Uri getLocalBitmapUri(Bitmap bmp, Context mContext) {
        Uri bmpUri = null;
        String path = FileUtils.getExtraPath("DCIM");
        // 首先保存图片
        File appDir = new File(path, "Camera");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                bmpUri = Uri.fromFile(file);
                return bmpUri;
            }
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            bmpUri = Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            T.show("下载或分享图片失败");
            e.printStackTrace();
        } catch (IOException e) {
            T.show("下载或分享图片失败");
            e.printStackTrace();
        } catch (Exception e) {
            T.show("下载或分享图片失败");
            e.printStackTrace();
        }

        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        return bmpUri;
    }

    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl, Context context) {
        String path = null;
        FutureTarget<File> future = Glide.with(context)
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 复制数据
     *
     * @param oldPath
     * @param newPath
     */
    public static void copyFile(String oldPath, final String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param imgCode
     * @param mContext
     */
    public static void createQRCode(final String content, final ImageView imgCode, final Context mContext) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                QRCodeEncoder.HINTS.clear();
                QRCodeEncoder.HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
                QRCodeEncoder.HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                QRCodeEncoder.HINTS.put(EncodeHintType.MARGIN, 0);
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(mContext, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgCode.setImageBitmap(bitmap);
                } else {
                    T.showShort("生成中文二维码失败");
                }
            }
        }.execute();
    }

    /**
     * 获取url对应的域名
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        String result = "";
        int j = 0, startIndex = 0, endIndex = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                j++;
                if (j == 2) {
                    startIndex = i;
                } else if (j == 3) {
                    endIndex = i;
                }
            }
        }
        result = url.substring(startIndex + 1, endIndex == 0 ? startIndex + 1 : endIndex);
        return result;
    }


    /**************************************  城市综治  *************************************/
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
