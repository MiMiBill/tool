package com.zkys.operationtool.base;

import com.zkys.operationtool.util.LogFactory;
import com.zkys.operationtool.util.LogOutUtil;

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
        LogFactory.l().i("code==="+code);
        if(code==1001){ //token失效,退出登录
            LogOutUtil.LogOut();
        }
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
