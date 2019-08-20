package com.shangcheng.common_module.http;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;

import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.common.model.UserModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Navy
 */
public class MainInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        try {
            Request original = chain.request();
            Request.Builder requestBuilder = null;
            requestBuilder = original.newBuilder()
                    .header("version", app.getApplication().getApplicationContext().getPackageManager().getPackageInfo(app.getApplication().getPackageName(), 0).versionName)
                    .header("client", "Android");
            UserModel userModel = app.init().getUserInfo();
            if (userModel != null && !TextUtils.isEmpty(userModel.getAccessToken())) {
                requestBuilder.header("token", userModel.getAccessToken());
                Log.e("传入token:", userModel.getAccessToken());
            }
            String UA = "version:" + app.getApplication().getPackageManager().getPackageInfo(
                    app.getApplication().getPackageName(), 0).versionName +
                    "/" +
                    "client:Android";
            requestBuilder.removeHeader("User-Agent")
                    .addHeader("User-Agent", UA);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
