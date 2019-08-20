package com.shangcheng.xitaotao.http;

import com.shangcheng.common_module.BuildConfig;
import com.shangcheng.common_module.common.model.UserModel;
import com.shangcheng.common_module.http.HttpResponse;
import com.shangcheng.xitaotao.entity.HomeListProductBean;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author Navy
 */
public interface ApiService {
    @POST(BuildConfig.BASE_URL_SECURE+"member/verifycode-login")
    Observable<JSONObject> loginRegister(@Body() Object body);
    @POST(BuildConfig.BASE_URL_SECURE+"member/registermessage")
    Observable<String> sendCode(@Body() Object body);
    /**
     * 获取商品详情
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_SECURE+"member/memberinfo")
    Observable<UserModel> getUserInfo(@Body() Object body);
    @POST(BuildConfig.BASE_URL_SECURE+"member/remove-verifycode")
    Observable<String> removeVerifycode(@Body() Object body);
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token?")
    Observable<String> getAccessToken(@Body() Object body);
}
