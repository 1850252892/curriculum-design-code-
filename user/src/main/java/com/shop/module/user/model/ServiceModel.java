package com.shop.module.user.model;

/**
 * 服务返回数据模型
 * @param <T>
 */
public class ServiceModel<T> {
    boolean isSuccess; //是否成功
    String errMsg;//错误提示信息
    T data; //返回结果

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
