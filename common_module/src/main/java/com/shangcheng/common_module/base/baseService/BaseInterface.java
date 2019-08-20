package com.shangcheng.common_module.base.baseService;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Class description
 * Activity使用的Base基础接口
 *
 * @author WJ
 * @version 1.0, 2018-1-10
 */

public interface BaseInterface extends View.OnClickListener{

    /**
     * 初始化 view前
     */
    void beforeInitView(final Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(final Bundle bundle);

}
