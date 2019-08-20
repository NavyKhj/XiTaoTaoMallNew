package com.shangcheng.common_module.http;

import com.shangcheng.common_module.BuildConfig;
import com.shangcheng.common_module.baseApplication.ConfigType;
import com.shangcheng.common_module.baseApplication.app;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class description
 * Retrofit的封装类
 *
 * @author WJ
 * @version 1.0, 2018-1-16
 */

public class RetrofitClient {

    /**
     * 设置连接超时的值
     */
    private static final int DEFAULT_TIMEOUT = 20;

    /**
     * 声明RetrofitClient对象
     */
    private volatile static RetrofitClient retrofitClient;

    /**
     * RetrofitClient初始化
     */
    private RetrofitClient() {
        setRetrofitClient();
    }

    /**
     * 内部retrofit
     */
    private Retrofit retrofit;

    /**
     * 使用单例模式
     */
    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (retrofitClient == null) {
                    retrofitClient = new RetrofitClient();
                }
            }
        }
        return retrofitClient;
    }

    /**
     * 设置Okhttp
     *
     * @return OkHttpClient
     */
    private OkHttpClient genericClient() {
//        CustomTrust customTrust = new CustomTrust();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /*
         *HttpLoggingInterceptor1 是一个拦截器，用于输出网络请求和结果的 Log，
         *可以配置 level 为 BASIC / HEADERS / BODY，都很好理解，
         *对应的是原来 retrofit 的 set log level 方法
         *DEBUG模式下打印
         */
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        /*
         *设置okhttp拦截器，这样做的好处是可以为你的每一个
         *retrofit2的网络请求都增加相同的head头信息，而不用每一个请求都写头信息
         */
        builder.addInterceptor(new CatchInterceptor())//设置请求缓存拦截
                .addInterceptor(new MainInterceptor())
                .addNetworkInterceptor(new CatchInterceptor());//设置网络缓存拦截器
        return builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS) //设置连接超时
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置从主机读信息超时
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置写信息超时
                .retryOnConnectionFailure(true)//错误重连
                .build();
    }

    /**
     * 配置Retrofit
     */
    private void setRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .client(genericClient())
                //如果网络访问返回的是json字符串，使用gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                //此处顺序不能和上面对调，否则不能同时兼容普通字符串和Json格式字符串
                //添加Rxjava2支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //基本网络地址
                .baseUrl("http://192.168.20.233:10200/")
                .build();
    }

    /**
     * 返回HttpService的实例
     *
     * @param service T
     * @param <T>     T
     * @return T
     */
    public <T> T creat(final Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 缓存
     *
     * @param tClass rxcahe实例
     * @param <T>    返回类型
     * @return return
     */
    /*public synchronized <T> T getCache(final Class<T> tClass) {
        return new RxCache.Builder()
                .persistence(Objects.requireNonNull(app.getApplication().getExternalCacheDir()), new GsonSpeaker())
                .using(tClass);
    }*/
}
