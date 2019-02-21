package com.zkys.operationtool.retrofitinterface;

import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.bean.BedBean;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.DeviceBinderInfo;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.LoginResult;
import com.zkys.operationtool.bean.UserInfoBean;
import com.zkys.operationtool.bean.VolumeInfoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Desc:
 * Date: 2018/4/18
 * Copyright (c) 2016, dianlibian.com All Rights Reserved
 */

public interface RetrofitInterface {

    /**
     * @Body 标签不能同时和@FormUrlEncoded、Multipart标签使用了 否则爆红
     * @return
     */

    // 账号登陆
    @POST("admin/login")
    @FormUrlEncoded
    Observable<HttpResponse<UserInfoBean>> login(@Field("mobile") String username, @Field("password") String password);
    // 微信登陆
    @POST("sys/third")
    @FormUrlEncoded
    Observable<HttpResponse<LoginResult>> wechatLogin(@Field("code") String code);

    // 医院列表
    @POST("core/hospital/select")
    @FormUrlEncoded
    Observable<HttpResponseOld<List<HospitalBean>>> getHospitalList(@Field("null") String n);

    // 科室列表
    @POST("core/department/select")
    @FormUrlEncoded
    Observable<HttpResponseOld<List<CoreBean>>> getCoreList(@Field("hid") int hid);

    // 账号绑定
    @POST("sys/binding")
    @FormUrlEncoded
    Observable<HttpResponse<LoginResult>> bindingAccount(@Field("openid") String openid, @Field("unionid") String unionid, @Field("access_token") String access_token,
                                                            @Field("refresh_token") String refresh_token, @Field("username") String username, @Field("password") String password);
    // 激活设备
    @POST("core/device/activate")
//    @POST("core/deviceBinding/newActivate")
    Observable<HttpResponseOld<Object>> activatePlate(@Body Map<String, Object> map);

    // 床位列表
    @POST("core/deviceBinding/list")//@Field("hospitalId") int hospitalId, @Field("deptId") int deptId
    Observable<HttpResponseOld<List<BedBean>>> getBedList(@Body Map<String, Integer> map);

    // 根据编号获取设备绑定信息
    @POST("core/device/findBindingByCode")
    @FormUrlEncoded
    Observable<HttpResponseOld<DeviceBinderInfo>> findBindingByCode(@Field("code") String code);

    // 更换设备
    @POST("core/device/replacing")
//    @POST("core/deviceBinding/replacingDevice")
    Observable<HttpResponseOld<Object>> replaceDevice(@Body Map<String, Object> map);

    // 根据科室医院ID获取科室平板原有的音量配置
    @POST("fota/padVolume/list")
    @FormUrlEncoded
    Observable<HttpResponseOld<List<VolumeInfoBean>>> getPadVolume(@Field("hospitalId") int hospitalId, @Field("deptId") int deptId);


    // 控制修改科室平板原有的音量配置
    @POST("fota/padVolume/insert")
    @FormUrlEncoded
    Observable<HttpResponseOld<Object>> controlPadVolume(@Field("hospitalId") int hospitalId, @Field("deptId") int deptId, @Field("volume") int volume);

}
