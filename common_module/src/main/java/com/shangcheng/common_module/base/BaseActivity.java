package com.shangcheng.common_module.base;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.scottyab.aescrypt.AESCrypt;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.base.baseService.BaseInterface;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.dalog.ShareDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import qiu.niorgai.StatusBarCompat;

/**
 * @author Navy
 */
public abstract class BaseActivity extends BasePermissionActivity implements BaseInterface {
    @Nullable
    private TextView mToolbarTitle;
    private ClipboardManager clipboardManager;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView(savedInstanceState);
        mContext = this;
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(getTitle());
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        }
        initData(savedInstanceState);
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
        //注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getshareText();
    }

    private void getshareText() {
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = Objects.requireNonNull(clipData).getItemAt(0);
            if (null != item.getText()) {
                String text = item.getText().toString();
                if (!TextUtils.isEmpty(text) && text.contains("喜淘淘APP")) {
                    try {
                        String[] strings = text.split("@@");
                        String deStrings = AESCrypt.decrypt("d#fK^sU!9qrW8$aGLPqWTD8oL", strings[1]);
                        String goodsId = deStrings.split("&")[0];
                        String userId = deStrings.split("&")[1];
                        String name = strings[0].split("】")[0];
                        name = name.replace("【", "");
                        ShareDialog.showShareDialog(mContext, name, goodsId, userId, clipboardManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Toolbar getToolbar() {
        if ((Toolbar) findViewById(R.id.toolbar) != null) {
            ScreenMeasureUtil.getInstance().setMLayoutParam((Toolbar) findViewById(R.id.toolbar), 1f,
                    ScreenMeasureUtil.getInstance().dip2px(64) / (float) ScreenMeasureUtil.getInstance().getWidthPixels() - ScreenMeasureUtil.getInstance().getStatusBarHeight());
        }
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 获取头部标题的TextView
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    public enum GravityType {
        /**
         * 居中显示
         */
        CENTER,
        /**
         * 居右显示
         */
        RIGHT
    }

    @SuppressLint("RtlHardcoded")
    public void setToolBarTitle(@Nullable CharSequence title, @Nullable GravityType gravityType) {
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) Objects.requireNonNull(mToolbarTitle).getLayoutParams();
        switch (Objects.requireNonNull(gravityType)) {
            case CENTER:
                layoutParams.gravity = Gravity.CENTER;
                setToolBar(title, layoutParams);
                break;
            case RIGHT:
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.rightMargin = 20;
                setToolBar(title, layoutParams);
                break;
            default:
                break;
        }
    }

    private void setToolBar(@Nullable CharSequence title, @Nullable Toolbar.LayoutParams layoutParams) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setLayoutParams(layoutParams);
            getToolbar().setVisibility(View.VISIBLE);
            mToolbarTitle.setText(title);
            //设置无图标
            mToolbarTitle.setCompoundDrawables(null, null, null, null);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    protected void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.drawable.sel_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFastClick()) {
                    finish();
                }
            }
        });
    }

    private long lastClick = 0;

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 500) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                if (Objects.requireNonNull(v).getWindowToken() != null) {
                    hiddenKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v     视图
     * @param event 视图事件
     * @return 返回
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        try {
            v.getParent().requestDisallowInterceptTouchEvent(true);//通知父控件勿拦截本控件touch事件
            if (v instanceof EditText) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                // 点击EditText的事件，忽略它。
                return event.getX() <= left || event.getX() >= right
                        || event.getY() <= top || event.getY() >= bottom;
            }
        } catch (Exception e) {
            return false;
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏键盘
     */
    protected void hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        if (mInputMethodManager != null) {
            mInputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(this
                    .getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }
}
