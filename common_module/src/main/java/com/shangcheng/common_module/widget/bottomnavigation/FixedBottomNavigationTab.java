package com.shangcheng.common_module.widget.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.ViewUtil;


/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see BottomNavigationTab
 * @since 19 Mar 2016
 */
public class FixedBottomNavigationTab extends BottomNavigationTab {

    private float labelScale = 1;

    public FixedBottomNavigationTab(Context context) {
        super(context);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixedBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init(Context context) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.basecommon_item_fixed_bottom_navigation, this, true);
        containerView = view.findViewById(R.id.fixed_bottom_navigation_container);
        labelView = view.findViewById(R.id.fixed_bottom_navigation_title);
        iconView = view.findViewById(R.id.fixed_bottom_navigation_icon);
        iconContainerView = view.findViewById(R.id.fixed_bottom_navigation_icon_container);
        fixed_bottom_navigation_un_read = view.findViewById(R.id.fixed_bottom_navigation_un_read);
        view_un_read = view.findViewById(R.id.view_un_read);

        ScreenMeasureUtil.getInstance().setMTextSize(labelView, 11);
        ScreenMeasureUtil.getInstance().setMLayoutParam(iconView, 0.6f, 0.06f);
        ScreenMeasureUtil.getInstance().setMViewMargin(labelView, 0, 0, 0, 0.01f);
        ScreenMeasureUtil.getInstance().setMViewMargin(iconView, 0, 0.016f, 0, 0.016f);
        ScreenMeasureUtil.getInstance().setMViewMargin(fixed_bottom_navigation_un_read, 0.04f, 0f, 0, 0.016f);
        ScreenMeasureUtil.getInstance().setMViewMargin(view_un_read, 0.04f, 0f, 0, 0.016f);
        ScreenMeasureUtil.getInstance().setMLayoutParam(view_un_read, 0.028f, 0.028f);

        ViewUtil viewUtil = new ViewUtil(context);
        viewUtil.setBackgroundOfVersion(fixed_bottom_navigation_un_read, viewUtil.setShapeDrawable
                (ViewUtil.STROKE_MEDIUM, R.color.normal_main_button_text_color, 45, R.color.normal_main_color, -1));
        ScreenMeasureUtil.getInstance().setMTextSize(fixed_bottom_navigation_un_read, 10);
        ScreenMeasureUtil.getInstance().setMViewPadding(fixed_bottom_navigation_un_read, 0.014f, 0, 0.014f, 0);
        fixed_bottom_navigation_un_read.setTextColor(ContextCompat.getColor(context, R.color.normal_content_background_color));

        view_un_read.setCorner(ScreenMeasureUtil.getInstance().getWidthPixels() * 0.028f);
        viewUtil.setBackgroundOfVersion(view_un_read, viewUtil.setShapeDrawable
                (ViewUtil.STROKE_MEDIUM, R.color.normal_main_button_text_color, 45, R.color.normal_main_color, -1));
        super.init(context);
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
        labelView.animate().scaleX(1).scaleY(1).setDuration(animationDuration).start();
        super.select(setActiveColor, animationDuration);
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
        labelView.animate().scaleX(labelScale).scaleY(labelScale).setDuration(animationDuration).start();
        super.unSelect(setActiveColor, animationDuration);
    }
}
