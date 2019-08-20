package com.shangcheng.common_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.shangcheng.common_module.R;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;

import pl.droidsonroids.gif.GifImageView;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：
 * 创建人：niuyunwang
 * 创建时间：2019/4/28 15:31
 * 修改人：niuyunwang
 * 修改时间：2019/4/28 15:31
 * 修改备注：暂无
 */
public class RefreshHeadView extends RelativeLayout {
    private Context mContext;
    private boolean showGif;
    private boolean isHomeBg;
    private boolean showTopView;
    private String title;
    private GifImageView ivGif;
    private TextView tvHint;
    private RelativeLayout layout;
    private View topView;
    private int textColor;

    public RefreshHeadView(Context context) {
        super(context);
        initView(context, null);
    }

    public RefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshHeadView);
            title = typedArray.getString(R.styleable.RefreshHeadView_title);
            showGif = typedArray.getBoolean(R.styleable.RefreshHeadView_show_gif, true);
            isHomeBg = typedArray.getBoolean(R.styleable.RefreshHeadView_ishomeBg, false);
            showTopView = typedArray.getBoolean(R.styleable.RefreshHeadView_show_top, false);
            textColor = typedArray.getColor(R.styleable.RefreshHeadView_textColor, ContextCompat.getColor(context, R.color.normal_body_font_color));
            typedArray.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.basecommon_refresh_top_layout, this);
        topView = findViewById(R.id.top_view);
        ivGif = findViewById(R.id.iv_gif);
        tvHint = findViewById(R.id.tv_hint);
        tvHint.setTextColor(textColor);
        layout = findViewById(R.id.refresh_layout);
        if (showTopView) {
            topView.setVisibility(View.VISIBLE);
        } else {
            topView.setVisibility(View.GONE);
        }
        if (showGif) {
            ivGif.setVisibility(View.VISIBLE);
        } else {
            ivGif.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            tvHint.setText(title);
        }
        if (isHomeBg) {
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .gradient(R.color.refresh_top_start, R.color.refresh_top_end)
                    .solid(ColorUtil.getAttrColor(R.color.transparent_color, mContext))
                    .into(layout);
        }


    }

    public GifImageView getGifView() {
        return ivGif;
    }

    public TextView getTextView() {
        return tvHint;
    }
}
