package com.shangcheng.home_module.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.entity.BannerBean;

/**
 * @author Navy
 */
public class LocalImageHolderView extends Holder<BannerBean> {
    private ImageLoaderUtil imageLoaderUtil;
    private ImageView imageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(BannerBean data) {
        if (!TextUtils.isEmpty(data.getImg_url())) {
            imageLoaderUtil.loadImage(app.getApplication(), imageView, data.getImg_url());
        } else {
            if (data.getDefaultPic() != 0) {
                imageView.setImageResource(data.getDefaultPic());
            } else {
                imageView.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }

        }
    }
}
