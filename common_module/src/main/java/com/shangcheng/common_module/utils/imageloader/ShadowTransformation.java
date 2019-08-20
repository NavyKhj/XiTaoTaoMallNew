package com.shangcheng.common_module.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.shangcheng.common_module.utils.ImageUtils;

import java.security.MessageDigest;

/**
 * 蒙版
 * @author wangwei
 * @date 2018-7-17
 */

public class ShadowTransformation extends BitmapTransformation {
    private Context context;
    public ShadowTransformation(Context context){
        this.context = context;
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return ImageUtils.addShadow(context, toTransform);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
