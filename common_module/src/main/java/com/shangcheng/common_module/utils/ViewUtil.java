package com.shangcheng.common_module.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * View相关设置不同状态或形状的背景、图片
 *
 * @author yangzm
 * @time 2016/12/8
 */
public class ViewUtil {

    private Context mContext;

    /**stroke边框宽度 (默认选择STROKE_MEDIUM)*/
    public static final int STROKE_SMALL = 1;

    public static final int STROKE_MEDIUM = 2;

    public static final int STROKE_LARGE = 4;

    /**round圆角半径*/
    public static int ROUND_MINI = 3;

    public static int ROUND_SMALL = 6;

    public static int ROUND_MEDIUM = 12;

    public static int ROUND_LARGE = 24;


    public ViewUtil(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        ROUND_MINI = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * 0.006);
        ROUND_SMALL = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * 0.013);
        ROUND_MEDIUM = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * 0.025);
        ROUND_LARGE = (int) (ScreenMeasureUtil.getInstance().getWidthPixels() * 0.05);
    }

    /**
     * 在API16以前使用setBackgroundDrawable，在API16以后使用setBackground
     * API16<---->4.1
     *
     * @param view
     * @param drawable
     */
    public void setBackgroundOfVersion(View view, Drawable drawable) {
        view.setBackground(drawable);
    }


    /**
     * 设置Shape背景图
     *
     * @param strokeWidth  边框宽度
     * @param strokeColor  边框颜色
     * @param roundRadius  圆角半径
     * @param fillColor    内部填充颜色
     * @param gradientType 填充样式  GradientDrawable.OVAL 圆形  GradientDrawable.RECTANGLE  矩形
     * @return
     */
    public GradientDrawable setShapeDrawable(int strokeWidth, int strokeColor,
                                             int roundRadius, int fillColor, int gradientType) {
        /** 创建drawable */
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        if (roundRadius != -1) {
            gd.setCornerRadius(roundRadius);
        }
        if (strokeWidth != -1) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        if (gradientType != -1) {
            gd.setShape(gradientType);
        }
        return gd;
    }

    /**
     * 设置Shape背景图
     *
     * @param strokeWidth       边框宽度
     * @param strokeColor       边框颜色
     * @param topLeftRadius     圆角半径
     * @param topRightRadius    圆角半径
     * @param bottomRightRadius 圆角半径
     * @param bottomLeftRadius  圆角半径
     * @param fillColor         内部填充颜色
     * @param gradientType      填充样式  GradientDrawable.OVAL 圆形
     * @return
     */
    public GradientDrawable setShapeDrawable(int strokeWidth, int strokeColor,
                                             int topLeftRadius, int topRightRadius,
                                             int bottomRightRadius, int bottomLeftRadius,
                                             int fillColor, int gradientType) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);

            gd.setCornerRadii(new float[]
                    {topLeftRadius, topLeftRadius, topRightRadius, topRightRadius,
                            bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});


        if (strokeWidth != -1) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        if (gradientType != -1) {
            gd.setShape(gradientType);
        }
        return gd;
    }


    /**
     * 对TextView设置文字颜色（不同状态）
     *
     * @param normal
     * @param pressed
     * @param unable
     * @return
     */
    public ColorStateList createColorStateList(int normal, int pressed, int unable) {
        int[] colors = new int[]{pressed, normal, unable, normal};
        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_window_focused};
        states[3] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 对TextView设置文字颜色（选中状态）
     *
     * @param selected
     * @param normal
     * @return
     */
    public ColorStateList createColorStateList(int normal, int selected) {
        int[] colors = new int[]{selected, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 对TextView设置文字颜色（选中状态）
     *
     * @param checked
     * @param normal
     * @return
     */
    public ColorStateList checkColorStateList(int normal, int checked) {
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 设置背景颜色的选中状态
     *
     * @param idNormal
     * @param idPressed
     * @param idUnable
     * @return
     */
    public StateListDrawable newSelector(int idNormal, int idPressed, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : ContextCompat.getDrawable(mContext, idNormal);
        Drawable pressed = idPressed == -1 ? null : ContextCompat.getDrawable(mContext, idPressed);
        Drawable unable = idUnable == -1 ? null :  ContextCompat.getDrawable(mContext,idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }


    /**
     * 设置背景颜色的选中状态
     *
     * @param idNormal
     * @param idSelected
     * @return
     */
    public StateListDrawable newSelector(int idNormal, int idSelected) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : ContextCompat.getDrawable(mContext, idNormal);
        Drawable selected = idSelected == -1 ? null : ContextCompat.getDrawable(mContext, idSelected);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.SELECTED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_selected}, selected);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 设置Selector
     *
     * @param normal
     * @param pressed
     * @param unable
     * @return
     */
    public StateListDrawable newSelector(Drawable normal, Drawable pressed, Drawable unable) {
        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 设置enable为true和false使用
     */
    public StateListDrawable newSelectorEnable(Drawable normal, Drawable pressed, Drawable unable) {
        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        int enabled = android.R.attr.state_enabled;//正常为true，加-（负号）为false
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{enabled}, normal);//true
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{-enabled}, unable);//false
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 设置Selector
     *
     * @param normal
     * @param selected
     * @return
     */
    public StateListDrawable newSelector(Drawable normal, Drawable selected) {
        StateListDrawable bg = new StateListDrawable();
        // View.SELECTED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_selected}, selected);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

}