package com.shangcheng.common_module.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liqi.toast.MyToast;
import com.liqi.toast.Toast;
import com.liqi.toast.ToastTime;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.baseApplication.app;

/**
 * Toast统一管理类
 */
public class T {
    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        showShort(message, true);
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     * @param showFlag true 显示
     */
    public static void showShort(String message, boolean showFlag) {
        if (!showFlag) {
            return;
        }
        showCustomToast(message, ToastTime.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(String message) {
        showCustomToast(message, ToastTime.LENGTH_LONG);

    }

    private static TextView tvMessage;

    /**
     * 自定义toast底部不偏距短时间显示
     */
    public static void show(String message) {
        showCustomToast(message, ToastTime.LENGTH_SHORT);
    }

    private static void showCustomToast(String message, ToastTime toastTime) {
        if (toast == null && tvMessage == null) {
            LayoutInflater inflate = (LayoutInflater)
                    app.getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflate.inflate(R.layout.basecommon_item_custom_toast, null);
            tvMessage = (TextView) view.findViewById(R.id.message);
            tvMessage.setText(message);
            toast = Toast.putCenterToast(app.getApplication(), view, toastTime, 255, Gravity.CENTER, 0, 0);
        } else {
            tvMessage.setText(message);
        }
        toast.show();
    }
}

