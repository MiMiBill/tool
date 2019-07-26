package com.zkys.operationtool.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zkys.operationtool.util.DateUtil;

public class BedOrderStateBean implements MultiItemEntity {



    /**
     "heartStatus": "2",
     "hospitalId": "4",
     "createTime": 1553311222000,
     "deptId": "2",
     "randomNumber": "1552706199735",
     "id": 58,
     "bedNumber": "03",
     "heartStatusName": "异常",
     "deviceStatus": "占用"
     */

    private String heartStatus;
    private String hospitalId;
    private long createTime;
    private String deptId;
    private String bedNumber;
    private String heartStatusName;
    private String deviceStatus;
    private String randomNumber;
    private int id;
    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public class OrderBean{
        private double refundFee;
        private int agentId;
        private int passwordTime;
        private int isConvention;
        private int num;
        private int pageSize;
        private int termId;
        private int password;
        private int expireDay;
        private int hospitalId;
        private int id;
        private double expireFee;
        private double payPrice;
        private String nickname;
        private String randomNumber;
        private String prepayId;
        private String termDesc;
        private int deviceType;
        private int leaseTime;
        private int noticeNum;
        private int pageNum;
        private int deptId;
        private int userId;
        private String mobile;
        private String cabinetCode;
        private String returnTime;
        private int unit;
        private int expireTime;
        private int createTime;
        private int deposit;
        private int status;

        public double getRefundFee() {
            return refundFee;
        }

        public void setRefundFee(double refundFee) {
            this.refundFee = refundFee;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getPasswordTime() {
            return passwordTime;
        }

        public void setPasswordTime(int passwordTime) {
            this.passwordTime = passwordTime;
        }

        public int getIsConvention() {
            return isConvention;
        }

        public void setIsConvention(int isConvention) {
            this.isConvention = isConvention;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTermId() {
            return termId;
        }

        public void setTermId(int termId) {
            this.termId = termId;
        }

        public int getPassword() {
            return password;
        }

        public void setPassword(int password) {
            this.password = password;
        }

        public int getExpireDay() {
            return expireDay;
        }

        public void setExpireDay(int expireDay) {
            this.expireDay = expireDay;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getExpireFee() {
            return expireFee;
        }

        public void setExpireFee(double expireFee) {
            this.expireFee = expireFee;
        }

        public double getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(double payPrice) {
            this.payPrice = payPrice;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRandomNumber() {
            return randomNumber;
        }

        public void setRandomNumber(String randomNumber) {
            this.randomNumber = randomNumber;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getTermDesc() {
            return termDesc;
        }

        public void setTermDesc(String termDesc) {
            this.termDesc = termDesc;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public int getLeaseTime() {
            return leaseTime;
        }

        public void setLeaseTime(int leaseTime) {
            this.leaseTime = leaseTime;
        }

        public int getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(int noticeNum) {
            this.noticeNum = noticeNum;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCabinetCode() {
            return cabinetCode;
        }

        public void setCabinetCode(String cabinetCode) {
            this.cabinetCode = cabinetCode;
        }

        public String getReturnTimeFormat() {
            return DateUtil.timeStamp2Date(String.valueOf(returnTime), null);
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public int getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public String getHeartStatus() {
        if(heartStatus.equals("1")){
            heartStatus="1";
        }else {
            heartStatus="2";
        }
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

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
