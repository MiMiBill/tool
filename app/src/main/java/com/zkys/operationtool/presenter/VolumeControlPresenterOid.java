package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.base.HttpResultObserverOld;
import com.zkys.operationtool.baseImpl.BasePresenterImplOid;
import com.zkys.operationtool.baseImpl.BaseViewOld;
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
public class VolumeControlPresenterOid extends BasePresenterImplOid<BaseViewOld> {

    public VolumeControlPresenterOid(BaseViewOld view) {
        super(view);
    }

    public void getHospitalList() {
        HttpUtils.getRetrofit().getHospitalList("")
                .compose(((RxAppCompatActivity) view).<HttpResponseOld<List<HospitalBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserverOld<List<HospitalBean>>() {
                    @Override
                    public void onSuccess(HttpResponseOld<List<HospitalBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getCoreList(int hid) {
        HttpUtils.getRetrofit().getCoreList(hid)
                .compose(((RxAppCompatActivity) view).<HttpResponseOld<List<CoreBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserverOld<List<CoreBean>>() {
                    @Override
                    public void onSuccess(HttpResponseOld<List<CoreBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

    public void getPadVolume(int hid, int cid) {
        HttpUtils.getRetrofit().getPadVolume(hid, cid)
                .compose(((RxAppCompatActivity) view).<HttpResponseOld<List<VolumeInfoBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserverOld<List<VolumeInfoBean>>() {
                    @Override
                    public void onSuccess(HttpResponseOld<List<VolumeInfoBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }
    public void controlPadVolume(int hid, int cid, int volume) {
        HttpUtils.getRetrofit().controlPadVolume(hid, cid, volume)
                .compose(((RxAppCompatActivity) view).<HttpResponseOld<Object>>bindToLifecycle())
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
                .subscribe(new HttpResultObserverOld<Object>() {
                    @Override
                    public void onSuccess(HttpResponseOld<Object> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });
    }

}
