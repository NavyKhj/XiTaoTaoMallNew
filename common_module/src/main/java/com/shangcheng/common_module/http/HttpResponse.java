package com.shangcheng.common_module.http;

import java.io.Serializable;

/**
 * @author khj
 * @date 2018-3-13
 */

public class HttpResponse<T> implements Serializable {
    private String statusCode;
    private String statusMsg;
    private String success;
    private T data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
