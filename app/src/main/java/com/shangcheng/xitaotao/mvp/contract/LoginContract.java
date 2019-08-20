package com.shangcheng.xitaotao.mvp.contract;

import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.base.mvp.service.IBaseView;

/**
 * @author Navy
 */
public interface LoginContract {
    interface View extends IBaseView {

    }

    interface Presenter {
        /**
         * 登录
         * @param userName 用户名
         * @param verifyCode 验证码
         */
        void login(BaseMvpActivity activity,String userName, String verifyCode,String type);
        void sendCode(BaseMvpActivity activity,String userName);
        void getUserInfo(BaseMvpActivity activity,String token);
        void removeVerifycode(BaseMvpActivity activity,String phone);
        void getAccessToken(BaseMvpActivity activity,String code);
    }
}
