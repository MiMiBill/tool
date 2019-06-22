package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.LockInfo;
import com.zkys.operationtool.canstant.URLConstant;
import com.zkys.operationtool.http.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                , jsonObject.toString());
        RequestBody requestBody = new FormBody.Builder()
                .add("did", code)
                .build();
        final Request request = new Request.Builder()
                .addHeader("access_token",MyApplication.getInstance().getUserInfo().getAccess_token())
                .url(URLConstant.BASE_URL+"/lock/unlock")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener!=null)
                    listener.onLockFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(listener!=null)
                    listener.onLock(response.body().string());
            }
        });
    }


    private OnLockListener listener;

    public void setOnLockListener(OnLockListener listener) {
        this.listener = listener;
    }

    public interface OnLockListener {
        void onLock(String result);
        void onLockFail();
    }

}
