package com.zkys.operationtool.base;

public class HttpResponse<T> {
    private int code;
    private String msg;
    private T data;

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int Code) {
        this.code = Code;
    }

    public T getData() {
        return data;
    }

    public void setData(T Data) {
        this.data = Data;
    }
}
