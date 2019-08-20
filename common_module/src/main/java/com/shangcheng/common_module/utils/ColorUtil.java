package com.shangcheng.common_module.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

/**
 * 颜色工具类
 *
 * @author lixiaoyu
 * @time 2017/9/20
 */
public class ColorUtil {

    /**
     * 获取attr中的color
     *
     * @param attrId
     * @return
     */
    public static int getAttrColor(int attrId, Context context) {
        int[] colorAttr = new int[]{attrId};
        int indexOfAttrTextSize = 0;
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, colorAttr);
        int color = a.getColor(indexOfAttrTextSize, -1);
        a.recycle();
        return color;
    }


}
