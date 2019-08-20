package com.shangcheng.common_module.utils.shapeutils.interfaces;

import android.view.View;

/**
 * 样式接口
 *
 * @author yangzm
 * @time 2018/4/3
 */
public interface IDevUtils<T,V extends View> {

    /**
     * 直接设置样式到view
     *
     * @param v 需要设置样式的view
     */
    void into(V v);



    /**
     * 返回Drawable样式
     *
     * @return T
     */
    T build();
}
