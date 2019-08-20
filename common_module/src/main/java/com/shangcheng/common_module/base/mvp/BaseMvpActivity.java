package com.shangcheng.common_module.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.mvp.service.IBaseView;

/**
 * @author Navy
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {
    /**
     * presenter 具体的presenter由子类确定
     */
    protected P mPresenter;

    /**
     * MVP初始化
     *
     * @return mPresenter
     */
    public abstract P initPresenter();

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        initMvp();
    }

    @Override
    public void initMvp() {
        mPresenter = (P) initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachViewM();
        }
    }
}
