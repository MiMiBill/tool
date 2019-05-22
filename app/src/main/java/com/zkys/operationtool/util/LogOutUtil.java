package com.zkys.operationtool.util;

import com.zkys.operationtool.application.MyApplication;

public class LogOutUtil {
    public static void LogOut(){
        MyApplication.getInstance().clearUserInfo();
        MyApplication.getInstance().restartApplication();
    }
}
