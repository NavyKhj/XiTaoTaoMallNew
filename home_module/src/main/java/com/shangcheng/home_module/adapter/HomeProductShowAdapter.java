package com.shangcheng.home_module.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shangcheng.common_module.baseApplication.BaseApplication;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.GoodsUtils;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.imageloader.ImageLoader;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.entity.GoodsBeanEntity;

import java.util.List;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：产品列表适配器
 * 创建人：niuyunwang
 * 创建时间：2019/3/29 13:42
 * 修改人：niuyunwang
 * 修改时间：2019/3/29 13:42
 * 修改备注：暂无
 */
public class HomeProductShowAdapter extends RecyclerView.Adapter<HomeProductShowAdapter.MyViewHolder> {

    private Context mContext;
    /**
     * 数据
     */
    private List<GoodsBeanEntity.GoodsBean> dataList;
    private ImageLoaderUtil imageLoaderUtil;
    private onItemClickListener onItemClickListener;
    private boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public interface onItemClickListener {
        void itemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeProductShowAdapter(Context context, List<GoodsBeanEntity.GoodsBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.basecommon_item_goods_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GoodsBeanEntity.GoodsBean bean = dataList.get(position);
        holder.tvName.setText(bean.getName());
        if (!TextUtils.isEmpty(bean.getFirstPicture()) && !isScrolling) {
            ImageLoader imageLoader = new ImageLoader.Builder()
                    .url(bean.getFirstPicture())
                    .imgView(holder.ivProduct)
                    .placeHolder(R.mipmap.bc_ic_placeholder_middle)
                    .errorHolder(R.mipmap.bc_ic_placeholder_middle)
                    .override(172, 172)
                    .build();
            imageLoaderUtil.loadImage(mContext, imageLoader);
        } else {
            holder.ivProduct.setImageResource(R.mipmap.bc_ic_placeholder_middle);
        }
        /**价格*/
        holder.tvPrice.setText("¥" + bean.getPrice());
        if (!TextUtils.isEmpty(bean.getOriginalPrice())) {
            if (GoodsUtils.isTypeTT(bean.getAreaCode()) || GoodsUtils.isTypeST(bean.getAreaCode())) {
                holder.tvOldPrice.setVisibility(View.GONE);
            } else {
                holder.tvOldPrice.setVisibility(View.VISIBLE);
                holder.tvOldPrice.setText("¥" + bean.getOriginalPrice());
            }
        } else {
            holder.tvOldPrice.setVisibility(View.GONE);
        }
        if (bean.getPoints() > 0) {
            holder.tvQuan.setVisibility(View.VISIBLE);
            holder.tvQuan.setText("+" + bean.getPoints() + "积分");
        } else {
            holder.tvQuan.setVisibility(View.GONE);
        }

        if (bean.getStock() <= 0) {
            holder.rlSellout.setVisibility(View.VISIBLE);
        } else {
            holder.rlSellout.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(bean.getAreaCode()) || GoodsUtils.isTypePT(bean.getAreaCode())) {
            holder.ivType.setVisibility(View.GONE);
            holder.tvMark.setVisibility(View.GONE);
//            holder.tvQuan.setVisibility(View.GONE);
            holder.tvQuan.setVisibility(View.VISIBLE);
            holder.tvQuan.setText("+" + bean.getTaoticket() + "券");
        } else {
            holder.ivType.setVisibility(View.VISIBLE);
            if (GoodsUtils.isTypeSoure(bean.getAreaCode())) {
                holder.ivType.setImageResource(R.mipmap.soure_type_icon);
                holder.tvMark.setVisibility(View.GONE);
                holder.tvQuan.setVisibility(View.VISIBLE);
                holder.tvQuan.setText("+" + bean.getPoints() + "积分");
            } else if (GoodsUtils.isTypeTT(bean.getAreaCode())) {
                holder.ivType.setImageResource(R.mipmap.tt_type_icon);
                if (TextUtils.equals(bean.getTaoticket(), "0")) {
                    holder.tvMark.setVisibility(View.GONE);
                } else {
                    holder.tvMark.setVisibility(View.VISIBLE);
                    holder.tvMark.setText("返" + bean.getTaoticket() + "券");
                }

                holder.tvQuan.setVisibility(View.GONE);
                holder.tvPrice.setText("¥" + bean.getOriginalPrice());
            } else if (GoodsUtils.isTypeTP(bean.getAreaCode())) {
                if (TextUtils.equals(bean.getDiscount(), "0")) {
                    holder.tvMark.setVisibility(View.GONE);
                } else {
                    holder.tvMark.setVisibility(View.VISIBLE);
                    holder.tvMark.setText(bean.getDiscount() + "折");
                }

                holder.ivType.setImageResource(R.mipmap.tp_type_icon);
                holder.tvQuan.setVisibility(View.VISIBLE);
                holder.tvQuan.setText("+" + bean.getTaoticket() + "券");
            } else if (GoodsUtils.isTypeST(bean.getAreaCode())) {
                if (TextUtils.equals(bean.getDiscount(), "0")) {
                    holder.tvMark.setVisibility(View.GONE);
                } else {
                    holder.tvMark.setVisibility(View.VISIBLE);
                    holder.tvMark.setText(bean.getDiscount() + "折");
                }
                holder.ivType.setImageResource(R.mipmap.shetao_icon);
                holder.tvQuan.setVisibility(View.VISIBLE);
                holder.tvQuan.setText("+" + bean.getTaoticket() + "券");
            }
        }
        holder.tvNum.setText("已售" + bean.getSold() + "件");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(position);
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        ImageView img = holder.ivProduct;
        if (null != img) {
            Glide.with(mContext).clear(img);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * 整体layout
         */
        LinearLayout layout;
        /**
         * 售罄
         */
        RelativeLayout rlSellout;
        ImageView ivSellout;
        /**
         * 返券
         */
        TextView tvMark;
        /**
         * 商品名称
         */
        TextView tvName;
        /**
         * 商品价格
         */
        TextView tvPrice;
        /**
         * 商品数量
         */
        TextView tvNum;
        /**
         * 商品券
         */
        TextView tvQuan;
        /**
         * 商品全赖价格
         */
        TextView tvOldPrice;
        /**
         * 商品图片
         */
        ImageView ivProduct;
        /**
         * 商品类型
         */
        ImageView ivType;
        View line;

        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.ll_layout);
            ivSellout = itemView.findViewById(R.id.iv_sellout);
            rlSellout = itemView.findViewById(R.id.rl_sellout);
            ivType = itemView.findViewById(R.id.iv_type);
            tvMark = itemView.findViewById(R.id.tv_mark);
            tvName = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivProduct = itemView.findViewById(R.id.iv_photo);
            tvNum = itemView.findViewById(R.id.tv_sell_num);
            tvQuan = itemView.findViewById(R.id.tv_quan);
            tvOldPrice = itemView.findViewById(R.id.tv_old_price);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            line = itemView.findViewById(R.id.line);
            line.setVisibility(View.VISIBLE);
            /**设置整体背景*/
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .solid(ColorUtil.getAttrColor(R.color.normal_content_background_color, mContext))
                    .radius(3).into(layout);
            ScreenMeasureUtil.getInstance().setMViewMargin(layout, 0f, 0.032f, 0.032f, 0f);
            /**设置整体背景*/
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .solid(ContextCompat.getColor(mContext, R.color.bank_btn_noraml))
                    .brRadius(10)
                    .trRadius(10)
                    .into(tvMark);
        }
    }
}
