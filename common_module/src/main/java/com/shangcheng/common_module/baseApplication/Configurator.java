package com.shangcheng.common_module.baseApplication;


import android.app.Application;

import com.google.gson.Gson;
import com.shangcheng.common_module.common.model.UserModel;
import com.shangcheng.common_module.utils.Des4;
import com.shangcheng.common_module.utils.LogUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;

import java.util.HashMap;

/**
 * application初始化全局常量
 *
 * @author Navy
 */
public class Configurator {
    private static final HashMap<String, Object> APP_CONFIGS = new HashMap<>();

    Configurator() {
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public final HashMap<String, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void Configure() {
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    public Configurator withDebug(boolean flag) {
        LogUtil.isDebug = flag;
        return this;
    }

    public void withUserInfo(UserModel userInfo) {
        if (userInfo != null) {
            String jsonString = new Gson().toJson(userInfo);
            jsonString = Des4.encryptDES(jsonString);
            APP_CONFIGS.put(ConfigType.USER_INFO.name(), jsonString);
        }
    }

    public UserModel getUserInfo() {
        String jsonInfo = (String) APP_CONFIGS.get(ConfigType.USER_INFO.name());
        UserModel userModel = new Gson().fromJson(jsonInfo, UserModel.class);
        return userModel;
    }

    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) APP_CONFIGS.get(key.name());
    }

    public final Configurator withWXAppId(String wxAppId) {
        APP_CONFIGS.put(ConfigType.WX_APP_ID.name(), wxAppId);
        return this;
    }
    public final Configurator withWXAppSecret(String wxSecret) {
        APP_CONFIGS.put(ConfigType.WX_APP_SECRET.name(), wxSecret);
        return this;
    }

    public final Configurator initDevShapeUtils() {
        DevShapeUtils.init((Application) APP_CONFIGS.get(ConfigType.APPLICATION_CONTEXT.name()));
        return this;
    }

}
