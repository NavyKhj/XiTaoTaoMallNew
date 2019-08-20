package com.shangcheng.common_module.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;


/**
 * Class Note:
 * using  Glide  to load image
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    //加载优先级 :HIGH ,NORMAL ，LOW .默认NORMAL
    private Priority priority = Priority.NORMAL;
    //设置tag标签，控制pause和resume
    private Object mTag = "";

    //图片加载监听
    private OnImageLoadingListener mImageLoadingListener;

    private ImageLoader img;
    private Context mContext;

    /**
     * 图片加载监听
     */
    public interface OnImageLoadingListener {
        /*加载成功*/
        void onSuccess(String model, Drawable resource);

        /*加载失败*/
        void onError(String model);
    }

    private void onLoadingSuccess(String model, Drawable resource) {
        if (mImageLoadingListener != null) {
            mImageLoadingListener.onSuccess(model, resource);
        }
    }

    private void onLoadingError(String model) {
        if (mImageLoadingListener != null) {
            mImageLoadingListener.onError(model);
        }
    }

    public void setImageLoadingListener(OnImageLoadingListener imageLoadingListener) {
        if (imageLoadingListener != null) {
            mImageLoadingListener = imageLoadingListener;
        }
    }

    @Override
    public void loadImage(Context ctx, final ImageLoader img) {
        this.img = img;
        this.mContext = ctx;
        if (TextUtils.isEmpty(img.getUrl()) && img.getResId() == 0) {
            img.getImgView().setImageResource(img.getPlaceHolder());
        } else {
            if (ctx == null) {
                return;
            }
            if (ctx instanceof Activity && ((Activity) ctx).isDestroyed()) {
                return;
            }
            RequestBuilder drawableTypeRequest = Glide.with(ctx).asDrawable()
                    .load(TextUtils.isEmpty(img.getUrl()) ? img.getResId() : img.getUrl());  //图片路径
            RequestOptions options = new RequestOptions();
            options.disallowHardwareConfig();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            DrawableTransitionOptions o = new DrawableTransitionOptions();
            //加载动画 0- dontAnimate(直接加载不要淡入淡出效果) 1-crossFade(加载图片时候淡入淡出动画)
            switch (img.getAnimate()) {
                case 0:
                    options.dontAnimate();
                    break;
                case 1:
                    o.crossFade();
                    break;
            }
            //占位图与错位图（本地资源）
            if (img.getPlaceHolder() != -1 && img.getErrorHolder() != -1) {
                options.placeholder(img.getPlaceHolder())
                        .error(img.getErrorHolder());
            } else if (img.getPlaceDrawable() != null && img.getErrorDrawable() != null) {//占位图与错位图（网络下载Drawable）
                options.placeholder(img.getPlaceDrawable())
                        .error(img.getErrorDrawable());
            }
            // 图片显示 0-fitCenter 1-centerCrop 2-centerCrop
            switch (img.getScaleType()) {
                case 0:
                    options.fitCenter();
                    break;
                case 1:
                    options.centerCrop();
                    break;
                case 2:
                    options.centerInside();
                    break;
                default:
                    break;
            }
            // 自定义显示大小
            if (img.getWidth() != -1 && img.getHeight() != -1) {
                options.override(img.getWidth(), img.getHeight());
            }
            if (img.getTransformation() != null) {
                options.transform(img.getTransformation());
            }
            //请求监听器
            if (img.isCallback()) {
                drawableTypeRequest.listener(requestListener);
            }
            drawableTypeRequest.apply(options).transition(o)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
                            img.getImgView().setImageDrawable(resource);
                        }
                    });
        }

    }

    @Override
    public void downloadImage(Context context, String path, final DownloadDelegate delegate) {
        final String finalPath = changeSDCardPath(path);
        RequestOptions options = new RequestOptions();
        options.disallowHardwareConfig();
        Glide.with(context.getApplicationContext()).asBitmap().apply(options).load(finalPath).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (delegate != null) {
                    delegate.onSuccess(finalPath, resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (delegate != null) {
                    delegate.onFailed(finalPath);

                }
            }
        });
    }
    /**
     * 本地图片显示路径转换
     */
    private static String changeSDCardPath(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }
        return path;
    }

    RequestListener requestListener = new RequestListener<Bitmap>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            onLoadingError(model.toString());
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            if(mContext != null){
                onLoadingSuccess(model.toString(), new BitmapDrawable(mContext.getResources(),resource));
            }
            return false;
        }
    };


}
