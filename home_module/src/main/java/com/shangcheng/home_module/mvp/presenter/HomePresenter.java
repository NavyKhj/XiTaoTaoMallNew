package com.shangcheng.home_module.mvp.presenter;

import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.BaseFragment;
import com.shangcheng.common_module.base.mvp.BasePresenter;
import com.shangcheng.common_module.common.model.BaseEntity;
import com.shangcheng.common_module.http.BaseNormalObserver;
import com.shangcheng.common_module.http.BaseObserver;
import com.shangcheng.common_module.http.HttpResponse;
import com.shangcheng.common_module.http.RetrofitClient;
import com.shangcheng.common_module.http.RxSchedulers;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.home_module.entity.GoodsBeanEntity;
import com.shangcheng.home_module.entity.HomeListProductBean;
import com.shangcheng.home_module.http.ApiService;
import com.shangcheng.home_module.mvp.contract.HomeContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;
import java.util.WeakHashMap;

import io.reactivex.Observable;

/**
 * @author Navy
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mMvpView 绑定View
     */
    public HomePresenter(HomeContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void getAreaProduct(BaseFragment fragment) {
        WeakHashMap<String, String> inputBody = new WeakHashMap<>();
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<HttpResponse<HomeListProductBean>> observable = apiService.getAreaProduct(inputBody);
        observable.compose(RxSchedulers.<HttpResponse<HomeListProductBean>>compose())
                .as(AutoDispose.<HttpResponse<HomeListProductBean>>autoDisposable(AndroidLifecycleScopeProvider.from(fragment)))
                .subscribe(new BaseObserver<HomeListProductBean>(fragment.getActivity()) {
                    @Override
                    protected void onSuccess(HomeListProductBean homeListProductBean) {
                        getView().loadDataSuccess(homeListProductBean, "getAreaProduct");
                    }
                });
    }

    @Override
    public void getHotList(BaseFragment fragment, final int page) {
        WeakHashMap<String, Object> inputBody = new WeakHashMap<>();
        WeakHashMap<String, String> map = new WeakHashMap<>();
        inputBody.put("pageNo", String.valueOf(page));
        if (page == 1) {
            inputBody.put("pageSize", "10");
        } else {
            inputBody.put("pageSize", "20");
        }
        inputBody.put("property", "createTime");
        inputBody.put("direction", "DESC");
        inputBody.put("page", map);
        ApiService apiService = RetrofitClient.getInstance().creat(ApiService.class);
        Observable<HttpResponse<GoodsBeanEntity>> observable = apiService.getHotList(inputBody);
        observable.compose(RxSchedulers.<HttpResponse<GoodsBeanEntity>>compose())
                .as(AutoDispose.<HttpResponse<GoodsBeanEntity>>autoDisposable(AndroidLifecycleScopeProvider.from(fragment)))
                .subscribe(new BaseObserver<GoodsBeanEntity>() {
                    @Override
                    protected void onSuccess(GoodsBeanEntity goodsBeanList) {
                        if (page == 1) {
                            getView().loadDataSuccess(goodsBeanList, "getHotList");
                        } else {
                            getView().loadDataSuccess(goodsBeanList, "getHotListMore");
                        }
                    }
                });
    }
}
