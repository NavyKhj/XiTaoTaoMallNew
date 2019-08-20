package com.shangcheng.common_module.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shangcheng.common_module.baseApplication.app;

/**
 * @author Navy
 */
public class NetUtil {
    /**
     * 检测网络连接
     *
     * @return true 有 false 无
     */
    public static boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return !(networkInfo == null || !networkInfo.isAvailable());
        }
        return false;
    }
}
