package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.ItemStatisticBean;
import com.zkys.operationtool.bean.OrderDataBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderListPresenter extends BasePresenterImpl<BaseView> {

    public OrderListPresenter(BaseView view) {
        super(view);
    }

    public void getHospitalList() {
        /**
         * 审核状态, 0:待审核，1：已通过，2：未通过
         */
//        HttpUtils.getRetrofit().getHospitalList(MyApplication.getInstance().getUserInfo().getCorrelationId() + "", 1)
        HttpUtils.getRetrofit().getnewHospitalList( 1)
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
    public void getOderData(Map<String, Object> map) {
        HttpUtils.getRetrofit().getOderData(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<OrderDataBean>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<OrderDataBean>() {
                    @Override
                    public void onSuccess(HttpResponse<OrderDataBean> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getOrderStatistics(Map<String, Object> map) {
        HttpUtils.getRetrofit().getOrderStatistics(map)
                .compose(((RxAppCompatActivity)view).<HttpResponse<List<ItemStatisticBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<List<ItemStatisticBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<ItemStatisticBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }
}
