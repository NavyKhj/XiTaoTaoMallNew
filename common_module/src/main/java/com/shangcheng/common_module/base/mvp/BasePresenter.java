package com.shangcheng.common_module.base.mvp;


import com.shangcheng.common_module.base.mvp.service.IBaseView;
import com.shangcheng.common_module.utils.LogUtil;

/**
 * @author Navy
 */
public abstract class BasePresenter<V extends IBaseView> {
    /**
     * 绑定的view
     */
    protected V mMvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mMvpView 绑定View
     */
    public BasePresenter(V mMvpView) {
        this.mMvpView = mMvpView;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachViewM() {
        this.mMvpView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return mMvpView;
    }
}
