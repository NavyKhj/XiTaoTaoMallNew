package com.shangcheng.home_module.mvp.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.shangcheng.common_module.aspectj.CheckLogin;
import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.base.mvp.BasePresenter;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.common.model.UserModel;
import com.shangcheng.common_module.http.BaseNormalObserver;
import com.shangcheng.common_module.http.BaseObserver;
import com.shangcheng.common_module.http.HttpResponse;
import com.shangcheng.common_module.http.RetrofitClient;
import com.shangcheng.common_module.http.RxSchedulers;
import com.shangcheng.common_module.utils.GoodsUtils;
import com.shangcheng.common_module.utils.LogUtil;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.home_module.entity.HomeListProductBean;
import com.shangcheng.home_module.entity.ProductDetailBean;
import com.shangcheng.home_module.entity.SelectConditionBeanEntity;
import com.shangcheng.home_module.entity.SelectRuleResultBean;
import com.shangcheng.home_module.http.ApiService;
import com.shangcheng.home_module.mvp.contract.ProductDetailContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONObject;

import java.util.List;
import java.util.WeakHashMap;

import io.reactivex.Observable;

/**
 * @author Navy
 */
public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.View> implements ProductDetailContract.Presenter {
    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mMvpView 绑定View
     */
    public ProductDetailPresenter(ProductDetailContract.View mMvpView) {
        super(mMvpView);
    }

    @CheckLogin
    @Override
    public void getSelectRule(BaseMvpActivity activity, String id) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("id", id);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<HttpResponse<SelectConditionBeanEntity>> observable = apiService.getSelectRule(inputBody);
        observable.compose(RxSchedulers.<HttpResponse<SelectConditionBeanEntity>>compose())
                .as(AutoDispose.<HttpResponse<SelectConditionBeanEntity>>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseObserver<SelectConditionBeanEntity>(activity) {
                    @Override
                    protected void onSuccess(SelectConditionBeanEntity selectConditionBeanEntity) {
                        getView().loadDataSuccess(selectConditionBeanEntity, "getSelectRule");
                    }
                });
    }

    @Override
    public void getProductDetail(BaseMvpActivity activity, String id) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("id", id);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<HttpResponse<ProductDetailBean>> observable = apiService.getDetail(inputBody);
        observable.compose(RxSchedulers.<HttpResponse<ProductDetailBean>>compose())
                .as(AutoDispose.<HttpResponse<ProductDetailBean>>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseObserver<ProductDetailBean>(activity) {
                    @Override
                    protected void onSuccess(ProductDetailBean productDetailBean) {
                        getView().loadDataSuccess(productDetailBean, "getDetail");
                    }
                });
    }

    @CheckLogin
    @Override
    public void checkStock(final BaseMvpActivity activity, final ProductDetailBean detailBean, SelectRuleResultBean resultBean, List<SelectConditionBeanEntity.SelectConditionBean> selectList, String userId) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("goodsId", detailBean.getId());
        inputBody.put("buyNum", String.valueOf(resultBean.getNum()));
        for (int i = 0; i < selectList.size(); i++) {
            if (i == 0) {
                inputBody.put("specsItemId1", resultBean.getIds().get(i));
            } else if (i == 1) {
                inputBody.put("specsItemId2", resultBean.getIds().get(i));
            }
        }
        if (!TextUtils.isEmpty(userId)) {
            inputBody.put("changeHandsUserId", userId);
        }
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.checkStock(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>() {
                    @Override
                    public void onHandleSuccess(String s) {
                        String prepareId =  new JSONObject().optJSONObject("data").optString("prepareId");
                        if (GoodsUtils.isTypeTP(detailBean.getAreaCode())) {
                            checkTaopiAuth(activity,prepareId);
                        } else {
                            checkAccount(activity,prepareId);
                        }
                    }
                });
    }

    @Override
    public void checkTaopiAuth(final BaseMvpActivity activity, final String prepareId) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        inputBody.put("prepareId", prepareId);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.checkTaopiAuth(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>() {
                    @Override
                    public void onHandleSuccess(String s) {
                        checkAccount(activity, prepareId);
                    }
                });
    }

    @Override
    public void checkAccount(BaseMvpActivity activity, final String prepareId) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<String> observable = apiService.checkAccount(inputBody);
        observable.compose(RxSchedulers.<String>compose())
                .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<String>() {
                    @Override
                    public void onHandleSuccess(String s) {
                        getView().loadDataSuccess(prepareId,"checkAccount");
                    }
                });
    }

    @Override
    public void getUserInfo(BaseMvpActivity activity) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<UserModel> observable = apiService.getUserInfo(inputBody);
        observable.compose(RxSchedulers.<UserModel>compose())
                .as(AutoDispose.<UserModel>autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new BaseNormalObserver<UserModel>() {
                    @Override
                    public void onHandleSuccess(UserModel userModel) {
                        if (userModel == null || TextUtils.isEmpty(userModel.getNickName())) {
                            return;
                        }
                        userModel.setAccessToken(app.init().getUserInfo().getAccessToken());
                        app.init().withUserInfo(userModel);
                        if (!TextUtils.isEmpty(app.init().getUserInfo().getLevel()) && Integer.parseInt(app.init().getUserInfo().getLevel()) > 0) {
                            getView().loadDataSuccess("success","getUserInfo");
                        } else {
                            T.show("V0会员没有淘批权益，请购买专属产品升级");
                        }
                    }
                });
    }
}
