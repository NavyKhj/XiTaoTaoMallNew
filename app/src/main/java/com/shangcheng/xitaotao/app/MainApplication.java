package com.shangcheng.xitaotao.app;

import android.app.Application;

import com.shangcheng.common_module.baseApplication.BaseApplication;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.xitaotao.BuildConfig;

/**
 * @author Navy
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        app.init(this)
                .withDebug(BuildConfig.DEBUG)
                .initDevShapeUtils()
                .Configure();
    }
}
