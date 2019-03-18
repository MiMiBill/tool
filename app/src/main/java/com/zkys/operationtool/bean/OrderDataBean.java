package com.zkys.operationtool.bean;

import java.util.List;

public class OrderDataBean {

    /**
     * moneyTotal : 0.1
     * array : [{"deptName":"内科","agentId":25,"randomnumber":"1551249889045","passwordTime":0,"num":1,"deptid":1,"devicetype":2,"termId":106,"password":0,"hospitalId":4,"payPrice":0.1,"nickname":"Will","id":101313,"prepayId":"wx28134619956449617267d15c3883782878","termDesc":"1","bedNumber":"01","leaseTime":1551332779,"noticeNum":0,"mobile":"","hospitalName":"东莞大朗医院","userId":10029,"returnTime":1551335679,"isconvention":1,"unit":2,"expireTime":1551419179,"createTime":1551332779,"cabinetCode":"1000000010","deposit":1,"status":4}]
     * orderCount : 1
     */

    private double moneyTotal;
    private int orderCount;
    private List<ArrayBean> array;

    public double getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(double moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public List<ArrayBean> getArray() {
        return array;
    }

    public void setArray(List<ArrayBean> array) {
        this.array = array;
    }

    public static class ArrayBean {
        /**
         * deptName : 内科
         * agentId : 25
         * randomnumber : 1551249889045
         * passwordTime : 0
         * num : 1
         * deptid : 1
         * devicetype : 2
         * termId : 106
         * password : 0
         * hospitalId : 4
         * payPrice : 0.1
         * nickname : Will
         * id : 101313
         * prepayId : wx28134619956449617267d15c3883782878
         * termDesc : 1
         * bedNumber : 01
         * leaseTime : 1551332779
         * noticeNum : 0
         * mobile :
         * hospitalName : 东莞大朗医院
         * userId : 10029
         * returnTime : 1551335679
         * isconvention : 1
         * unit : 2
         * expireTime : 1551419179
         * createTime : 1551332779
         * cabinetCode : 1000000010
         * deposit : 1
         * status : 4
         */

        private String deptName;
        private int agentId;
        private String randomnumber;
        private long passwordTime;
        private int num;
        private int deptid;
        private int devicetype;
        private int termId;
        private int password;
        private int hospitalId;
        private double payPrice;
        private String nickname;
        private int id;
        private String prepayId;
        private String termDesc;
        private String bedNumber;
        private long leaseTime;
        private int noticeNum;
        private String mobile;
        private String hospitalName;
        private int userId;
        private long returnTime;
        private int isconvention;
        private int unit;
        private long expireTime;
        private long createTime;
        private String cabinetCode;
        private int deposit;
        private int status;

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getRandomnumber() {
            return randomnumber;
        }

        public void setRandomnumber(String randomnumber) {
            this.randomnumber = randomnumber;
        }

        public long getPasswordTime() {
            return passwordTime;
        }

        public void setPasswordTime(int passwordTime) {
            this.passwordTime = passwordTime;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getDeptid() {
            return deptid;
        }

        public void setDeptid(int deptid) {
            this.deptid = deptid;
        }

        public int getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(int devicetype) {
            this.devicetype = devicetype;
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

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getBedNumber() {
            return bedNumber;
        }

        public void setBedNumber(String bedNumber) {
            this.bedNumber = bedNumber;
        }

        public long getLeaseTime() {
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(int returnTime) {
            this.returnTime = returnTime;
        }

        public int getIsconvention() {
            return isconvention;
        }

        public void setIsconvention(int isconvention) {
            this.isconvention = isconvention;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getCabinetCode() {
            return cabinetCode;
        }

        public void setCabinetCode(String cabinetCode) {
            this.cabinetCode = cabinetCode;
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
}
