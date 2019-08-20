package com.shangcheng.common_module.widget.bottomnavigation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.shangcheng.common_module.utils.imageloader.ImageLoader;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see FrameLayout
 * @since 19 Mar 2016
 */
abstract class BottomNavigationTab extends FrameLayout {

    protected int mPosition;
    protected int mActiveColor;
    protected int mInActiveColor;
    protected int mBackgroundColor;
    protected int mActiveWidth;
    protected int mInActiveWidth;

    protected String mUrl;
    protected Drawable mCompactIcon;
    protected Drawable mCompactInActiveIcon;
    protected boolean isInActiveIconSet = false;
    protected String mLabel;
    boolean isActive = false;

    protected View containerView;
    protected TextView labelView;
    protected ImageView iconView;
    protected FrameLayout iconContainerView;
    protected SuperTextView fixed_bottom_navigation_un_read,view_un_read;

    protected Context mContext;
    protected ImageLoaderUtil imageLoaderUtil = new ImageLoaderUtil();

    public BottomNavigationTab(Context context) {
        this(context, null);
    }

    public BottomNavigationTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setActiveWidth(int activeWidth) {
        mActiveWidth = activeWidth;
    }

    public void setInactiveWidth(int inactiveWidth) {
        mInActiveWidth = inactiveWidth;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = mInActiveWidth;
        setLayoutParams(params);
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setIcon(Drawable icon) {
        mCompactIcon = DrawableCompat.wrap(icon);
    }

    public void setInactiveIcon(Drawable icon) {
        mCompactInActiveIcon = DrawableCompat.wrap(icon);
        isInActiveIconSet = true;
    }

    public void setLabel(String label) {
        mLabel = label;
        labelView.setText(label);
    }

    public void setActiveColor(int activeColor) {
        mActiveColor = activeColor;
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public void setInactiveColor(int inActiveColor) {
        mInActiveColor = inActiveColor;
        labelView.setTextColor(inActiveColor);
    }

    public void setItemBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    public void selectAnimation() {
        startShakeByViewAnim(iconView, 1f, 1.4f, 600);
    }

    public void select(boolean setActiveColor, int animationDuration) {
        isActive = true;
        //选择动画
        startShakeByViewAnim(iconView, 1f, 1.4f, 600);
        iconView.setSelected(true);
        if (setActiveColor) {
            labelView.setTextColor(mActiveColor);
        } else {
            labelView.setTextColor(mBackgroundColor);
        }
    }

    public void unSelect(boolean setActiveColor, int animationDuration) {
        isActive = false;
        //离开动画
        labelView.setTextColor(mInActiveColor);
        iconView.setSelected(false);
    }

    @CallSuper
    public void initialise(boolean setActiveColor) {
        iconView.setSelected(false);
        if (isInActiveIconSet) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_selected},
                    mCompactIcon);
            states.addState(new int[]{-android.R.attr.state_selected},
                    mCompactInActiveIcon);
            states.addState(new int[]{},
                    mCompactInActiveIcon);
            iconView.setImageDrawable(states);
        } else {
            if (setActiveColor) {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mActiveColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            } else {
                DrawableCompat.setTintList(mCompactIcon, new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected}, //1
                                new int[]{-android.R.attr.state_selected}, //2
                                new int[]{}
                        },
                        new int[]{
                                mBackgroundColor, //1
                                mInActiveColor, //2
                                mInActiveColor //3
                        }
                ));
            }
            setImageResource(mUrl, mCompactIcon, iconView);
        }

    }

    /**
     * 设置图片
     *
     * @param url
     * @param holderDrawable
     * @param imageView
     */
    private void setImageResource(String url, Drawable holderDrawable, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            ImageLoader imageLoader = new ImageLoader.Builder()
                    .url(url)
                    .placeDrawable(holderDrawable)
                    .errorDrawable(holderDrawable)
                    .imgView(imageView)
                    .scaleType(-1)
                    .build();
            imageLoaderUtil.loadImage(mContext, imageLoader);
        } else {
            imageView.setImageDrawable(holderDrawable);
        }
    }

    /**
     * 抖动动画要求同时对大小、动画时长进行更改且可定制
     *
     * @param view
     * @param scaleSmall
     * @param scaleLarge
     * @param duration
     */
    private void startShakeByViewAnim(View view, float scaleSmall, float scaleLarge, long duration) {
        if (view == null) {
            return;
        }

        //X轴放大-缩小
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", scaleSmall, scaleLarge, scaleSmall).setDuration(duration);
        //Y轴放大-缩小
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", scaleSmall, scaleLarge, scaleSmall).setDuration(duration);

        //动画集合
        AnimatorSet set = new AnimatorSet();
        //同时运行
        set.playTogether(animator1, animator2);
        //设置弹跳结束
        set.setInterpolator(new BounceInterpolator());
        //动画执行
        set.start();
    }
}
