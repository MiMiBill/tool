package com.zkys.operationtool.bean;

import java.io.Serializable;
import java.util.List;

public class RepairListBean implements Serializable {
        /**
         * bedId : 1
         * bedNumber : 1008
         * deptName : 内科
         * hospName : 东莞大朗医院
         * id : 1
         * repairBedId : 1
         * repairLog : []
         * repairTime : 2019-07-18 17:28:25
         * status : 0
         * submitterId : 110
         * type : 无法开机2，无法关机2
         * result : 已完成
         * solveTime : 1563498934000
         */

        private String bedId;
        private String bedNumber;
        private String deptName;
        private String hospName;
        private int id;
        private int repairBedId;
        private String repairTime;
        private int status;
        private int submitterId;
        private String type;
        private String result;
        private long solveTime;
        private List<LogBean> repairLog;

        public String getBedId() {
            return bedId;
        }

        public void setBedId(String bedId) {
            this.bedId = bedId;
        }

        public String getBedNumber() {
            return bedNumber;
        }

        public void setBedNumber(String bedNumber) {
            this.bedNumber = bedNumber;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getHospName() {
            return hospName;
        }

        public void setHospName(String hospName) {
            this.hospName = hospName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRepairBedId() {
            return repairBedId;
        }

        public void setRepairBedId(int repairBedId) {
            this.repairBedId = repairBedId;
        }

        public String getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(String repairTime) {
            this.repairTime = repairTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSubmitterId() {
            return submitterId;
        }

        public void setSubmitterId(int submitterId) {
            this.submitterId = submitterId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public long getSolveTime() {
            return solveTime;
        }

        public void setSolveTime(long solveTime) {
            this.solveTime = solveTime;
        }

        public List<LogBean> getRepairLog() {
            return repairLog;
        }

        public void setRepairLog(List<LogBean> repairLog) {
            this.repairLog = repairLog;
        }

        public static class LogBean implements Serializable{
            private String result;
            private int conductorId;
            private int id;
            private int rbId;
            private long solveTime;
            private int status;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public int getConductorId() {
                return conductorId;
            }

            public void setConductorId(int conductorId) {
                this.conductorId = conductorId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRbId() {
                return rbId;
            }

            public void setRbId(int rbId) {
                this.rbId = rbId;
            }

            public long getSolveTime() {
                return solveTime;
            }

            public void setSolveTime(long solveTime) {
                this.solveTime = solveTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
}
