package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.LockInfo;
import com.zkys.operationtool.bean.UnLockInfo;
import com.zkys.operationtool.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UnLockPresenter extends BasePresenterImpl<BaseView> {

    public UnLockPresenter(BaseView view) {
        super(view);
    }

    /**
     * 查询锁状态
     */
    public void checkLock(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("did", code);
        HttpUtils.getRetrofit().getLockInfo(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<LockInfo>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<LockInfo>() {
                    @Override
                    public void onSuccess(HttpResponse<LockInfo> result) {
                        view.setData(result);

                    }

                    @Override
                    public void _onError(Throwable e) {
                    }
                });
    }

    /**
     * 开锁
     */
    public void unlock(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("did", code);
        HttpUtils.getRetrofit().unlock(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<UnLockInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在解锁...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<UnLockInfo>() {
                    @Override
                    public void onSuccess(HttpResponse<UnLockInfo> result) {
                        view.setData(result);

                    }

                    @Override
                    public void _onError(Throwable e) {
                        view.onError_(e);
                    }
                });
    }


}
