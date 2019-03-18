package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.BedOrderStateBean;
import com.zkys.operationtool.bean.DeviceInfoBean;
import com.zkys.operationtool.bean.ItemUsageRatesBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlateStatusPresenter extends BasePresenterImpl<BaseView> {

    public PlateStatusPresenter(BaseView view) {
        super(view);
    }

    public void getAllPadUsageRates(Map<String, Object> map) {
        HttpUtils.getRetrofit().getAllPadUsageRates(map)
                .compose(((RxAppCompatActivity)view).<HttpResponse<List<ItemUsageRatesBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<ItemUsageRatesBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<ItemUsageRatesBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getAllDeptPadUsageRates(Map<String, Object> map) {
        HttpUtils.getRetrofit().getAllDeptPadUsageRates(map)
                .compose(((RxAppCompatActivity)view).<HttpResponse<List<ItemUsageRatesBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<ItemUsageRatesBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<ItemUsageRatesBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getPadOrderStatusData(Map<String, Object> map) {
        HttpUtils.getRetrofit().getPadOrderStatusData(map)
                .compose(((RxAppCompatActivity)view).<HttpResponse<List<BedOrderStateBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<BedOrderStateBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<BedOrderStateBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getDeviceDetailInfo(Map<String, Object> map) {
        HttpUtils.getRetrofit().getDeviceDetailInfo(map)
                .compose(((RxAppCompatActivity)view).<HttpResponse<DeviceInfoBean>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<DeviceInfoBean>() {
                    @Override
                    public void onSuccess(HttpResponse<DeviceInfoBean> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

}
