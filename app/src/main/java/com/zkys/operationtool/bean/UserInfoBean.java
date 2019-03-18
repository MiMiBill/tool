package com.zkys.operationtool.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登陆返回数据
 */
public class UserInfoBean implements Parcelable {

    /**
     * roleId : 68
     * mobile : 18211111111
     * pageSize : 20
     * active : "prod"
     * deviceActiveCount:2
     * admin : OGLWCHjQinT-soWOk6kNsRZK2Co8-zOiREPZrMgqsaSJyVLBzUnazczXVv2j12rcmmwMBZSEz9yUiENHCpbzRPHYbFgt-LtBxrXcbo8I18EWOA4JmUMDJudt5MrRuL6HrX3FPJsVpXrqy4MibrmEjFM5vQqr0-aXrrsgMXCMd1lAz2-u4dT7VnL2B-5fSTYtgmkHxO-FI1jFQijWrk-mKHZSGXNDgCyBZM5-_eavinOFA-WZjczqZlhaeraNfTUk5-AI2lI8tpmn-7pNBqPNyeS1Y2C4RvGHK-kY2xarjaGk4HtINn4QyipglfrUrM7ya1TdJWg_o3hihlJmKWIyuDpS8hI20F57ADWTS3uBBpArxCrkC0952LF5GCKs6I1vjWVW5jthuCb3jvsitpdlaljqrB4iWmfbOxnMr_YTyhpcDcIA8Z1BS5QYejKWFm3G2S_np6uP1DVKUyDQd7yCQsylT_HyeeqdnjpzfskInsxxI2UqQWrd_JW9Sk15DVdd
     * remark : 代理商测试
     * type : 3
     * pageNum : 1
     * password : c4ca4238a0b923820dcc509a6f75849b
     * createTime : 2019-02-15 13:15:24.0
     * emaill : 1424121114@qq.com
     * loginName : 182
     * name : 代理商测试
     * correlationId : 1
     * disabled : 1
     * tag : "operation",
     * id : 20
     * key : 18211111111
     * openId : "oyMsm5597iU4ebL0KnzYFduXbJ0Y"
     */

    private int roleId;
    private String mobile;
    private int pageSize;
    private String admin;
    private String remark;
    private int type;
    private int pageNum;
    private String password;
    private String createTime;
    private String emaill;
    private String loginName;
    private String name;
    private int correlationId;
    private int disabled;
    private int id;
    private String key;
    private String tag;
    private String active;
    private String openId;
    private int deviceActiveCount;

    public int getDeviceActiveCount() {
        return deviceActiveCount;
    }

    public void setDeviceActiveCount(int deviceActiveCount) {
        this.deviceActiveCount = deviceActiveCount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public static Creator<UserInfoBean> getCREATOR() {
        return CREATOR;
    }

    protected UserInfoBean(Parcel in) {
        roleId = in.readInt();
        mobile = in.readString();
        pageSize = in.readInt();
        admin = in.readString();
        remark = in.readString();
        type = in.readInt();
        pageNum = in.readInt();
        password = in.readString();
        createTime = in.readString();
        emaill = in.readString();
        loginName = in.readString();
        name = in.readString();
        correlationId = in.readInt();
        disabled = in.readInt();
        id = in.readInt();
        key = in.readString();
        tag = in.readString();
        active = in.readString();
        openId = in.readString();
        deviceActiveCount = in.readInt();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmaill() {
        return emaill;
    }

    public void setEmaill(String emaill) {
        this.emaill = emaill;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(roleId);
        dest.writeString(mobile);
        dest.writeInt(pageSize);
        dest.writeString(admin);
        dest.writeString(remark);
        dest.writeInt(type);
        dest.writeInt(pageNum);
        dest.writeString(password);
        dest.writeString(createTime);
        dest.writeString(emaill);
        dest.writeString(loginName);
        dest.writeString(name);
        dest.writeInt(correlationId);
        dest.writeInt(disabled);
        dest.writeInt(id);
        dest.writeString(key);
        dest.writeString(tag);
        dest.writeString(active);
        dest.writeString(openId);
        dest.writeInt(deviceActiveCount);
    }
}
