package com.zkys.operationtool.util;

public class StringUtil {
    //不限定参数拼接字符串
    public static String getString(String... msgs){
        String str="";
        for (int i = 0; i < msgs.length; i++) {
            str+=msgs[i];
        }
        return str;
    }
}
