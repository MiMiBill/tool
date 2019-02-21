package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.VolumeInfoBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by DGDL-08 on ${DATA}
 */
public class VolumeControlPresenter extends BasePresenterImpl<BaseView> {

    public VolumeControlPresenter(BaseView view) {
        super(view);
    }

    public void getHospitalList() {
        HttpUtils.getRetrofit().getHospitalList("")
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
        HttpUtils.getRetrofit().getCoreList(hid)
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

    public void getPadVolume(int hid, int cid) {
        HttpUtils.getRetrofit().getPadVolume(hid, cid)
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<VolumeInfoBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<VolumeInfoBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<VolumeInfoBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }
    public void controlPadVolume(int hid, int cid, int volume) {
        HttpUtils.getRetrofit().controlPadVolume(hid, cid, volume)
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
