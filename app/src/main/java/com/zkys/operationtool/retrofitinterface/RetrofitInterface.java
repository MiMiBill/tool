package com.zkys.operationtool.retrofitinterface;

import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.AuditItemBean;
import com.zkys.operationtool.bean.BedBean;
import com.zkys.operationtool.bean.BedOrderStateBean;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.DeviceBinderInfo;
import com.zkys.operationtool.bean.DeviceInfoBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.ItemStatisticBean;
import com.zkys.operationtool.bean.ItemUsageRatesBean;
import com.zkys.operationtool.bean.OrderDataBean;
import com.zkys.operationtool.bean.UserInfoBean;
import com.zkys.operationtool.bean.VolumeInfoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    @POST("admin/wxLogin")
    @FormUrlEncoded
    Observable<HttpResponse<UserInfoBean>> wechatLogin(@Field("code") String code);

    // 医院列表
    @FormUrlEncoded
    @POST("hospital/list")
    Observable<HttpResponse<List<HospitalBean>>> getHospitalList(@Field("sydicId")String sydicId, @Field("state") int state);

    // 科室列表
    @POST("hospital/deptList")
    @FormUrlEncoded
    Observable<HttpResponse<List<CoreBean>>> getCoreList(@Field("hospitalId") int hid,  @Field("state") int state);

    // 账号绑定
    @POST("admin/bindingOpenId")
    @FormUrlEncoded
    Observable<HttpResponse<UserInfoBean>> bindingAccount(@Field("openId") String openid,  @Field("mobile") String mobile, @Field("password") String password);
    // 激活设备
//    @POST("core/device/activate")
    @POST("deviceBinding/newActivate")
    Observable<HttpResponse<Object>> activatePlate(@Body Map<String, Object> map);

    // 床位列表

//    @POST("hospitalBed/list")//@Field("hospitalId") int hospitalId, @Field("deptId") int deptId
    @POST("deviceBinding/list")//@Field("hospitalId") int hospitalId, @Field("deptId") int deptId
    @FormUrlEncoded
    Observable<HttpResponse<List<BedBean>>> getBedList(@FieldMap Map<String, Integer> map);


    // 根据编号获取设备绑定信息
    @POST("deviceBinding/findBindingByCode")
    @FormUrlEncoded
    Observable<HttpResponse<DeviceBinderInfo>> findBindingByCode(@Field("code") String code);

    // 更换设备
    @POST("deviceBinding/replacingDevice")
    Observable<HttpResponse<Object>> replaceDevice(@Body Map<String, Object> map);

    // 根据科室医院ID获取科室平板原有的音量配置
    @POST("padVolume/list")
    @FormUrlEncoded
    Observable<HttpResponse<List<VolumeInfoBean>>> getPadVolume(@Field("hospitalId") int hospitalId, @Field("deptId") int deptId);


    // 控制修改科室平板原有的音量配置
    @POST("padVolume/insert")
    @FormUrlEncoded
    Observable<HttpResponse<Object>> controlPadVolume(@Field("hospitalId") int hospitalId, @Field("deptId") int deptId, @Field("volume") int volume, @Field("id") int vId);


    // 訂單列表
    @POST("wxOrder/list")
    @FormUrlEncoded
    Observable<HttpResponse<OrderDataBean>> getOderData(@FieldMap Map<String, Object> map);

    // 获取统计图表数据
    @POST("/wxOrder/wxOrderStatistics")
    @FormUrlEncoded
    Observable<HttpResponse<List<ItemStatisticBean>>> getOrderStatistics(@FieldMap Map<String, Object> map);

    // 获取团队审核
    @GET("teamAuthenticate/findAllUnaudited")
    Observable<HttpResponse<List<AuditItemBean>>> getTeamAuditData();

    // 审核团队认证申请
    @GET("teamAuthenticate/audit")
    Observable<HttpResponse<Object>> audit(@Query("teamId") int teamId, @Query("state") int state);

    // 所有医院的平板使用率
    @POST("padStatus/allPadUsageRates")
    @FormUrlEncoded
    Observable<HttpResponse<List<ItemUsageRatesBean>>> getAllPadUsageRates(@FieldMap Map<String, Object> map);

    // 指定医院所有科室的平板使用率
    @POST("padStatus/allDeptPadUsageRates")
    @FormUrlEncoded
    Observable<HttpResponse<List<ItemUsageRatesBean>>> getAllDeptPadUsageRates(@FieldMap Map<String, Object> map);

    // 平板订单状态状态
    @GET("padStatus/padOrderStatus")
    Observable<HttpResponse<List<BedOrderStateBean>>> getPadOrderStatusData(@QueryMap Map<String, Object> map);

    // 设备详细信息
    @GET("padStatus/deviceDetailInfo")
    Observable<HttpResponse<DeviceInfoBean>> getDeviceDetailInfo(@QueryMap Map<String, Object> map);


}
