package com.shangcheng.common_module.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shangcheng.common_module.base.BaseFragment;
import com.shangcheng.common_module.base.mvp.service.IBaseView;

/**
 * @author Navy
 */
public abstract class BaseMvpFragment<Presenter extends BasePresenter> extends BaseFragment implements IBaseView {
    /**
     * presenter 具体的presenter由子类确定
     */
    protected Presenter mPresenter;

    /**
     * 初始化Presenter
     *
     * @return
     */
    public abstract Presenter initPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMvp();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initMvp() {
        mPresenter = (Presenter) initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachViewM();
        }
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {

    }
}
