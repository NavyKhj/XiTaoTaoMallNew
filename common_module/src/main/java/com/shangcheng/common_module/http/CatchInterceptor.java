package com.shangcheng.common_module.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.NetUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class description
 * 网络缓存拦截器
 *
 * @author WJ
 * @version 1.0, 2018-1-22
 */

public class CatchInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetUtil.checkConnection()) {
            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)//无网读缓存
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetUtil.checkConnection()) {
            int maxAge = 60;
            // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为1周
            int maxStale = 60 * 60 * 24 * 7;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
