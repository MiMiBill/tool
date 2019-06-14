package com.zkys.operationtool.bean;

public class UnLockInfo {

        /**
         * code : 200
         * object : {"msg":"通讯成功","code":200,"data":""}
         */

        private int code;
        private ObjectBean object;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ObjectBean getObject() {
            return object;
        }

        public void setObject(ObjectBean object) {
            this.object = object;
        }

        public static class ObjectBean {
            /**
             * msg : 通讯成功
             * code : 200
             * data :
             */

            private String msg;
            private int code;
            private String data;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }

}
