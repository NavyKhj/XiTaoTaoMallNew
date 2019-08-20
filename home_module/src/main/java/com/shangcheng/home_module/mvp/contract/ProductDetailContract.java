package com.shangcheng.home_module.mvp.contract;

import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.base.mvp.service.IBaseView;
import com.shangcheng.home_module.entity.ProductDetailBean;
import com.shangcheng.home_module.entity.SelectConditionBeanEntity;
import com.shangcheng.home_module.entity.SelectRuleResultBean;

import java.util.List;

/**
 * @author Navy
 */
public interface ProductDetailContract {
    interface View extends IBaseView {

    }

    interface Presenter {
        void getSelectRule(BaseMvpActivity activity, String id);

        void getProductDetail(BaseMvpActivity activity, String id);

        /**
         * 检查库存
         * @param activity .
         * @param detailBean 商品
         * @param resultBean 规格选择实体
         * @param selectList 选择列表
         * @param userId 用户id
         */
        void checkStock(BaseMvpActivity activity, ProductDetailBean detailBean, SelectRuleResultBean resultBean, List<SelectConditionBeanEntity.SelectConditionBean> selectList, String userId);

        /**
         * 验证是否有淘批购买权限
         */
        void checkTaopiAuth(BaseMvpActivity activity, String prepareId);

        /**
         * 检查账户
         * @param activity .
         * @param prepareId .
         */
        void checkAccount(BaseMvpActivity activity, String prepareId);

        /**
         * 刷新用户信息
         *
         * @param activity .
         */
        void getUserInfo(BaseMvpActivity activity);
    }
}
