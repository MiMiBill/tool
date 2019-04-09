package com.zkys.operationtool.bean;

import com.zkys.operationtool.util.DateUtil;

public class DeviceInfoBean {

    /**
     * orderId : "3553242"
     * padStatus : 正常
     * orderMoney : 0.01
     * powerSid : 10086010
     * padDid : 353769223605623
     * pageSize : 20
     * pageNum : 1
     * padBid : 2018120000094
     * holderZid : 10086010
     * cabinetSignal : 87
     * iccid : 8986011705310110201
     * orderCreateDate : 1552027002
     * lockStatus : 异常
     * cabinetHeart : 2019-03-06
     * cabinetDid : 1000000010
     * orderExpireDate : 1552113402
     * activeDate : 2019-03-06
     * "activeUser": "string"
     * "padLastHeart": "string"
     * "padSignal": "string"
     * orderId : "123112"
     * nickname : "xxx"
     */


    private String padStatus = "";
    private String orderMoney = "";
    private String powerSid = "";
    private String padDid = "";
    private int pageSize;
    private int pageNum;
    private String padBid = "";
    private String holderZid = "";
    private String cabinetSignal = "";
    private String iccid = "";
    private String orderCreateDate = "";
    private String lockStatus = "";
    private String cabinetHeart = "";
    private String cabinetDid = "";
    private String orderExpireDate = "";
    private String activeDate = "";
    private String activeUser = "";
    private String padLastHeart = "";
    private String padSignal = "";
    private String nickname = "";
    private String orderId = "";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    public String getPadLastHeart() {
        return padLastHeart;
    }

    public void setPadLastHeart(String padLastHeart) {
        this.padLastHeart = padLastHeart;
    }

    public String getPadSignal() {
        return padSignal;
    }

    public void setPadSignal(String padSignal) {
        this.padSignal = padSignal;
    }

    public String getPadStatus() {
        return padStatus;
    }

    public void setPadStatus(String padStatus) {
        this.padStatus = padStatus;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getPowerSid() {
        return powerSid;
    }

    public void setPowerSid(String powerSid) {
        this.powerSid = powerSid;
    }

    public String getPadDid() {
        return padDid;
    }

    public void setPadDid(String padDid) {
        this.padDid = padDid;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getPadBid() {
        return padBid;
    }

    public void setPadBid(String padBid) {
        this.padBid = padBid;
    }

    public String getHolderZid() {
        return holderZid;
    }

    public void setHolderZid(String holderZid) {
        this.holderZid = holderZid;
    }

    public String getCabinetSignal() {
        return cabinetSignal;
    }

    public void setCabinetSignal(String cabinetSignal) {
        this.cabinetSignal = cabinetSignal;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getOrderCreateDate() {
        return DateUtil.timeStamp2Date(orderCreateDate, null);
    }

    public void setOrderCreateDate(String orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getCabinetHeart() {
        return cabinetHeart;
    }

    public void setCabinetHeart(String cabinetHeart) {
        this.cabinetHeart = cabinetHeart;
    }

    public String getCabinetDid() {
        return cabinetDid;
    }

    public void setCabinetDid(String cabinetDid) {
        this.cabinetDid = cabinetDid;
    }

    public String getOrderExpireDate() {
        return DateUtil.timeStamp2Date(orderExpireDate, null);
    }

    public void setOrderExpireDate(String orderExpireDate) {
        this.orderExpireDate = orderExpireDate;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }
}
