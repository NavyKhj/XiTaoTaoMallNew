package com.shangcheng.common_module.utils.imageloader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


/**
 * Class Note:
 * encapsulation of ImageView,Build Pattern used
 */
public class ImageLoader {
    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int resId; //本地资源文件 R.drawable.ic_xxx
    private int placeHolder; //当没有成功加载的时候显示的图片
    private int errorHolder; //加载发生错误占位图
    private Drawable placeDrawable; //当没有成功加载的时候显示的图片
    private Drawable errorDrawable; //加载发生错误占位图
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载
    private boolean callback; //请求监听器  true-监听  false-不监听
    private int scaleType; //0-fitCenter 可显示全部变形 1-centerCrop 存在裁剪不变形 2-CENTER_INSIDE
    private int animate;  // 0- dontAnimate(直接加载不要淡入淡出效果) 1-crossFade(加载图片时候淡入淡出动画)
    private int width,  height;  //override 自定义显示大小
    private BitmapTransformation transformation;//图形处理

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.resId = builder.resId;
        this.placeHolder = builder.placeHolder;
        this.errorHolder = builder.errorHolder;
        this.placeDrawable = builder.placeDrawable;
        this.errorDrawable = builder.errorDrawable;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.callback = builder.callback;
        this.scaleType = builder.scaleType;
        this.animate = builder.animate;
        this.width = builder.width;
        this.height = builder.height;
        this.transformation = builder.transformation;
    }
    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public int getAnimate() {
        return animate;
    }

    public int getErrorHolder() {
        return errorHolder;
    }

    public boolean isCallback() {
        return callback;
    }

    public int getScaleType() {
        return scaleType;
    }

    public int getResId() {
        return resId;
    }

    public Drawable getPlaceDrawable() {
        return placeDrawable;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public static class Builder {
        private int type;
        private String url;
        private int resId;
        private int placeHolder;
        private int errorHolder;
        private Drawable placeDrawable;
        private Drawable errorDrawable;
        private ImageView imgView;
        private int wifiStrategy;
        private boolean callback;
        private int scaleType;
        private int animate;
        private int width,  height;
        private BitmapTransformation transformation;
        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.resId = 0;
            this.placeHolder = -1;
            this.errorHolder = -1;
            this.placeDrawable = null;
            this.errorDrawable = null;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
            this.callback = false;
            this.scaleType = 1; //默认centerCrop
            this.animate = 0;
            this.width = -1;
            this.height = -1;
            this.transformation = null;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder resId(int resId) {
            this.resId = resId;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public Builder placeDrawable(Drawable placeDrawable) {
            this.placeDrawable = placeDrawable;
            return this;
        }

        public Builder errorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder callback(boolean callback) {
            this.callback = callback;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public Builder scaleType(int scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder animate(int animate) {
            this.animate = animate;
            return this;
        }

        public Builder override(int width,int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
