package com.shangcheng.common_module.http;

import com.shangcheng.common_module.BuildConfig;
import com.shangcheng.common_module.utils.T;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Navy
 */
public interface ApiService {
    /**
     * 微信登录
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/ishomequery")
    Observable<Object> getAreaProduct(@Body() Object body);
}
