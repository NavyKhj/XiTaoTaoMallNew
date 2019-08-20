package com.shangcheng.common_module.utils.imageloader;

import android.content.Context;

/**
 * 图片加载基类接口
 */
public interface BaseImageLoaderStrategy {
   void loadImage(Context ctx, ImageLoader img);
   void downloadImage(Context ctx, String path, DownloadDelegate delegate);
}
