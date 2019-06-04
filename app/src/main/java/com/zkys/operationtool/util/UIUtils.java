package com.zkys.operationtool.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.util.TypedValue;
import android.webkit.URLUtil;

import com.zkys.operationtool.application.MyApplication;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by DGDL-08 on ${DATA}
 */
public class UIUtils {

    /**
     * 获取 application context
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 检查网络是否已经连接
     *
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public static int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, MyApplication.getContext().getResources().getDisplayMetrics());
    }


    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isUrl(String query) {
        return Patterns.WEB_URL.matcher(query).matches()||URLUtil.isValidUrl(query);
    }

    public static String getRegards() {

        Date d = new Date();
        String regards = "";
        if(d.getHours()<11){
            regards = "早上好";
        }else if(d.getHours()<13){
            regards = "中午好";
        }else if(d.getHours()<18){
            regards = "下午好";
        }else if(d.getHours()<24){
            regards = "晚上好";
        }
        return regards;
    }

    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

}
