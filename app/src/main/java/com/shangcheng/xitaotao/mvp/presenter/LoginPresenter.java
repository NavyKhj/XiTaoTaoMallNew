package com.shangcheng.xitaotao.mvp.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.base.mvp.BasePresenter;
import com.shangcheng.common_module.baseApplication.ConfigType;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.common.model.UserModel;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.http.BaseNormalObserver;
import com.shangcheng.common_module.http.BaseObserver;
import com.shangcheng.common_module.http.HttpResponse;
import com.shangcheng.common_module.http.RetrofitClient;
import com.shangcheng.common_module.http.RxSchedulers;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.home_module.entity.HomeListProductBean;
import com.shangcheng.xitaotao.http.ApiService;
import com.shangcheng.xitaotao.mvp.contract.LoginContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONObject;

import java.util.WeakHashMap;

import io.reactivex.Observable;

import static com.shangcheng.xitaotao.ui.LoginRegisterActivity.TYPE_REGISTER;

/**
 * @author Navy
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mMvpView 绑定View
     */
    public LoginPresenter(LoginContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void login(final BaseMvpActivity activity, String userName, String verifyCode, String type) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("phone", userName);
        inputBody.put("verifyCode", verifyCode);
        inputBody.put("type", type);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<JSONObject> observable = apiService.loginRegister(inputBody);
        observable.compose(RxSchedulers.<JSONObject>compose())
                .as(AutoDispose.<JSONObject>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<JSONObject>(activity) {
                    @Override
                    public void onHandleSuccess(JSONObject jsonObject) {
                        String code = jsonObject.optJSONObject("data").optString("code");
                        if (TextUtils.equals(TYPE_REGISTER, code)) {
                            getView().loadDataSuccess(TYPE_REGISTER, "login");
                        }else {
                            String token = jsonObject.optJSONObject("data").optString("token");
                            if (!TextUtils.isEmpty(token)) {
                                UserModel userModel = new UserModel();
                                userModel.setAccessToken(token);
                                app.init().withUserInfo(userModel);
                                getUserInfo(activity,token);
                            }
                        }
                    }
                });
    }

    @Override
    public void sendCode(BaseMvpActivity activity, String userName) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("phone", userName);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.sendCode(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>(activity) {
                    @Override
                    public void onHandleSuccess(String string) {
                        T.show("已成功发送验证码");
                        getView().loadDataSuccess("success", "sendCode");
                    }
                });
    }

    @Override
    public void getUserInfo(BaseMvpActivity activity, final String token) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<UserModel> observable = apiService.getUserInfo(inputBody);
        observable.compose(RxSchedulers.<UserModel>compose())
                .as(AutoDispose.<UserModel>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<UserModel>(activity) {
                    @Override
                    public void onHandleSuccess(UserModel userModel) {
                        if (userModel == null || TextUtils.isEmpty(userModel.getNickName())) {
                            T.show("获取用户信息失败，请重新登录");
                            return;
                        }
                        userModel.setAccessToken(token);
                        app.init().withUserInfo(userModel);
                        T.show("登录成功");
                        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.REFRESH_ACCOUNT));
                        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.REFRESH_USERINFO));
                        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.MY));
                        getView().loadDataSuccess("success", "getUserInfo");
                    }
                });
    }

    @Override
    public void removeVerifycode(BaseMvpActivity activity, String phone) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("phone", phone);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.removeVerifycode(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>() {
                    @Override
                    public void onHandleSuccess(String string) {
                        getView().loadDataSuccess("success", "removeVerifycode");
                    }
                });
    }

    @Override
    public void getAccessToken(BaseMvpActivity activity, String code) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("appid", (String) app.init().getConfiguration(ConfigType.WX_APP_ID));
        inputBody.put("secret", (String) app.init().getConfiguration(ConfigType.WX_APP_SECRET));
        inputBody.put("code", code);
        inputBody.put("grant_type", "authorization_code");
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.getAccessToken(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>() {
                    @Override
                    public void onHandleSuccess(String string) {

                        getView().loadDataSuccess("success", "removeVerifycode");
                    }
                });
    }
}
