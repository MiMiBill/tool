package com.zkys.operationtool.bean;

import java.io.Serializable;

public class ItemUsageRatesBean implements Serializable {
    /**
     * activationCount : 15
     * createTime : 2019-07-20
     * deviceRentCount : 0
     * deviceRentRate : 0
     * hospitalId : 4
     * hospitalName : 东莞大朗医院
     * lockActivationCount : 7
     * lockOffLineCount : 7
     * lockOnCount : 0
     * lockOnRate : 0
     * offLineCount : 15
     * onCount : 0
     * onRate : 0
     * padRentCount : 0
     * padRentRate : 0
     * padUseCount : 0
     * padUseRate : 0
     */
    private String deptName;
    private int deptId;
    private int activationCount;
    private String createTime;
    private String deviceRentCount;
    private String deviceRentRate;
    private int hospitalId;
    private String hospitalName;
    private int lockActivationCount;
    private int lockOffLineCount;
    private String lockOnCount;
    private String lockOnRate;
    private int offLineCount;
    private String onCount;
    private String onRate;
    private String padRentCount;
    private String padRentRate;
    private String padUseCount;
    private String padUseRate;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getActivationCount() {
        return activationCount;
    }

    public void setActivationCount(int activationCount) {
        this.activationCount = activationCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeviceRentCount() {
        return deviceRentCount;
    }

    public void setDeviceRentCount(String deviceRentCount) {
        this.deviceRentCount = deviceRentCount;
    }

    public String getDeviceRentRate() {
        return deviceRentRate;
    }

    public void setDeviceRentRate(String deviceRentRate) {
        this.deviceRentRate = deviceRentRate;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getLockActivationCount() {
        return lockActivationCount;
    }

    public void setLockActivationCount(int lockActivationCount) {
        this.lockActivationCount = lockActivationCount;
    }

    public int getLockOffLineCount() {
        return lockOffLineCount;
    }

    public void setLockOffLineCount(int lockOffLineCount) {
        this.lockOffLineCount = lockOffLineCount;
    }

    public String getLockOnCount() {
        return lockOnCount;
    }

    public void setLockOnCount(String lockOnCount) {
        this.lockOnCount = lockOnCount;
    }

    public String getLockOnRate() {
        return lockOnRate;
    }

    public void setLockOnRate(String lockOnRate) {
        this.lockOnRate = lockOnRate;
    }

    public int getOffLineCount() {
        return offLineCount;
    }

    public void setOffLineCount(int offLineCount) {
        this.offLineCount = offLineCount;
    }

    public String getOnCount() {
        return onCount;
    }

    public void setOnCount(String onCount) {
        this.onCount = onCount;
    }

    public String getOnRate() {
        return onRate;
    }

    public void setOnRate(String onRate) {
        this.onRate = onRate;
    }

    public String getPadRentCount() {
        return padRentCount;
    }

    public void setPadRentCount(String padRentCount) {
        this.padRentCount = padRentCount;
    }

    public String getPadRentRate() {
        return padRentRate;
    }

    public void setPadRentRate(String padRentRate) {
        this.padRentRate = padRentRate;
    }

    public String getPadUseCount() {
        return padUseCount;
    }

    public void setPadUseCount(String padUseCount) {
        this.padUseCount = padUseCount;
    }

    public String getPadUseRate() {
        return padUseRate;
    }

    public void setPadUseRate(String padUseRate) {
        this.padUseRate = padUseRate;
    }
}
