package com.zkys.operationtool.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AuditItemBean implements Parcelable {

    /**
     * deptName : 外科
     * authenticateImage : http://qiniuimage.zgzkys.com/1551937638195_icon_98.png
     * name : k
     * pageSize : 20
     * isAuthenticate : 2 (认证状态，0：未认证；1：认证通过；2：认证中)
     * hospitalName : 东莞大朗医院
     * id : 224
     * applyDate : 2019-03-07 13:47:59
     * pageNum : 1
     */

    private String deptName;
    private String authenticateImage;
    private String name;
    private int pageSize;
    private int isAuthenticate;
    private String hospitalName;
    private int id;
    private String applyDate;
    private int pageNum;

    protected AuditItemBean(Parcel in) {
        deptName = in.readString();
        authenticateImage = in.readString();
        name = in.readString();
        pageSize = in.readInt();
        isAuthenticate = in.readInt();
        hospitalName = in.readString();
        id = in.readInt();
        applyDate = in.readString();
        pageNum = in.readInt();
    }

    public static final Creator<AuditItemBean> CREATOR = new Creator<AuditItemBean>() {
        @Override
        public AuditItemBean createFromParcel(Parcel in) {
            return new AuditItemBean(in);
        }

        @Override
        public AuditItemBean[] newArray(int size) {
            return new AuditItemBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deptName);
        dest.writeString(authenticateImage);
        dest.writeString(name);
        dest.writeInt(pageSize);
        dest.writeInt(isAuthenticate);
        dest.writeString(hospitalName);
        dest.writeInt(id);
        dest.writeString(applyDate);
        dest.writeInt(pageNum);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAuthenticateImage() {
        return authenticateImage;
    }

    public void setAuthenticateImage(String authenticateImage) {
        this.authenticateImage = authenticateImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(int isAuthenticate) {
        this.isAuthenticate = isAuthenticate;
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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
