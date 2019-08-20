package com.shangcheng.home_module.mvp.contract;

import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.BaseFragment;
import com.shangcheng.common_module.base.mvp.service.IBaseView;

/**
 * @author Navy
 */
public interface HomeContract {
    interface View extends IBaseView {

    }

    interface Presenter {
        void getAreaProduct(BaseFragment fragment);

        void getHotList(BaseFragment fragment, int page);
    }
}
