package com.zkys.operationtool.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zkys.operationtool.bean.LoginResult;
import com.zkys.operationtool.bean.UserInfo;
import com.zkys.operationtool.canstant.SharedConstant;

import org.json.JSONException;

/**
 * Desc:
 * Author:
 * Date: 2018/4/18
 * Copyright (c) 2016, dianlibian.com All Rights Reserved
 */

public class MyApplication extends Application {

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx96eeba77767289aa";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI mWxApi;

    public IWXAPI getmWxApi() {
        return mWxApi;
    }

    public static Context getContext() {
        return context;
    }

    public static MyApplication getInstance() {
        return (MyApplication) getContext();
    }

    public static Context context;

    public SharedPreferences mainPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mainPreferences = getSharedPreferences(SharedConstant.MAIN_PREFERENCE, MODE_PRIVATE);
        registToWX();
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(APP_ID);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        String jsonStr = mainPreferences.getString(SharedConstant.USERINFO, "");
        if (userInfo == null) {
            UserInfo userInfo = new Gson().fromJson(jsonStr, UserInfo.class);
            setUserInfo(userInfo);// 设置现有的数据模型
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

//    public boolean isLogin() {
//        return userInfo != null;
//    }

    public static SharedPreferences getMainPreferences() {
        return getInstance().mainPreferences;
    }

    /**
     * 存储用户数据到本地
     * @param loginResult
     * @throws JSONException
     */
    public void saveUserInfo(LoginResult loginResult) {
        if (loginResult != null) {
            UserInfo info = new UserInfo();
            info.setToken(loginResult.getToken());
            info.setAvatarUrl(loginResult.getAvatarUrl());
            info.setId(loginResult.getId());
            info.setName(loginResult.getName());
            info.setUsername(loginResult.getUsername());
            info.setTag(loginResult.getTag());
            info.setRoleInfo(loginResult.getRoleInfo());
            String json = new Gson().toJson(info);
            mainPreferences.edit().clear().apply();
            mainPreferences.edit().putString(SharedConstant.USERINFO, json).apply();
            setUserInfo(info);
        } else {
            Log.d(this.getClass().getSimpleName(),"用户数据保存失败！");
        }
    }


    public boolean isLogin() {
        String jsonStr = mainPreferences.getString(SharedConstant.USERINFO, "");
        if (!TextUtils.isEmpty(jsonStr) && jsonStr.length() > 0) {
            UserInfo userInfo = new Gson().fromJson(jsonStr, UserInfo.class);
            setUserInfo(userInfo);// 设置现有的数据模型
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.getUsername()) && !TextUtils.isEmpty(userInfo.getToken()) && userInfo.getId() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 清除用户数据
     */
    public void clearUserInfo() {
        mainPreferences.edit().clear().apply();
        setUserInfo(null);
    }

    public  void restartApplication() {
        final Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(MyApplication.getContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getContext().startActivity(intent);
    }


}
