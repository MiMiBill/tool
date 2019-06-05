package com.zkys.operationtool.bean;

public class ToolBean {
    /**
     * userid : zkys
     * cmd : control
     * deviceid : 768901006040
     * result : ok
     * info : cmd send ok
     * serialnum : 1559714503
     * sign : 99a9aaa2e32f6b6ae9a4932d0b98197d
     */

    private String userid;
    private String cmd;
    private String deviceid;
    private String result;
    private String info;
    private int serialnum;
    private String sign;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(int serialnum) {
        this.serialnum = serialnum;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
