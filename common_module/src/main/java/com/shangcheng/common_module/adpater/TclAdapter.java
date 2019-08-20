package com.shangcheng.common_module.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.common.model.TagEntity;
import com.shangcheng.common_module.databinding.BasecommonTclItemBinding;
import com.shangcheng.common_module.utils.DimensConstant;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.ViewUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingchao on 2018-4-30.
 * 标签 adapter
 */

public class TclAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private BasecommonTclItemBinding itemBinding;
    private List<TagEntity> tclList = new ArrayList();
    private ViewUtil viewUtil;
    private int selectPosition = 0;

    public void setSelectPosition(int positin) {
        this.selectPosition = positin;
        notifyDataSetChanged();
    }

    public TclAdapter(Context context, List<TagEntity> arrayList) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        tclList = arrayList;
        viewUtil = new ViewUtil(mContext);
    }

    @Override
    public int getCount() {
        return tclList.size();
    }

    @Override
    public Object getItem(int position) {
        return tclList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemBinding = DataBindingUtil.inflate(inflater, R.layout.basecommon_tcl_item, parent, false);
            ScreenMeasureUtil.getInstance().setMViewPadding(itemBinding.tag, DimensConstant.DimensScale.DIMENS_32, DimensConstant.DimensScale.DIMENS_8,
                    DimensConstant.DimensScale.DIMENS_32, DimensConstant.DimensScale.DIMENS_8);
            if (position == selectPosition) {
                DevShapeUtils.shape(DevShape.RECTANGLE)
                        .solid(R.color.normal_content_background_color)
                        .line(DevShapeUtils.STROKE_SMALL, ContextCompat.getColor(mContext, R.color.bank_btn_transparent))
                        .radius(DevShapeUtils.ROUND_SMALL)
                        .into(itemBinding.tag);
                itemBinding.tag.setTextColor(ContextCompat.getColor(mContext, R.color.bank_btn_noraml));
            } else {
                DevShapeUtils.shape(DevShape.RECTANGLE)
                        .solid(R.color.normal_content_background_color)
                        .line(DevShapeUtils.STROKE_SMALL, ContextCompat.getColor(mContext, R.color.mark_transparent))
                        .radius(DevShapeUtils.ROUND_SMALL)
                        .into(itemBinding.tag);
                itemBinding.tag.setTextColor(ContextCompat.getColor(mContext, R.color.normal_body_font_color));
            }
            convertView = itemBinding.getRoot();
            convertView.setTag(itemBinding);
        } else {
            itemBinding = (BasecommonTclItemBinding) convertView.getTag();
        }
        itemBinding.tag.setText(tclList.get(position).getName());
        return convertView;
    }
}
