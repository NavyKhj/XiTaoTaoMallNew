package com.shangcheng.common_module.utils.web.webutil;

import android.os.Bundle;
import android.webkit.WebView;


import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.LogUtil;

import org.json.JSONObject;

public class JsGetMethod {

    public static JsCallback mJsCallback ;
    /**
     * 提供给Js的方法 跳转调查页面
     *
     * @return
     */
    public static void finished(WebView view) {
        LogUtil.d("JsGetMethod", "finished");
    }

    /**
     * 提供给Js的方法 视频页面
     *
     * @return
     */
    public static void playVideo(WebView view, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_VIDEO,bundle));
    }

    /**
     * 提供给Js的方法 显示图片
     *
     * @return
     */
    public static void displayPicture(WebView view, String index, String imageURL) {
        Bundle bundle = new Bundle();
        bundle.putString("index", index);
        bundle.putString("imageURL", imageURL);
        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_DISPLAY_PICTURE,bundle));
    }

    /**
     * 提供给Js的方法 扫一扫
     *
     * @return
     */
    public static void scanCode(WebView view, JSONObject name, JsCallback jsCallback) {
        mJsCallback = jsCallback;
        //需要传入的function能够在当前页面生命周期内多次使用,请在第一次apply前setPermanent(true)
        jsCallback.setPermanent(true);
        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_SCAN));
    }

    /**
     * 提供给Js的方法 定位
     *
     * @return
     */
    public static void getPosition(WebView view, JSONObject name, JsCallback jsCallback) {
        mJsCallback = jsCallback;
        //需要传入的function能够在当前页面生命周期内多次使用,请在第一次apply前setPermanent(true)
        jsCallback.setPermanent(true);
        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_LOCATION));
    }

    /**
     * 提供给Js的方法 微信分享
     *
     * @return
     */
    public static void shareWechat(WebView view, String url, String title, String desc) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putString("desc", desc);
        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.SHARE_WECHAT,bundle));
    }
}
