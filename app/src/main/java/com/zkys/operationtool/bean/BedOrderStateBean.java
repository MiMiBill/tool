package com.zkys.operationtool.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zkys.operationtool.util.DateUtil;

public class BedOrderStateBean implements MultiItemEntity {


    /**
     * heartStatus : 1
     * hospitalId : 4
     * createTime : 1551861049000
     * deptId : 1
     * bedNumber : 555
     * heartStatusName : 正常
     * deviceStatus : 正常
     */

    private String heartStatus;
    private String hospitalId;
    private long createTime;
    private String deptId;
    private String bedNumber;
    private String heartStatusName;
    private String deviceStatus;

    public String getHeartStatus() {
        return heartStatus;
    }

    public void setHeartStatus(String heartStatus) {
        this.heartStatus = heartStatus;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCreateTimeFormat() {
        return DateUtil.timeStamp2Date(String.valueOf(createTime/1000), null);
    }
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getHeartStatusName() {
        return heartStatusName;
    }

    public void setHeartStatusName(String heartStatusName) {
        this.heartStatusName = heartStatusName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Override
    public int getItemType() {
        return Integer.parseInt(getHeartStatus());
    }
}
