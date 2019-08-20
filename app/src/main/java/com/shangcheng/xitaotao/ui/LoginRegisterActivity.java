package com.shangcheng.xitaotao.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.dalog.AgreementDialog;
import com.shangcheng.xitaotao.R;
import com.shangcheng.xitaotao.databinding.ActivityLoginRegisterBinding;
import com.shangcheng.xitaotao.mvp.contract.LoginContract;
import com.shangcheng.xitaotao.mvp.presenter.LoginPresenter;

import java.util.Objects;

import qiu.niorgai.StatusBarCompat;

/**
 * @author Navy
 */
public class LoginRegisterActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    ActivityLoginRegisterBinding binding;
    private CountDownTimer timer;
    private boolean isTimer;
    public final static String TYPE_LOGIN = "login";
    public final static String TYPE_REGISTER = "register";

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                String phone = binding.etLoginUser.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    T.show("请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    T.show("请输入正确手机号");
                    return;
                }
                if (!isTimer) {
                    timer.start();
                    mPresenter.sendCode(LoginRegisterActivity.this, phone);
                }
                break;
            case R.id.login_register:
                String userPhone = binding.etLoginUser.getText().toString();
                String verifyCode = binding.etLoginPassword.getText().toString();
                if (TextUtils.isEmpty(userPhone)) {
                    T.show("请输入手机号");
                    return;
                }
                if (userPhone.length() != 11) {
                    T.show("请输入正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    T.show("请输入验证码");
                    return;
                }
                mPresenter.login(LoginRegisterActivity.this, userPhone, verifyCode, TYPE_LOGIN);
                break;
            case R.id.wxLogin:
                break;
            case R.id.please_login_password:
                startActivity(new Intent(LoginRegisterActivity.this, LoginPswActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initData(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_register);
        StatusBarCompat.translucentStatusBar(this, true);
        getToolbar().setBackgroundResource(android.R.color.transparent);
        ScreenMeasureUtil.getInstance().setMViewMargin(getToolbar(), 0, ScreenMeasureUtil.getInstance().getStatusBarHeight(), 0, 0);
        binding.back.setOnClickListener(this);
        binding.wxLogin.setOnClickListener(this);
        binding.tvForget.setOnClickListener(this);
        binding.loginRegister.setOnClickListener(this);
        binding.pleaseLoginPassword.setOnClickListener(this);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvForget.setText(String.valueOf(millisUntilFinished / 1000) + "s后重新获取");
                binding.tvForget.setEnabled(false);
                isTimer = true;
            }

            @Override
            public void onFinish() {
                binding.tvForget.setText("获取验证码");
                binding.tvForget.setEnabled(true);
                isTimer = false;
            }
        };
    }

    @Override
    public void loadDataSuccess(Object data, String requestTag) {
        switch (requestTag) {
            case "login":
                if (TextUtils.equals((String)data,TYPE_REGISTER)){
                    showAgreementDialog();
                }
                break;
            case "sendCode":
                break;
            case "getUserInfo":
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }
    private void showAgreementDialog() {
        final AgreementDialog agreementDialog = new AgreementDialog(this);
        agreementDialog.setOnDialogCancelListener(new AgreementDialog.OnDialogCancelListener() {
            @Override
            public void agree() {
                agreementDialog.dismiss();
                String phone = binding.etLoginUser.getText().toString();
                String verifyCode = binding.etLoginPassword.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    T.show("请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    T.show("请输入正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    T.show("请输入验证码");
                    return;
                }
                mPresenter.login(LoginRegisterActivity.this,phone,verifyCode,TYPE_REGISTER);
            }

            @Override
            public void disAgree() {
                String phone = binding.etLoginUser.getText().toString();
                mPresenter.removeVerifycode(LoginRegisterActivity.this,phone);
                agreementDialog.dismiss();
            }
        });
        agreementDialog.show();
    }
}
