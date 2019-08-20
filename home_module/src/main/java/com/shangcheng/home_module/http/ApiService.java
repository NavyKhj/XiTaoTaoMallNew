package com.shangcheng.home_module.http;

import com.shangcheng.common_module.BuildConfig;
import com.shangcheng.common_module.common.model.BaseEntity;
import com.shangcheng.common_module.common.model.UserModel;
import com.shangcheng.common_module.http.HttpResponse;
import com.shangcheng.home_module.entity.GoodsBeanEntity;
import com.shangcheng.home_module.entity.HomeListProductBean;
import com.shangcheng.home_module.entity.ProductDetailBean;
import com.shangcheng.home_module.entity.SelectConditionBeanEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Navy
 */
public interface ApiService {
    /**
     * 首页获取区域展示商品
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/ishomequery")
    Observable<HttpResponse<HomeListProductBean>> getAreaProduct(@Body() Object body);

    /**
     * 首页hot列表
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/homequery")
    Observable<HttpResponse<GoodsBeanEntity>> getHotList(@Body() Object body);

    /**
     * 获取规格列表
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/specsquery")
    Observable<HttpResponse<SelectConditionBeanEntity>> getSelectRule(@Body() Object body);
    /**
     * 获取商品详情
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/details")
    Observable<HttpResponse<ProductDetailBean>> getDetail(@Body() Object body);
    /**
     * 获取商品详情
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_GOODS+"goods/checkgoodsstock")
    Observable<String> checkStock(@Body() Object body);
    /**
     * 获取用户信息
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_SECURE+"member/memberinfo")
    Observable<UserModel> getUserInfo(@Body() Object body);
    /**
     * 检查淘批购买权限
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_SECURE+"member/check-taopi-auth")
    Observable<String> checkTaopiAuth(@Body() Object body);
    /**
     * 检查库存
     * @param body 输入
     * @return 输出
     */
    @POST(BuildConfig.BASE_URL_FINANCE+"finance/balanceVerify")
    Observable<String> checkAccount(@Body() Object body);
}
