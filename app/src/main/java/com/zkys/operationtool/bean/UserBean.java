package com.zkys.operationtool.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登陆返回数据
 */
public class UserBean implements Parcelable {
    /**
     * access_token : 21_MqqbOk4dT-2EY5jqorGnHP2NjrIBn2KCtEivs5ilBJ1KCZOihXjB-H1vj9wSUe4C2r8Tllt1euxTlo8jLLN8afy7EEHbAQHU-ocrFyb2Tk4
     * refresh_token : 21_aU54CVzeUKrfR3qgP71pYxJrOXJ0DsDwRzTY61qJjPS760q6-ADxB2uRN0aqhv8fDcmtGtkcztyduMUXzkzsU6LMP0ZiF51fARB1zRA-2hM
     * unionid : oo31r1HvFb6YOmkMWOvoZt34qRgU
     * openid : oyMsm54x-m1PzM7XdnKRFbptVP2Y
     * scope : snsapi_userinfo
     * expires_in : 7200
     */

    private String access_token;
    private String refresh_token;
    private String unionid;
    private String openid;
    private String scope;
    private int expires_in;

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

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }


    protected UserBean(Parcel in) {
        expires_in = in.readInt();
        scope = in.readString();
        openid = in.readString();
        unionid = in.readString();
        refresh_token = in.readString();
        access_token = in.readString();

    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expires_in);
        dest.writeString(scope);
        dest.writeString(openid);
        dest.writeString(unionid);
        dest.writeString(refresh_token);
        dest.writeString(access_token);
    }
}
