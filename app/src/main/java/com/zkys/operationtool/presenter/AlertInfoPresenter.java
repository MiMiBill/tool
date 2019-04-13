package com.zkys.operationtool.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.AlertBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AlertInfoPresenter extends BasePresenterImpl<BaseView> {

    public AlertInfoPresenter(BaseView view) {
        super(view);
    }

    public void getAlertInfo() {
        HttpUtils.getRetrofit().getAlertInfo()
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<AlertBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<AlertBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<AlertBean>> result) {
                        view.setData(result);
                        Log.d("AlertInfoPresenter", new Gson().toJson(result.getData()));
                    }

                    @Override
                    public void _onError(Throwable e) {
                        view.onError_(e);
                        Log.d("AlertInfoPresenter", e.getMessage());
                    }
                });
    }
}
