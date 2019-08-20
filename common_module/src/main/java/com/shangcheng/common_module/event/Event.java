package com.shangcheng.common_module.event;

import android.os.Bundle;

/**
 * EventBus订阅发布事件
 *
 * @author yangzm
 * @time 2017/12/8
 */
public class Event<T> {
    private int code;
    private T data;
    private Bundle bundle;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Event(int code, Bundle bundle) {
        this.code = code;
        this.bundle = bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setMsg(Bundle bundle) {
        this.bundle = bundle;
    }
}
