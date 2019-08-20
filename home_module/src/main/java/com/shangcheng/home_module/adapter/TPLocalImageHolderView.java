package com.shangcheng.home_module.adapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import com.shangcheng.home_module.entity.HomeProductBean;

import java.util.List;

/**
 * @author Navy
 */
public class TPLocalImageHolderView extends Holder<List<HomeProductBean>> {
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

    private ITPLocalImageHolderListener listener;
    public TPLocalImageHolderView(View itemView,ITPLocalImageHolderListener listener) {
        super(itemView);
        imageLoaderUtil = new ImageLoaderUtil();
        this.listener = listener;
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

        tvPriceFirst = view.findViewById(R.id.tv_taopi_price_first);
        tvPriceSecond = view.findViewById(R.id.tv_taopi_price_second);
        tvPriceThree = view.findViewById(R.id.tv_taopi_price_three);

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
        RelativeSizeSpan stylte = new RelativeSizeSpan(0.8f);
        StyleSpan styleSpan=new StyleSpan(Typeface.ITALIC);
        if(data!=null&&data.size()>=3){
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

            String first="¥"+data.get(0).getPrice()+"  +"+data.get(0).getTaoticket()+"券";
            String second="¥"+data.get(1).getPrice()+"  +"+data.get(1).getTaoticket()+"券";
            String three="¥"+data.get(2).getPrice()+"  +"+data.get(2).getTaoticket()+"券";

            SpannableString sp1=new SpannableString(first);
            int start_frist=first.indexOf("+");
            sp1.setSpan(stylte,start_frist,first.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString sp2=new SpannableString(second);
            int start_second=second.indexOf("+");
            sp2.setSpan(stylte,start_second,second.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString sp3=new SpannableString(three);
            int start_three=three.indexOf("+");
            sp3.setSpan(stylte,start_three,three.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPriceFirst.setText(sp1);
            tvPriceSecond.setText(sp2);
            tvPriceThree.setText(sp3);

            tvNumFirst.setText("仅剩"+data.get(0).getStock()+"件");
            tvNumSecond.setText("仅剩"+data.get(1).getStock()+"件");
            tvNumThree.setText("仅剩"+data.get(2).getStock()+"件");
            if(!TextUtils.isEmpty(data.get(0).getDiscount())){
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText(data.get(0).getDiscount()+"折");
            }else{
                tvZheFirst.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.get(1).getDiscount())){
                tvZheSecond.setVisibility(View.VISIBLE);
                tvZheSecond.setText(data.get(1).getDiscount()+"折");
            }else{
                tvZheSecond.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.get(2).getDiscount())){
                tvZheThree.setVisibility(View.VISIBLE);
                tvZheThree.setText(data.get(2).getDiscount()+"折");
            }else{
                tvZheThree.setVisibility(View.GONE);
            }
        }else if(data!=null&&data.size()==2){
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

            String first="¥"+data.get(0).getPrice()+"  +"+data.get(0).getTaoticket()+"券";
            String second="¥"+data.get(1).getPrice()+"  +"+data.get(1).getTaoticket()+"券";


            SpannableString sp1=new SpannableString(first);
            int start_frist=first.indexOf("+");
            sp1.setSpan(stylte,start_frist,first.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString sp2=new SpannableString(second);
            int start_second=second.indexOf("+");
            sp2.setSpan(stylte,start_second,second.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            tvPriceFirst.setText(sp1);
            tvPriceSecond.setText(sp2);

            tvNumFirst.setText("仅剩"+data.get(0).getStock()+"件");
            tvNumSecond.setText("仅剩"+data.get(1).getStock()+"件");
            if(!TextUtils.isEmpty(data.get(0).getDiscount())){
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText(data.get(0).getDiscount()+"折");
            }else{
                tvZheFirst.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.get(1).getDiscount())){
                tvZheSecond.setVisibility(View.VISIBLE);
                tvZheSecond.setText(data.get(1).getDiscount()+"折");
            }else{
                tvZheSecond.setVisibility(View.GONE);
            }

        }else if(data!=null&&data.size()==1){
            rlFrist.setVisibility(View.VISIBLE);
            rlSecond.setVisibility(View.INVISIBLE);
            rlThree.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.get(0).getPicture())) {
                imageLoaderUtil.loadImage(app.getApplication(), ivFirst, data.get(0).getPicture());
            } else {
                ivFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
            }
            String first="¥"+data.get(0).getPrice()+"  +"+data.get(0).getTaoticket()+"券";
            SpannableString sp1=new SpannableString(first);
            int start_frist=first.indexOf("+");
            sp1.setSpan(stylte,start_frist,first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvPriceFirst.setText(sp1);

            tvNumFirst.setText("仅剩"+data.get(0).getStock()+"件");
            if(!TextUtils.isEmpty(data.get(0).getDiscount())){
                tvZheFirst.setVisibility(View.VISIBLE);
                tvZheFirst.setText(data.get(0).getDiscount()+"折");
            }else{
                tvZheFirst.setVisibility(View.GONE);
            }


        }

        rlFrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data!=null&data.size()>0){
                    listener.onClick(data.get(0).getId());
                }

            }
        });

        rlSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data!=null&data.size()>1){
                    listener.onClick(data.get(1).getId());
                }
            }
        });

        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data!=null&data.size()>2){
                    listener.onClick(data.get(2).getId());
                }
            }
        });
    }
    public interface ITPLocalImageHolderListener{
        void onClick(String id);
    }
}
