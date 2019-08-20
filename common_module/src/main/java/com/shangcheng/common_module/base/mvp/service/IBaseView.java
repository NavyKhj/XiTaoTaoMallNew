package com.shangcheng.common_module.base.mvp.service;

/**
 * @author Navy
 */
public interface IBaseView {
    /**
     * 初始化MVP
     */
    void initMvp();

    /**
     * 基础的请求的返回
     *
     * @param requestTag 请求标识
     */
    void loadDataSuccess(Object data, String requestTag);

    /**
     * 基础请求的错误
     *
     * @param e          e
     * @param requestTag 请求标识
     */
    void loadDataError(Throwable e, int requestTag);
}
