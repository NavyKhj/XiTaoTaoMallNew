package com.shangcheng.common_module.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangcheng.common_module.baseApplication.app;

import java.math.BigDecimal;

/**
 * @author Navy
 */
public class ScreenMeasureUtil {
    /**
     * 屏幕宽度
     */
    private int width_pixels;
    /**
     * 屏幕高度
     */
    private int height_pixels;
    /**
     * 屏幕宽比例
     */
    private BigDecimal proportion;
    /**
     * 屏幕密度
     */
    private float density;

    private static final class Holder {
        private static final ScreenMeasureUtil INSTANCE = new ScreenMeasureUtil();
    }

    public static ScreenMeasureUtil getInstance() {
        return Holder.INSTANCE;
    }

    public void init() {
        calculateWidthAndHeight();
    }

    /**
     * 获取宽高与DP比例（DP比例用于字体与字体图标的大小，非精准）
     */
    private void calculateWidthAndHeight() {
        DisplayMetrics dm = app.getApplication().getResources().getDisplayMetrics();
        // 计算当前设备宽度密度比与基础宽度密度比的比例，适用于分辨率800*480，密度1.5为基准的开发
        BigDecimal phone = new BigDecimal(dm.widthPixels).divide(
                new BigDecimal(dm.density), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal normal = new BigDecimal(480).divide(new BigDecimal(1.5), 2,
                BigDecimal.ROUND_HALF_UP);
        this.proportion = phone.divide(normal, 2, BigDecimal.ROUND_HALF_UP);
        this.width_pixels = dm.widthPixels;
        this.height_pixels = dm.heightPixels;
        this.density = dm.density;
    }

    /**
     * 设置控件字体
     *
     * @param tv     控件
     * @param dpSize 大小
     */
    public void setMTextSize(TextView tv, int dpSize) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
                getProportion().multiply(new BigDecimal(dpSize)).intValue());
    }

    /**
     * 设置控件宽高 <br>
     * 特殊设置时说明<br>
     * 1f ： MATCH_PARENT <br>
     * 0f ： WRAP_CONTENT
     *
     * @param vc     View控件
     * @param width  宽(相对宽度的比例)
     * @param height 高(相对宽度的比例)
     */
    public void setMLayoutParam(View vc, float width, float height) {
        ViewGroup.LayoutParams lp = vc.getLayoutParams();
        lp.width = width == 1f ? ViewGroup.LayoutParams.MATCH_PARENT
                : (width == 0f ? ViewGroup.LayoutParams.WRAP_CONTENT
                : (int) (getWidthPixels() * width));
        lp.height = height == 1f ? ViewGroup.LayoutParams.MATCH_PARENT
                : (height == 0f ? ViewGroup.LayoutParams.WRAP_CONTENT
                : (int) (getWidthPixels() * height));
        vc.setLayoutParams(lp);
    }

    /**
     * 设置控件Margin
     *
     * @param vc     View控件
     * @param left   左侧(相对宽度的比例)
     * @param top    顶部(相对宽度的比例)
     * @param right  右侧(相对宽度的比例)
     * @param bottom 底部(相对宽度的比例)
     */
    public void setMViewMargin(View vc, float left, float top, float right,
                               float bottom) {
        try {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vc
                    .getLayoutParams();
            params.setMargins((int) (getWidthPixels() * left),
                    (int) (getWidthPixels() * top),
                    (int) (getWidthPixels() * right),
                    (int) (getWidthPixels() * bottom));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置View相对比例的Padding
     *
     * @param vc     View控件
     * @param left   左侧(相对宽度的比例)
     * @param top    顶部(相对宽度的比例)
     * @param right  右侧(相对宽度的比例)
     * @param bottom 底部(相对宽度的比例)
     */
    public void setMViewPadding(View vc, float left, float top, float right,
                                float bottom) {
        vc.setPadding((int) (getWidthPixels() * left),
                (int) (getWidthPixels() * top),
                (int) (getWidthPixels() * right),
                (int) (getWidthPixels() * bottom));
    }

    public int dip2px(float var1) {
        float var2 = app.getApplication().getResources().getDisplayMetrics().density;
        return (int) (var1 * var2 + 0.5F);
    }

    public int px2dip(float var1) {
        float var2 = app.getApplication().getResources().getDisplayMetrics().density;
        return (int) (var1 / var2 + 0.5F);
    }

    public int sp2px(float var1) {
        float var2 = app.getApplication().getResources().getDisplayMetrics().density;
        return (int) (var1 * var2 + 0.5F);
    }

    public int px2sp(float var1) {
        float var2 = app.getApplication().getResources().getDisplayMetrics().density;
        return (int) (var1 / var2 + 0.5F);
    }

    public int getWidthPixels() {
        if (0 == width_pixels) {
            calculateWidthAndHeight();
        }
        return width_pixels;
    }

    public int getHeightPixels() {
        if (0 == height_pixels) {
            calculateWidthAndHeight();
        }
        return height_pixels;
    }

    public BigDecimal getProportion() {
        if (null == proportion) {
            calculateWidthAndHeight();
        }
        return proportion;
    }

    public float getDensity() {
        if (0 == density) {
            calculateWidthAndHeight();
        }
        return density;
    }
    /**
     * 获取状态栏高度
     * @return 高度
     */
    public float getStatusBarHeight() {
        Resources resources = app.getApplication().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height / (float) getWidthPixels();
    }
}
