package com.shangcheng.common_module.utils.web.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.shangcheng.common_module.BuildConfig;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.web.webutil.InjectedChromeClient;
import com.shangcheng.common_module.utils.web.webutil.JsGetMethod;

import java.io.File;

/**
 * 进度条WebView
 *
 * @author yangzm
 * @time 2017/4/20
 */

public class ProgressWebView extends WebView {

    private Context mContext;
    private ProgressBar mProgressBar; //进度条
    private boolean isAnimStart; //动画标记
    private int currentProgress; //当前加载进度
    private boolean isShow = false; // 是否显示进度条
    private WebSettings wSet;
    private OnProgressListener mOnProgressListener;
    private OnReceivedListener mOnReceivedListener;
    private OnFileChooserListener mOnFileChooserListener;
    private OnShowFileChooserListener mOnShowFileChooserListener;
    /*拷贝逻辑相关*/
    private ClipboardManager mClipboardManager;

    public interface OnProgressListener {
        void onStartProgress();

        void onStopProgress();

    }

    public interface OnReceivedListener {
        void onReceivedTitle(String title);
    }

    public interface OnFileChooserListener {
        void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture);
    }

    public interface OnShowFileChooserListener {
        boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                  WebChromeClient.FileChooserParams fileChooserParams);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.mOnProgressListener = onProgressListener;
    }

    public void setOnReceivedListener(OnReceivedListener onReceivedListener) {
        this.mOnReceivedListener = onReceivedListener;
    }

    public void setOnFileChooserListener(OnFileChooserListener onFileChooserListener) {
        this.mOnFileChooserListener = onFileChooserListener;
    }

    public void setOnShowFileChooserListener(OnShowFileChooserListener onShowFileChooserListener) {
        this.mOnShowFileChooserListener = onShowFileChooserListener;
    }

    public ProgressWebView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        mProgressBar.setLayoutParams(params);
        //设置进度条样式
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.web_progress_bar);
        mProgressBar.setProgressDrawable(drawable);
        //添加进度条
        addView(mProgressBar);
        //webView设置
        initWebSettings();
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        setWebViewClient(new CustomWebViewClient());
        //WebChromeClient是一个事件接口，用于响应那些改变浏览器中装饰元素的事件包括JavaScript警告信息、网页图标、状态条加载、网页标题刷新等
        setWebChromeClient(new CustomChromeClient("jsBridge", JsGetMethod.class));
        /*//WebView 调试
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }*/

        /*拷贝逻辑*/
        mClipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = mClipboardManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                if (item != null && !TextUtils.isEmpty(item.getText())) {
                    SharedPreferences sp = mContext.getSharedPreferences("Clipboard", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("content", item.getText().toString());
                    editor.apply();
                }
            }
        });
    }

    /**
     * 初始化WebView的配置
     */
    private void initWebSettings() {
        wSet = getSettings();
        //默认缓存设置
        setCache(false);
        //允许JavaScript
        wSet.setJavaScriptEnabled(true);
        //默认编码utf-8
        wSet.setDefaultTextEncodingName("utf-8");
        //设置可以支持缩放
        wSet.setSupportZoom(true);
        wSet.setDomStorageEnabled(true);
        wSet.setBuiltInZoomControls(true); //支持手势缩放
        wSet.setDisplayZoomControls(false); //是否显示缩放按钮
        //扩大比例的缩放
//      wSet.setUseWideViewPort(true);
        //设置字号
        wSet.setTextZoom(100);
        //获取及设置UserAgentString
        String userAgent = wSet.getUserAgentString();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(userAgent);
        stringBuffer.append(" ");//空格
        stringBuffer.append(BuildConfig.PROJECT_FULL_PIN_YIN + File.separator + BuildConfig.VERSION_NAME);
        wSet.setUserAgentString(stringBuffer.toString());
        //自适应屏幕
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wSet.setLoadWithOverviewMode(true);
        //https加载http需要支持MIXED_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    public void setProgressBarStatus(boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * 当前链接
     *
     * @return
     */
    public String getCurrentUrl() {
        String url = getUrl();
        return url;
    }

    /**
     * 设置缓存机制
     *
     * @param isCache
     */
    private static final String APP_CACHE_DIRNAME = "/webcache";

    public void setCache(boolean isCache) {
        if (isCache) {
            getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
            // 开启 DOM storage API 功能
            getSettings().setDomStorageEnabled(true);
            //开启 database storage API 功能
            getSettings().setDatabaseEnabled(true);
            String cacheDirPath = mContext.getExternalFilesDir(APP_CACHE_DIRNAME).toString();
            //设置数据库缓存路径
            getSettings().setDatabasePath(cacheDirPath);
            //设置  Application Caches 缓存目录
            getSettings().setAppCachePath(cacheDirPath);
            //开启 Application Caches 功能
            getSettings().setAppCacheEnabled(true);
        } else {
            //无缓存
            getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
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
            /* currentProgress = mProgressBar.getProgress();
            if (newProgress >= 100 && !isAnimStart) {
                // 防止调用多次动画
                isAnimStart = true;
                mProgressBar.setProgress(newProgress);
                // 开启属性动画让进度条平滑消失
                startDismissAnimation(mProgressBar.getProgress());
            } else {
                // 开启属性动画让进度条平滑递增
                startProgressAnimation(newProgress);
            } */
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
            if (mOnReceivedListener != null) {
                mOnReceivedListener.onReceivedTitle(title);
            }
            super.onReceivedTitle(view, title);
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            if (mOnFileChooserListener != null) {
                mOnFileChooserListener.openFileChooser(valueCallback, acceptType, capture);
            }
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            if (mOnShowFileChooserListener != null) {
                mOnShowFileChooserListener.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
            return true;
        }
    }

    /**
     * WebView 请求回调
     */

    public class CustomWebViewClient extends WebViewClient {

        private String[] imageTypes = new String[]{".jpg", ".png", ".jpeg", "webp"};

        /**
         * 在开始加载网页时会回调
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (isShow) {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAlpha(1.0f);
            }
            if (mOnProgressListener != null) {
                mOnProgressListener.onStartProgress();
            }
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 在结束加载网页时会回调
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            if (mOnProgressListener != null) {mOnProgressListener.onStopProgress();}
            EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_DISMISS_DIALOG));
            if (TextUtils.equals(url, "about:blank")) { //空白页
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.WEB_BLANK));
            }
            super.onPageFinished(view, url);
        }

        /**
         * 拦截 url 跳转,在里边添加点击链接跳转或者操作
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //解决重定向问题（返回值是true的时候控制去WebView打开，返回值是false调用系统浏览器或第三方浏览器）
            if (url.startsWith("tel:")) {
                //判断是拨打电话、调系统打电话界面
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                mContext.startActivity(intent);
                return true;
            }
            boolean flag = false;
            HitTestResult hitTestResult = view.getHitTestResult();
            if (hitTestResult != null && hitTestResult.getType() != 0) {
                view.loadUrl(url);
                flag = true;
            }
            return flag;
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
            return super.shouldInterceptRequest(view, url);
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
                if (mOnProgressListener != null) {
                    mOnProgressListener.onStopProgress();
                }
            }
        });
        anim.start();
    }

    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param url     可以使用[domain][host]
     * @param value   cookie相关拼接数据
     */
    public void syncCookie(Context context, String url, String value) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true); //允许接受 Cookie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(this, true);        //跨域cookie读取
        }
        cookieManager.removeSessionCookie();// 移除缓存的cookie
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, value);
        CookieSyncManager.getInstance().sync(); //立即同步
    }

}
