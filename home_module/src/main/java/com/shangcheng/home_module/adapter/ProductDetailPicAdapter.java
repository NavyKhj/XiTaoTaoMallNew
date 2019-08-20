package com.shangcheng.home_module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.shangcheng.common_module.baseApplication.BaseApplication;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.home_module.R;

import java.util.List;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：商品详情图片适配器
 * 创建人：niuyunwang
 * 创建时间：2019/3/29 13:42
 * 修改人：niuyunwang
 * 修改时间：2019/3/29 13:42
 * 修改备注：暂无
 */
public class ProductDetailPicAdapter extends RecyclerView.Adapter<ProductDetailPicAdapter.MyViewHolder> {

    private Context mContext;
    /**
     * 数据
     */
    private List<String> dataList;
    private ImageLoaderUtil imageLoaderUtil;
    private onItemClickListener onItemClickListener;
    private DisplayMetrics dm = new DisplayMetrics();
    private int width;

    public interface onItemClickListener {
        void itemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ProductDetailPicAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        this.width = dm.widthPixels;
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_peoduct_pic_recyclerview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(dataList.get(position))) {
            /*ImageLoader imageLoader = new ImageLoader.Builder()
                    .url(dataList.get(position))
                    .imgView(holder.ivProduct)
                    .placeHolder(R.mipmap.bc_ic_placeholder_middle)
                    .errorHolder(R.mipmap.bc_ic_placeholder_middle)
                    .build();
            imageLoaderUtil.loadImage(mContext, imageLoader);*/
            final RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.bc_ic_placeholder_middle)
                    .error(R.mipmap.bc_ic_placeholder_middle)
                    .override(ScreenMeasureUtil.getInstance().getWidthPixels(), ViewGroup.LayoutParams.WRAP_CONTENT);
            Glide.with(mContext)
                    .asBitmap()
                    .load(dataList.get(position))
                    .apply(requestOptions)
                    .into(holder.ivProduct);
        } else {
            holder.ivProduct.setImageResource(R.mipmap.bc_ic_placeholder_middle);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * 商品图片
         */
        ImageView ivProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_pic);

        }
    }
}
