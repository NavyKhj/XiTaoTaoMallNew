package com.shangcheng.common_module.utils.imageloader;

import com.bumptech.glide.module.AppGlideModule;

/**
 * @author Navy
 */
//@GlideModule
public class CustomGlideModule extends AppGlideModule {
    /*@Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 10; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(3)
                .build();
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }*/
}
