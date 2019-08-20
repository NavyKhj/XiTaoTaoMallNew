package com.shangcheng.common_module.utils.imageloader;

import android.graphics.Bitmap;

/**
 * 下载图片接口
 */
public interface DownloadDelegate {
    void onSuccess(String path, Bitmap bitmap);

    void onFailed(String path);
}
