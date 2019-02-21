package com.zkys.operationtool.base;

/**
 * Created by DGDL-08 on ${DATA}
 */
@Deprecated
public class HttpResponseOld<T> {
    private int code;
    private long timestamp;
    private String info;
    private String version;
    private T data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
