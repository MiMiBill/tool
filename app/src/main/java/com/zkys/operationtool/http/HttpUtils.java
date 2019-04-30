package com.zkys.operationtool.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.canstant.URLConstant;
import com.zkys.operationtool.retrofitinterface.RetrofitInterface;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Desc:
 * Author:
 * Date: 2018/4/18
 * Copyright (c) 2016, dianlibian.com All Rights Reserved
 */

public class HttpUtils {

    private static final int DEFAULT_TIMEOUT = 10;// 连接超时，单位：秒
    /**
     * 配置Okhttp
     */
//    private static final OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(new GetCookiesInterceptor(MyApplication.getContext()))
////            .addInterceptor(new UserAgentIntercepter()
//            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//            .build();

    private static HttpUtils httpUtils;
    private static Retrofit retrofit;
    private static RetrofitInterface retrofitInterface;

    public static OkHttpClient configOkHttpClient() {
        //日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("mpk", message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //添加参数 Header

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .addInterceptor(new GetCookiesInterceptor(MyApplication.getContext()))
//            .addInterceptor(new UserAgentIntercepter()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }


    /**
     * 单例
     * @return
     */
    public synchronized static RetrofitInterface getRetrofit() {
        retrofit = null;// 先调接口这样定义(后面删掉)
        // 初始化
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLConstant.BASE_URL)
                    .client(configOkHttpClient())
//                    .addConverterFactory(MGsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }

        return retrofitInterface;
    }
    /**
     * 单例 （暂时定义用来测试）
     * @return
     */
    public synchronized static RetrofitInterface getRetrofit(String serverUrl) {
        retrofit = null;// 先调接口这样定义(后面删掉)
        // 初始化
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .client(configOkHttpClient())
//                    .addConverterFactory(MGsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }

        return retrofitInterface;
    }

    static class GetCookiesInterceptor implements Interceptor {
        private Context context;
        private final static String COOKIE = "COOKIE_JSON_STR";
        public static final String SP_NAME = "SharedPreferencesName";

        GetCookiesInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json;charset=UTF-8");
            if (MyApplication.getInstance().getUserInfo() != null) {
                //@TODO 认证暂时去掉 xq
                String access_token = MyApplication.getInstance().getUserInfo().getAccess_token();
                Log.e("mpk","access_token=="+access_token);
                if (!TextUtils.isEmpty(access_token)) builder.addHeader("access_token",access_token);
//                builder.addHeader("TES","3");
            }
            SharedPreferences mSharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            String str = mSharedPreferences.getString(COOKIE, null);
            if (!TextUtils.isEmpty(str)) {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> list = new Gson().fromJson(str, type);
                for (String s : list) {
                    Log.d(this.getClass().getSimpleName(),s);
                    builder.addHeader("cookie", s);//统一添加这里有多少就传多少（多cookie）
                }
            }
            Response response = chain.proceed(builder.build());
            List<String> cookie = response.headers("Set-Cookie");
            if (!cookie.isEmpty()) {//保存cookie
                mSharedPreferences.edit().putString(COOKIE, new Gson().toJson(cookie)).apply();
            }
            return response;
        }

    }

    /**
     * 添加请求头
     *//*
    public static class UserAgentIntercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = MyApplication.getInstance().getUserInfo().getToken();

            Request.Builder request = chain.request().newBuilder();
            if (!TextUtils.isEmpty(token))request.addHeader("Authorization", "Bearer " + token);

            return chain.proceed(request.build());
        }

    }*/



}
