package com.shangcheng.common_module.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.ImageFactory;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.web.webutil.InjectedChromeClient;
import com.shangcheng.common_module.utils.web.webutil.JsGetMethod;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 进度条WebView 中信使用带webview缓存
 * 可跳转微信支付
 *
 * @author yangzm
 * @time 2017/4/20
 */

public class ProgressWebViewCache extends WebView {

    private Context mContext;
    private ProgressBar mProgressBar; //进度条
    private boolean isAnimStart; //动画标记
    private int  currentProgress; //当前加载进度
    private boolean isShow=true ; // 是否显示进度条
    private OnProgressListener mOnProgressListener;
    private OnReceivedListener mOnReceivedListener;

    public interface OnProgressListener {
         void onStartProgress();
         void onStopProgress();

    }
    public interface OnReceivedListener {
        void onReceivedTitle(String title);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.mOnProgressListener = onProgressListener;
    }

    public void setOnReceivedListener(OnReceivedListener onReceivedListener) {
        this.mOnReceivedListener = onReceivedListener;
    }

    public ProgressWebViewCache(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ProgressWebViewCache(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ProgressWebViewCache(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //设置横向进度条
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setVisibility(View.GONE);
        //设置进度条宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 6);
        mProgressBar.setLayoutParams(params);
        //设置进度条样式
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.web_progress_bar);
        mProgressBar.setProgressDrawable(drawable);
        //添加进度条
        addView(mProgressBar);
        //webView设置
        initWebSettings();
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        setWebViewClient(new CustomWebViewClient(mContext));
        //WebChromeClient是一个事件接口，用于响应那些改变浏览器中装饰元素的事件包括JavaScript警告信息、网页图标、状态条加载、网页标题刷新等
        setWebChromeClient(new CustomChromeClient("jsBridge", JsGetMethod.class));
        //WebView 调试
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }
    }
    /**
     * 初始化WebView的配置
     */
    private void initWebSettings() {
        WebSettings wSet = getSettings();
        //无缓存
        wSet.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        wSet.setCacheMode(WebSettings.LOAD_DEFAULT);
        //手动开启DOM Storage
        wSet.setDomStorageEnabled(true);
        //允许JavaScript
        wSet.setJavaScriptEnabled(true);
        //默认编码utf-8
        wSet.setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
        wSet.setSupportZoom(true);
        wSet.setBuiltInZoomControls(true); //支持手势缩放
        wSet.setDisplayZoomControls(false); //是否显示缩放按钮
        //扩大比例的缩放
        wSet.setUseWideViewPort(true);
        //设置字号
        wSet.setTextZoom(100);
        //自适应屏幕
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //UserAgent (格式：tfkj@zbgz/version/platform)
        try {
            String userAgent = wSet.getUserAgentString();
            wSet.setUserAgentString(userAgent+"/"+"tfkj@zbgz"+"/"+mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode
                    +"/"+"android");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        wSet.setLoadWithOverviewMode(true);
        //https加载http需要支持MIXED_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void setProgressBarStatus(boolean isShow){
        this.isShow = isShow;
    }
    /**
     * WebView 滚动变化
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 重写WebChromeClient获取加载进度
     */
    public class CustomChromeClient extends InjectedChromeClient {

        public CustomChromeClient(String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 处理Alert事件
            if (message != null) {
                //弹出对话框
                T.showShort(message);
            }
            //一定要cancel，否则会出现各种奇怪问题
            result.cancel();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //在这里我们重写onProgressChanged，刷新ProgressBar
            /*currentProgress = mProgressBar.getProgress();
            if (newProgress >= 100 && !isAnimStart) {
                // 防止调用多次动画
                isAnimStart = true;
                mProgressBar.setProgress(newProgress);
                // 开启属性动画让进度条平滑消失
                startDismissAnimation(mProgressBar.getProgress());
            } else {
                // 开启属性动画让进度条平滑递增
                startProgressAnimation(newProgress);
            }*/
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //重写onReceivedTitle，刷新标题栏
            if(mOnReceivedListener!=null) {
                mOnReceivedListener.onReceivedTitle(title);
            }
            super.onReceivedTitle(view, title);
        }
    }

    /**
     * WebView 请求回调
     */

    public class CustomWebViewClient extends WebViewClient {

        private String[] imageTypes = new String[] { ".jpg",".png", ".jpeg","webp"};
        private Context context;
        public CustomWebViewClient(Context context){
            this.context=context;
        }

        /**
         * 在开始加载网页时会回调
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(isShow){
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAlpha(1.0f);
            }
            if(mOnProgressListener!=null) {
                mOnProgressListener.onStartProgress();
            }
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 在结束加载网页时会回调
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            if(mOnProgressListener!=null) {
                mOnProgressListener.onStopProgress();
            }
            EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_DISMISS_DIALOG));
            if(TextUtils.equals(url,"about:blank")){ //空白页
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_BLANK));
            }
            super.onPageFinished(view, url);
        }


        /**
         * 拦截 url 跳转,在里边添加点击链接跳转或者操作
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            //中信相关
            Log.e("地址",url);
            if(url == null) {
                return false;
            }
            try {
                if(url.startsWith("weixin://") || url.startsWith("alipays://") ||
                        url.startsWith("mailto://") || url.startsWith("tel://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(intent);
                    return true;
                }else if(url.indexOf("pay/result")!=-1){
                    Intent intent = new Intent();
                    intent.setAction(ConstantPool.Action.ACTION_PAY_STATUS);
                    mContext.startActivity(intent);
                    EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.ORDER_CLOSE));
                    ((Activity)  mContext).finish();
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }

            //处理http和https开头的url
//           loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);

        }

        /**
         * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
         */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_DISMISS_DIALOG));
            super.onReceivedError(view, request, error);
        }

        /**
         * 当接收到https错误时，会回调此函数，在其中可以做错误处理
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //EventBus.getDefault().post(new DismissDialogEvent());
            //WebViewClient的onReceivedSslError()函数中包含了一条handler.cancel() -停止加载
            //super.onReceivedSslError(view, handler, error);
            //接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
        }

        /**
         * 在每一次请求资源时，都会通过这个函数来回调
         */
        public WebResourceResponse response = null;
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.e("网址",url);
            response = null;
            if(url.contains("pay/result")){
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEBVIEW_CLOSE));
            }
            for (int i = 0; i <imageTypes.length ; i++) {
                if(url.contains(imageTypes[i])){
                    try {
                        final PipedOutputStream out = new PipedOutputStream();
                        final PipedInputStream in = new PipedInputStream(out);
                        //Glide 图片异步加载
                        Glide.with(mContext.getApplicationContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (resource != null) {
                                    try {
                                        out.write(ImageFactory.Bitmap2Bytes(resource));
                                        out.close();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        response = new WebResourceResponse("image/png", "UTF-8", in);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                }
            }
            return response;
        }
    }

    //----------------------------------------------------------------------------------------------
    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mProgressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速
        // 关键, 添加动画进度监听器
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();      // 0.0f ~ 1.0f
                int offset = 100 - progress;
                mProgressBar.setProgress((int) (progress + offset * fraction));
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.GONE);
                isAnimStart = false;
                if(mOnProgressListener!=null) {
                    mOnProgressListener.onStopProgress();
                }
            }
        });
        anim.start();
    }
}
