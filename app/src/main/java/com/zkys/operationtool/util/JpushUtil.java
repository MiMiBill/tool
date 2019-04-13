package com.zkys.operationtool.util;

import android.text.TextUtils;
import android.util.Log;

import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.bean.UserInfoBean;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushUtil {

    public static void setJPush(){

        UserInfoBean userInfo = MyApplication.getInstance().getUserInfo();
        if (userInfo == null) {
            Log.e("JpushUtil", "没有用户信息");
            return;
        }
        if(TextUtils.isEmpty(userInfo.getId() + "") || TextUtils.isEmpty(userInfo.getActive()) || TextUtils.isEmpty(userInfo.getTag())){
            Log.e("JpushUtil", "用户信息为空");
            return;
        }
        Set<String> set = new HashSet<>();
        set.add("运维工具");

        JPushInterface.setTags(MyApplication.getContext(), set, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("JpushUtil","i = " + i);
                Log.e("JpushUtil","s = " + s);
                switch (i){
                    //成功："Set tag and alias success"
                    case 0:
                        Log.e("JpushUtil","成功设置标签");
                        break;
                    //超时："Failed to set alias and tags due to timeout. Try again after 60s."
                    case 6002:
                        Log.e("JpushUtil","设置标签超时");
                        break;
                    //其他："Failed with errorCode = " + code
                    default:
                        Log.e("JpushUtil","设置标签失败" + i);

                }
            }
        });
        JPushInterface.setAlias(MyApplication.getContext(), userInfo.getId() + userInfo.getTag() + userInfo.getActive(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("JpushUtil","i = " + i);
                Log.e("JpushUtil","s = " + s);
                switch (i){
                    //成功："Set tag and alias success"
                    case 0:
                        Log.e("JpushUtil","成功设置别名");
                        break;
                    //超时："Failed to set alias and tags due to timeout. Try again after 60s."
                    case 6002:
                        Log.e("JpushUtil","设置别名超时");
                        break;
                    //其他："Failed with errorCode = " + code
                    default:
                        Log.e("JpushUtil","设置别名失败" + i);

                }
            }
        });
    }


}
