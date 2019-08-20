package com.shangcheng.common_module.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.baseService.IPermissionListener;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.T;

/**
 * Html显示界面（支部工作-中信：使用webView缓存）
 *
 * @author jiangchao
 * @time 2018/5/4
 */
public class WebLinkCacheActivity extends BaseActivity implements ProgressWebViewCache.OnReceivedListener {

    private ProgressWebViewCache mProgressWebView;
    //标题名称
    private String titleName;
    private String url; //地址
    private final int REQUEST_CODE = 0;
    private boolean isToken; //是否需要拼接Token

    private void getData() {
        titleName = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        isToken = getIntent().getBooleanExtra("isToken", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        if (savedInstanceState != null) {
            titleName = (String) savedInstanceState.get("titleName");
            url = (String) savedInstanceState.get("url");
        }
        initContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressWebView != null) {
            mProgressWebView.destroy();
        }

    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    protected void onSaveState(Bundle outState) {
        outState.putString("titleName", titleName);
        outState.putString("url", url);
    }


    protected void initContent() {
        requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
            @Override
            public void getPermissionSuccess() {
                setContentView(R.layout.basecommon_activity_web_chache);
                mProgressWebView = findViewById(R.id.webView);
                mProgressWebView.setOnReceivedListener(WebLinkCacheActivity.this);
                mProgressWebView.setProgressBarStatus(true);
                //WebView加载web资源
                mProgressWebView.loadUrl(url);
            }

            @Override
            public void getPermissionFailure() {
                T.show("请赋予程序必要的储存权限");
            }
        });

    }


    @Override
    public void onReceivedTitle(String title) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mProgressWebView != null && mProgressWebView.canGoBack()) {
            mProgressWebView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 网页加载空白页监听
     *
     * @param
     */
    @Override
    protected void receiveEvent(Event event) {
        /*if(event.getCode() == ConstantPool.EventCode.WEB_BLANK){
            setDataNoContacts(!TextUtils.isEmpty(titleName) ? titleName : "");
        }else if(event.getCode() == ConstantPool.EventCode.WEB_DISMISS_DIALOG){
            app.disMissDialog();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                mProgressWebView.loadUrl("javascript:vm._jsBridge.callHandler('backhome')");
            }
        }

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initData(Bundle bundle) {

    }
}