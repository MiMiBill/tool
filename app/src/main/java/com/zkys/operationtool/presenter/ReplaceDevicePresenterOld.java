package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.BedBean;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.DeviceBinderInfo;
import com.zkys.operationtool.bean.DeviceParameterBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by DGDL-08 on ${DATA}
 */
public class ReplaceDevicePresenterOld extends BasePresenterImpl<BaseView> {

    public ReplaceDevicePresenterOld(BaseView view) {
        super(view);
    }

    public void getHospitalList() {
        /**
         * 审核状态, 0:待审核，1：已通过，2：未通过
         */
//        HttpUtils.getRetrofit().getHospitalList(MyApplication.getInstance().getUserInfo().getCorrelationId() + "",1)
        HttpUtils.getRetrofit().getnewHospitalList(1)
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<HospitalBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在获取...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<List<HospitalBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<HospitalBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getCoreList(int hid) {
        HttpUtils.getRetrofit().getCoreList(hid, 1)
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<CoreBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在获取...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<List<CoreBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<CoreBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }


    public void getBedList(int hid, int cid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("hospitalId", hid);
        map.put("deptId", cid);
        map.put("isDel", 1);// isDel=1 写死
        HttpUtils.getRetrofit().getBedList(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<BedBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在获取...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<List<BedBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<BedBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }


    public void getDeviceBinderInfo(String deviceCode) {
        HttpUtils.getRetrofit().findBindingByCode(deviceCode)
                .compose(((RxAppCompatActivity) view).<HttpResponse<DeviceBinderInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在获取...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<DeviceBinderInfo>() {
                    @Override
                    public void onSuccess(HttpResponse<DeviceBinderInfo> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void replaceDevice(String bedNumber, int hospitalId, int deptId, List<DeviceParameterBean> deviceList, final String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("bedNumber", bedNumber);
        map.put("hospitalId", hospitalId);
        map.put("deptId", deptId);
        map.put("deviceList", deviceList);
        map.put("remark", remark);
        map.put("userId", MyApplication.getInstance().getUserInfo().getId());
        HttpUtils.getRetrofit().replaceDevice(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<Object>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在提交...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<Object>() {
                    @Override
                    public void onSuccess(HttpResponse<Object> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

}
