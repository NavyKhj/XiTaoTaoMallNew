package com.shangcheng.common_module.common.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息
 *
 * @author yangzm
 * @time 2018/4/26
 */
public class UserModel {

    /**
     * 静态用户信息
     */
    private static UserModel instance;

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    public static void setInstance(UserModel instance) {
        UserModel.instance = instance;
    }

    private String userId;
    private String id;
    private String level;
    private String levelMsg;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelMsg() {
        return levelMsg;
    }

    public void setLevelMsg(String levelMsg) {
        this.levelMsg = levelMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getSex_id() {
        return sex_id;
    }

    public void setSex_id(String sex_id) {
        this.sex_id = sex_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_verified() {
        return mobile_verified;
    }

    public void setMobile_verified(String mobile_verified) {
        this.mobile_verified = mobile_verified;
    }

    public boolean getName_verified() {
        return name_verified;
    }

    public void setName_verified(boolean name_verified) {
        this.name_verified = name_verified;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public Map<String, String> getmMap() {
        return mMap;
    }

    public void setmMap(Map<String, String> mMap) {
        this.mMap = mMap;
    }


    private String accessToken; //请求Token

    /**
     * 昵称
     */
    @SerializedName("userName")
    private String nickName;

    /**
     * 头像
     */
    private String favicon;
    /**
     * 性别id
     */
    private String sex_id;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    @SerializedName("phone")
    private String mobile;
    /**
     * 手机号是否验证0未验证1验证
     */
    private String mobile_verified;
    /**
     * 实名认证
     */
    @SerializedName("authentication")
    private boolean name_verified;
    /**
     * 银行卡
     */
    private String bankCard;

    /**
     * 城市
     */
    @SerializedName("addressCode")
    private String cityCode;
    @SerializedName("addressName")
    private String cityName;
    /**
     * 推荐人
     */
    @SerializedName("upId")
    private String recommender;
    @SerializedName("upUserId")
    private String recommender_id;
    @SerializedName("upPhone")
    private String recommender_phone;
    /**
     * 是否有支付密码
     */
    private boolean hasPayPassword;
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isHasPayPassword() {
        return hasPayPassword;
    }

    public void setHasPayPassword(boolean hasPayPassword) {
        this.hasPayPassword = hasPayPassword;
    }

    /* 本地缓存数据*/
    private Map<String, String> mMap = new HashMap<>();

    public boolean isLogin() {
        return (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(nickName));
    }

    public boolean isName_verified() {
        return name_verified;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRecommender_id() {
        return recommender_id;
    }

    public void setRecommender_id(String recommender_id) {
        this.recommender_id = recommender_id;
    }

    public String getRecommender_phone() {
        return recommender_phone;
    }

    public void setRecommender_phone(String recommender_phone) {
        this.recommender_phone = recommender_phone;
    }
}
