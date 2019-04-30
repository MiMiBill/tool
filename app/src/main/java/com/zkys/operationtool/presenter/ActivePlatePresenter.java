package com.zkys.operationtool.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.CoreBean;
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
public class ActivePlatePresenter extends BasePresenterImpl<BaseView> {

    public ActivePlatePresenter(BaseView view) {
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

    /*public void getHospitalList2() {
        HttpUtils.getRetrofit().getHospitalLists("")
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
    }*/

    public void getCoreList(int hid) {
        HttpUtils.getRetrofit().getCoreList(hid,  1)
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
                        Log.d("ActivePlatePresenter", new Gson().toJson(result));
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }



    /**
     *
     * @param bedNumber
     * @param cId
     * @param list
     * @param hId
     * @param run 1 -->是   2 -->否
     */
    public void activate(String bedNumber, int cId, List<DeviceParameterBean> list, int hId, int run) {
        /**
         * (@Field("bedNumber") String bedNumber, @Field("deptId") int cId,
         *                                            @Field("deviceList") String list,
         *                                            @Field("hospitalId") int hid, @Field("run") int run);
         */
        Map<String, Object> map = new HashMap<>();
        map.put("bedNumber", bedNumber);
        map.put("deptId", cId);
        map.put("deviceList", list);
        map.put("hospitalId", hId);
        map.put("run", run);
        map.put("userId", MyApplication.getInstance().getUserInfo().getId());
//        HttpUtils.getRetrofit().activatePlate3(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map)))
        HttpUtils.getRetrofit().activatePlate(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<Object>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在激活...");
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
