package com.shangcheng.home_module.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.entity.BannerBean;
import com.shangcheng.home_module.entity.HomeProductBean;

import java.util.List;

/**
 * @author Navy
 */
public class TTLocalImageHolderView extends Holder<List<HomeProductBean>> {
    private ImageLoaderUtil imageLoaderUtil;
    /**
     * 整体布局
     */
    private RelativeLayout rlFrist;
    private RelativeLayout rlSecond;
    private RelativeLayout rlThree;
    /**
     * 展示的图片
     */
    private ImageView ivFirst;
    private ImageView ivSecond;
    private ImageView ivThree;
    /**
     * 展示的价格
     */
    private TextView tvPriceFirst;
    private TextView tvPriceSecond;
    private TextView tvPriceThree;
    /**
     * 展示的数量
     */
    private TextView tvNumFirst;
    private TextView tvNumSecond;
    private TextView tvNumThree;
    /**
     * 折扣
     */
    private TextView tvZheFirst;
    private TextView tvZheSecond;
    private TextView tvZheThree;
    private ITTLocalImageHolderListener listener;

    public TTLocalImageHolderView(View itemView, ITTLocalImageHolderListener listener) {
        super(itemView);
        this.listener = listener;
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView(View view) {
        rlFrist = view.findViewById(R.id.rl_taopi_frist);
        rlSecond = view.findViewById(R.id.rl_taopi_second);
        rlThree = view.findViewById(R.id.rl_taopi_three);

        ivFirst = view.findViewById(R.id.iv_taopi_frist);
        ivSecond = view.findViewById(R.id.iv_taopi_second);
        ivThree = view.findViewById(R.id.iv_taopi_three);

        /**价格*/
        tvPriceFirst = view.findViewById(R.id.tv_taopi_price_first);
        tvPriceSecond = view.findViewById(R.id.tv_taopi_price_second);
        tvPriceThree = view.findViewById(R.id.tv_taopi_price_three);

        /**数量*/
        tvNumFirst = view.findViewById(R.id.tv_taopi_num_first);
        tvNumSecond = view.findViewById(R.id.tv_taopi_num_second);
        tvNumThree = view.findViewById(R.id.tv_taopi_num_three);

        /**折扣*/
        tvZheFirst = view.findViewById(R.id.tv_zhe_frist);
        tvZheSecond = view.findViewById(R.id.tv_zhe_second);
        tvZheThree = view.findViewById(R.id.tv_zhe_three);
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .solid(ContextCompat.getColor(app.getApplication(), R.color.bank_btn_noraml))
                .radius(10).into(tvZheFirst);
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .solid(ContextCompat.getColor(app.getApplication(), R.color.bank_btn_noraml))
                .radius(10).into(tvZheSecond);
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .solid(ContextCompat.getColor(app.getApplication(), R.color.bank_btn_noraml))
                .radius(10).into(tvZheThree);
    }

    @Override
    public void updateUI(final List<HomeProductBean> data) {
        if (data != null && data.size() >= 3) {
            rlFrist.setVisibility(View.VISIBLE);
            rlSecond.setVisibility(View.VISIBLE);
            rlThree.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(data.get(0).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivFirst, data.get(0).getPicture());
            } else {
                ivFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            if (!TextUtils.isEmpty(data.get(1).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivSecond, data.get(1).getPicture());
            } else {
                ivSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            if (!TextUtils.isEmpty(data.get(2).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivThree, data.get(2).getPicture());
            } else {
                ivThree.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            tvPriceFirst.setText("¥" + data.get(0).getOriginalPrice());
            tvPriceSecond.setText("¥" + data.get(1).getOriginalPrice());
            tvPriceThree.setText("¥" + data.get(2).getOriginalPrice());

            tvNumFirst.setText("已售" + data.get(0).getSold() + "件");
            tvNumSecond.setText("已售" + data.get(1).getSold() + "件");
            tvNumThree.setText("已售" + data.get(2).getSold() + "件");
            if (!TextUtils.isEmpty(data.get(0).getTaoticket())) {
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText("返" + data.get(0).getTaoticket() + "券");
            } else {
                tvZheFirst.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.get(1).getTaoticket())) {
                tvZheSecond.setVisibility(View.VISIBLE);
                tvZheSecond.setText("返" + data.get(1).getTaoticket() + "券");
            } else {
                tvZheSecond.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.get(2).getTaoticket())) {
                tvZheThree.setVisibility(View.VISIBLE);
                tvZheThree.setText("返" + data.get(2).getTaoticket() + "券");
            } else {
                tvZheThree.setVisibility(View.GONE);
            }
        } else if (data != null && data.size() == 2) {
            rlFrist.setVisibility(View.VISIBLE);
            rlSecond.setVisibility(View.VISIBLE);
            rlThree.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.get(0).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivFirst, data.get(0).getPicture());
            } else {
                ivFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            if (!TextUtils.isEmpty(data.get(1).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivSecond, data.get(1).getPicture());
            } else {
                ivSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            tvPriceFirst.setText("¥" + data.get(0).getOriginalPrice());
            tvPriceSecond.setText("¥" + data.get(1).getOriginalPrice());
            tvNumFirst.setText("已售" + data.get(0).getSold() + "件");
            tvNumSecond.setText("已售" + data.get(1).getSold() + "件");
            if (!TextUtils.isEmpty(data.get(0).getTaoticket())) {
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText("返" + data.get(0).getTaoticket() + "券");
            } else {
                tvZheFirst.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.get(1).getTaoticket())) {
                tvZheSecond.setVisibility(View.VISIBLE);
                tvZheSecond.setText("返" + data.get(1).getTaoticket() + "券");
            } else {
                tvZheSecond.setVisibility(View.GONE);
            }
        } else if (data != null && data.size() == 1) {
            rlFrist.setVisibility(View.VISIBLE);
            rlSecond.setVisibility(View.INVISIBLE);
            rlThree.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.get(0).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivFirst, data.get(0).getPicture());
            } else {
                ivFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            tvPriceFirst.setText("¥" + data.get(0).getOriginalPrice());
            tvNumFirst.setText("已售" + data.get(0).getSold() + "件");
            if (!TextUtils.isEmpty(data.get(0).getTaoticket())) {
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText("返" + data.get(0).getTaoticket() + "券");
            } else {
                tvZheFirst.setVisibility(View.GONE);
            }
        }

        rlFrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null & data.size() > 0) {
                    listener.onClick(data.get(0).getId());
                }
            }
        });

        rlSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null & data.size() > 1) {
                    listener.onClick(data.get(1).getId());
                }
            }
        });

        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null & data.size() > 2) {
                    listener.onClick(data.get(2).getId());
                }
            }
        });

    }

    public interface ITTLocalImageHolderListener {
        void onClick(String id);
    }
}
