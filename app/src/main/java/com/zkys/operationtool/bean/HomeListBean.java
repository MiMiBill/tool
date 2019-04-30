package com.zkys.operationtool.bean;

import java.util.List;

public class HomeListBean {
        /**
         * code : app:menu:yw:active
         * createTime : 1555895354000
         * disabled : 1
         * fatherId : 203
         * id : 204
         * list : []
         * name : 激活设备
         * pathUrl : *
         * sortno : 1
         * type : 2
         * updateTime : 1556024422000
         */

        private String code;
        private long createTime;
        private int disabled;
        private int fatherId;
        private int id;
        private String name;
        private String pathUrl;
        private int sortno;
        private String type;
        private long updateTime;
        private List<?> list;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDisabled() {
            return disabled;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public int getFatherId() {
            return fatherId;
        }

        public void setFatherId(int fatherId) {
            this.fatherId = fatherId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPathUrl() {
            return pathUrl;
        }

        public void setPathUrl(String pathUrl) {
            this.pathUrl = pathUrl;
        }

        public int getSortno() {
            return sortno;
        }

        public void setSortno(int sortno) {
            this.sortno = sortno;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
}
