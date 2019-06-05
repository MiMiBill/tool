package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.OverDueBean;
import com.zkys.operationtool.http.HttpUtils;
import com.zkys.operationtool.util.LogFactory;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OverduePresenter extends BasePresenterImpl<BaseView> {

    public OverduePresenter(BaseView view) {
        super(view);
    }

    public void wxOrderList(Map<String, Object> map) {
        HttpUtils.getRetrofit().wxOrderList(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<OverDueBean>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<OverDueBean>() {
                    @Override
                    public void onSuccess(HttpResponse<OverDueBean> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        LogFactory.l().i("e==="+e.getMessage());
                        view.onError_(e);
                    }
                });
    }
}
