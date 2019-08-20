package com.shangcheng.common_module.http;


import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.NetUtil;
import com.shangcheng.common_module.utils.T;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author khj
 * @date 2017-12-25
 */

public class RxSchedulers {
    public static <V> ObservableTransformer<V, V> compose() {
        return new ObservableTransformer<V, V>() {
            @Override
            public ObservableSource<V> apply(Observable<V> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) {
                                if (!NetUtil.checkConnection()) {
                                    T.show("请先连接网络");
                                }
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> composeCache() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
//                                new DiskCacheManager<T>(app.getApplication()).put("1", t);
                            }
                        });
            }
        };
    }
}
