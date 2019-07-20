package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.RepairListBean;
import com.zkys.operationtool.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepairListPresenter extends BasePresenterImpl<BaseView> {

    public RepairListPresenter(BaseView view) {
        super(view);
    }

    public void repairList(int pageNum,int status) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",10);
        if(status!=-1){
            map.put("status",status);
        }
        HttpUtils.getRetrofit().repairList(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<RepairListBean>>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<List<RepairListBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<RepairListBean>> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        view.onError_(e);
                    }
                });
    }
}
