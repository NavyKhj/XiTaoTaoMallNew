/**
 * Summary: 异步回调页面JS函数管理对象
 * Version 1.0
 * Date: 13-11-26
 * Time: 下午7:55
 * Copyright: Copyright (c) 2013
 */

package com.shangcheng.common_module.utils.web.webutil;

import android.webkit.WebView;

import com.shangcheng.common_module.utils.LogUtil;


public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:%s.callback(%d, %d %s);";
    private int mIndex;
    private WebView mWebView;
    private int mIsPermanent;
    private String mInjectedName;

    public JsCallback(WebView view, String injectedName, int index) {
        mWebView = view;
        mInjectedName = injectedName;
        mIndex = index;
    }

    public void apply(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                sb.append("\'");
            }
            sb.append(String.valueOf(arg));
            if (isStrArg) {
                sb.append("\'");
            }
        }
        String execJs = String.format(CALLBACK_JS_FORMAT, mInjectedName, mIndex, mIsPermanent, sb.toString());
        LogUtil.d("JsCallBack", execJs);
        mWebView.loadUrl(execJs);
    }

    public void setPermanent(boolean value) {
        mIsPermanent = value ? 1 : 0;
    }
}