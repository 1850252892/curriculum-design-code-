package com.shop.module.user.model;

public class ResponseModel {
    public static int NOTEXISTS=1001;
    public static int SECRETERROR=1002;
    public static int USERLOCK=1003;
    public static int TOKENERROR=1004;
    public static int LOGINSUCEESS=2000;
    public static int ACCOUNTEXISTS=3001;
    public static int PASSWORDLONG=3002;
    public static int REGISTERSUCESS=2002;
    //状态码
    private int code;
    //返回消息
    private String msg;
    //返回数据
    private Object data;

    public ResponseModel() {
    }
    public static ResponseModel newResponseModel(){
        return new ResponseModel();
    }
    public ResponseModel(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
