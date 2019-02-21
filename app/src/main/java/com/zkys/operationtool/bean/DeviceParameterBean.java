package com.zkys.operationtool.bean;

public class DeviceParameterBean {

    public DeviceParameterBean(String code, String mobileCode, int type) {
        this.code = code;
        this.mobileCode = mobileCode;
        this.type = type;
    }

    /**
     * code : string
     * mobileCode : string
     * type : 0
     */


    private String code = "";
    private String mobileCode = "";
    private int type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
