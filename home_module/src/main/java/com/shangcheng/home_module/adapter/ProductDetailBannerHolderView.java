package com.shangcheng.home_module.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.imageloader.ImageLoader;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.home_module.R;

/**
 * @author Navy
 */
public class ProductDetailBannerHolderView extends Holder<String> {
    private ImageView imageView;
    private ImageLoaderUtil imageLoaderUtil;
    public ProductDetailBannerHolderView(View itemView) {
        super(itemView);
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(String data) {
        if (!TextUtils.isEmpty(data)) {
            ImageLoader imageLoader = new ImageLoader.Builder()
                    .url(data)
                    .imgView(imageView)
                    .placeHolder(R.mipmap.bc_ic_placeholder_large)
                    .errorHolder(R.mipmap.bc_ic_placeholder_large)
                    .scaleType(1)
                    .build();
            imageLoaderUtil.loadImage(app.getApplication(), imageLoader);
        } else {
            imageView.setImageResource(R.mipmap.bc_ic_placeholder_large);
        }
    }
}
