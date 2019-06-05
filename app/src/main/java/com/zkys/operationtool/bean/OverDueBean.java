package com.zkys.operationtool.bean;

import java.util.List;

public class OverDueBean {
        /**
         * array : [{"agentId":25,"bedNumber":"01","cabinetCode":"1000000010",
         * "createTime":1559198054,"deposit":1,"deptId":1,"deptName":"内科","deviceType":2,
         * "expireDay":0,"expireFee":0,"expireTime":1559284454,"hospitalId":4,
         * "hospitalName":"东莞大朗医院","id":101748,"isConvention":1,"leaseTime":1559198054,
         * "mobile":"","nickname":"袁金玲","pageNum":1,"pageSize":20,"payPrice":0.1,
         * "prepayId":"wx3014341439442120439484e82014813500","randomNumber":"1553756353384",
         * "returnTime":1559200017,"status":3,"termDesc":"测试","termId":212,"unit":2,
         * "userId":10188},{"agentId":25,"bedNumber":"01","cabinetCode":"1000000010",
         * "createTime":1559197176,"deposit":1,"deptId":1,"deptName":"内科","deviceType":2,
         * "expireDay":0,"expireFee":0,"expireTime":1559283576,"hospitalId":4,
         * "hospitalName":"东莞大朗医院","id":101746,"isConvention":1,"leaseTime":1559197176,
         * "mobile":"","nickname":"袁金玲","pageNum":1,"pageSize":20,"payPrice":0.01,
         * "prepayId":"wx301419370120935c2435a8503283011500","randomNumber":"1553756353384",
         * "returnTime":1559197769,"status":3,"termDesc":"测试套餐一","termId":265,"unit":2,
         * "userId":10188}]
         * moneyTotal : 41.84
         * orderCount : 479
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
             * agentId : 25
             * bedNumber : 01
             * cabinetCode : 1000000010
             * createTime : 1559198054
             * deposit : 1
             * deptId : 1
             * deptName : 内科
             * deviceType : 2
             * expireDay : 0
             * expireFee : 0
             * expireTime : 1559284454
             * hospitalId : 4
             * hospitalName : 东莞大朗医院
             * id : 101748
             * isConvention : 1
             * leaseTime : 1559198054
             * mobile :
             * nickname : 袁金玲
             * pageNum : 1
             * pageSize : 20
             * payPrice : 0.1
             * prepayId : wx3014341439442120439484e82014813500
             * randomNumber : 1553756353384
             * returnTime : 1559200017
             * status : 3
             * termDesc : 测试
             * termId : 212
             * unit : 2
             * userId : 10188
             */

            private int agentId;
            private String bedNumber;
            private String cabinetCode;
            private int createTime;
            private int deposit;
            private int deptId;
            private String deptName;
            private int deviceType;
            private int expireDay;
            private double expireFee;
            private int expireTime;
            private int hospitalId;
            private String hospitalName;
            private int id;
            private int isConvention;
            private int leaseTime;
            private String mobile;
            private String nickname;
            private int pageNum;
            private int pageSize;
            private double payPrice;
            private String prepayId;
            private String randomNumber;
            private int returnTime;
            private int status;
            private String termDesc;
            private int termId;
            private int unit;
            private int userId;

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getBedNumber() {
                return bedNumber;
            }

            public void setBedNumber(String bedNumber) {
                this.bedNumber = bedNumber;
            }

            public String getCabinetCode() {
                return cabinetCode;
            }

            public void setCabinetCode(String cabinetCode) {
                this.cabinetCode = cabinetCode;
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

            public int getDeptId() {
                return deptId;
            }

            public void setDeptId(int deptId) {
                this.deptId = deptId;
            }

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public int getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(int deviceType) {
                this.deviceType = deviceType;
            }

            public int getExpireDay() {
                return expireDay;
            }

            public void setExpireDay(int expireDay) {
                this.expireDay = expireDay;
            }

            public double getExpireFee() {
                return expireFee;
            }

            public void setExpireFee(double expireFee) {
                this.expireFee = expireFee;
            }

            public int getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(int expireTime) {
                this.expireTime = expireTime;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsConvention() {
                return isConvention;
            }

            public void setIsConvention(int isConvention) {
                this.isConvention = isConvention;
            }

            public int getLeaseTime() {
                return leaseTime;
            }

            public void setLeaseTime(int leaseTime) {
                this.leaseTime = leaseTime;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public double getPayPrice() {
                return payPrice;
            }

            public void setPayPrice(double payPrice) {
                this.payPrice = payPrice;
            }

            public String getPrepayId() {
                return prepayId;
            }

            public void setPrepayId(String prepayId) {
                this.prepayId = prepayId;
            }

            public String getRandomNumber() {
                return randomNumber;
            }

            public void setRandomNumber(String randomNumber) {
                this.randomNumber = randomNumber;
            }

            public int getReturnTime() {
                return returnTime;
            }

            public void setReturnTime(int returnTime) {
                this.returnTime = returnTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTermDesc() {
                return termDesc;
            }

            public void setTermDesc(String termDesc) {
                this.termDesc = termDesc;
            }

            public int getTermId() {
                return termId;
            }

            public void setTermId(int termId) {
                this.termId = termId;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
}
