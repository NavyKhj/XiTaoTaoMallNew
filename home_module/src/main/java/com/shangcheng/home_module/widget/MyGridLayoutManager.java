package com.shangcheng.home_module.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：自定义 GridLayoutManager
 * 创建人：niuyunwang
 * 创建时间：2019/4/1 11:06
 * 修改人：niuyunwang
 * 修改时间：2019/4/1 11:06
 * 修改备注：暂无
 */
public class MyGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;
    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }


}
