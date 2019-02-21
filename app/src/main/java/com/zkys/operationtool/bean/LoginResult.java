package com.zkys.operationtool.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LoginResult implements Parcelable {

    /**
     * id : 2
     * tag : app
     * name : 木巨开发者
     * username : developer
     * avatarUrl : http://cdn.duitang.com/uploads/item/201508/30/20150830105732_nZCLV.jpeg
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjIsInN1YiI6ImRldmVsb3BlciIsImF1ZCI6IuacqOW3qOW8gOWPkeiAhSIsInRhZyI6ImFwcCIsInVyZyI6WyJkZXZlbG9wZXIiLCJtYW5hZ2VyIiwiY2FwdGFpbiIsIm9wZXJhdGUiXSwiZXhwIjoxNTQ4MTE5NTUyfQ.qVTnf5rLTp-6KH67Z0WlGcoJOv3RDTp9Loi6ezZfF1F7DOyhGoFty389PJfl1VWJOXGmc5UpbnddX2fWN66-vQ
     * roleInfo : ["developer","manager","captain","operate"]
     */

    private int id;
    private String tag;
    private String name;
    private String username;
    private String avatarUrl;
    private String token;
    private List<String> roleInfo;
    private String openid;
    private String unionid;
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private int errcode;
    private String scope;

    protected LoginResult(Parcel in) {
        id = in.readInt();
        tag = in.readString();
        name = in.readString();
        username = in.readString();
        avatarUrl = in.readString();
        token = in.readString();
        roleInfo = in.createStringArrayList();
        openid = in.readString();
        unionid = in.readString();
        access_token = in.readString();
        refresh_token = in.readString();
        expires_in = in.readString();
        errcode = in.readInt();
        scope = in.readString();
    }

    public static final Creator<LoginResult> CREATOR = new Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel in) {
            return new LoginResult(in);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(List<String> roleInfo) {
        this.roleInfo = roleInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tag);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(avatarUrl);
        dest.writeString(token);
        dest.writeStringList(roleInfo);
        dest.writeString(openid);
        dest.writeString(unionid);
        dest.writeString(access_token);
        dest.writeString(refresh_token);
        dest.writeString(expires_in);
        dest.writeInt(errcode);
        dest.writeString(scope);
    }
}
