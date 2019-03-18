package com.zkys.operationtool.bean;

public class DeviceParameterBean {

    public DeviceParameterBean(String code, String simMobile, int type) {
        this.code = code;
        this.simMobile = simMobile;
        this.type = type;
    }

    /**
     * code : string
     * simMobile : string
     * type : 0
     */


    private String code = "";
    private String simMobile = "";
    private int type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimMobile() {
        return simMobile;
    }

    public void setSimMobile(String simMobile) {
        this.simMobile = simMobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
