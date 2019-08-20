package com.shangcheng.xitaotao.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.base.mvp.service.IBaseView;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.xitaotao.R;
import com.shangcheng.xitaotao.databinding.ActivityLoginBinding;
import com.shangcheng.xitaotao.mvp.contract.LoginContract;
import com.shangcheng.xitaotao.mvp.presenter.LoginPresenter;

/**
 * @author Navy
 */
public class LoginPswActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    ActivityLoginBinding binding;

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void initData(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.loginRegister.setOnClickListener(this);
    }

    @Override
    public void loadDataSuccess(Object data, String requestTag) {
        T.show((String)data);
    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register:
//                mPresenter.login("", "");
                break;
            case 0:
                break;
            default:
                break;
        }
    }
}
