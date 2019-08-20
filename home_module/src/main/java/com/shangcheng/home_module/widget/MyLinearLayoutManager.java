package com.shangcheng.home_module.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：自定义LinearLayoutManager
 * 创建人：niuyunwang
 * 创建时间：2019/4/1 11:04
 * 修改人：niuyunwang
 * 修改时间：2019/4/1 11:04
 * 修改备注：暂无
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
