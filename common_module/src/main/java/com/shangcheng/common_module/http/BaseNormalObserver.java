package com.shangcheng.common_module.http;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.shangcheng.common_module.utils.dalog.CustomProgressDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Navy
 */
public abstract class BaseNormalObserver<T> implements Observer<T>, CustomProgressDialog.IProgressCancelListener {
    private static final String TAG = "Observer";
    private CustomProgressDialog dialogWait = null;

    protected BaseNormalObserver() {
    }

    protected BaseNormalObserver(Context context) {
        dialogWait = CustomProgressDialog.createDialog(context,this);
        if (!((Activity) context).isFinishing()) {
            dialogWait.show();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    @Override
    public void onNext(T t) {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
        if (t != null) {
            onHandleSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
        Log.e(TAG, "error:" + e.toString());
    }

    public abstract void onHandleSuccess(T t);

    void onHandleError(String state, String msg) {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
        Log.i(TAG, "state>>>>>" + state + ":msg>>>>>" + msg);
    }

    @Override
    public void onCancelProgress() {
        onComplete();
    }
}
