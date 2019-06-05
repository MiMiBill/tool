package com.zkys.operationtool.presenter;

import com.google.gson.JsonObject;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.canstant.URLConstant;
import com.zkys.operationtool.http.HttpUtils;
import com.zkys.operationtool.util.MD5Util;
import com.zkys.operationtool.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

;

public class PadToolPresenter extends BasePresenterImpl<BaseView> {

    public PadToolPresenter(BaseView view) {
        super(view);
    }

    public void bindLock(String did, String bid, int brand) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("did", did);
        hashMap.put("bid", bid);
        hashMap.put("brand", brand);
        HttpUtils.getRetrofit().bindLock(hashMap)
                .compose(((RxAppCompatActivity) view).<HttpResponse<Object>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<Object>() {
                    @Override
                    public void onSuccess(HttpResponse<Object> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        view.onError_(e);
                    }
                });
    }


    //解绑
    public void unbindLock(String did, String bid, int brand) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("did", did);
        hashMap.put("bid", bid);
        hashMap.put("brand", brand);
        HttpUtils.getRetrofit().unbindLock(hashMap)
                .compose(((RxAppCompatActivity) view).<HttpResponse<Object>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<Object>() {
                    @Override
                    public void onSuccess(HttpResponse<Object> result) {
//                        view.setData(result);
                        if (listener != null)
                            listener.onSuccess(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
//                        view.onError_(e);
                        if (listener != null)
                            listener.onError();
                    }
                });
    }


    //第三方解锁
    public void thridunLock(String bid) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("userid", "zkys");
            jsonObject.addProperty("cmd", "control");
            jsonObject.addProperty("type", 1);
            jsonObject.addProperty("value", 0);
            jsonObject.addProperty("deviceid", bid);
            int serialnum = (int) (System.currentTimeMillis() / 1000);
            jsonObject.addProperty("serialnum", serialnum);
            String signKey = StringUtil.getString("zkys", "control", "1", "0", bid, String
                            .valueOf(serialnum), "tkN7sW*b#tM6x^SGp0C2");
            String sign = MD5Util.MD5Encode(signKey, "utf8");
            jsonObject.addProperty("sign", sign);
//            LogFactory.l().i("jsonobject===" + jsonObject.toString());

            OkHttpClient okHttpClient = new OkHttpClient();
            //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , jsonObject.toString());
            final Request request = new Request.Builder()
                    .url(URLConstant.THRID_URL+"/api")
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if(thridlistener!=null)
                        thridlistener.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(thridlistener!=null)
                        thridlistener.onThridUnlock(response.body().string());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private OnUnbindListener listener;

    public void setOnUnbindListener(OnUnbindListener listener) {
        this.listener = listener;
    }

    public interface OnUnbindListener {
        void onSuccess(HttpResponse result);

        void onError();
    }


    private OnThridUnbindListener thridlistener;

    public void setOnThridUnbindListener(OnThridUnbindListener thridlistener) {
        this.thridlistener = thridlistener;
    }

    public interface OnThridUnbindListener {
        void onThridUnlock(String result);

        void onError(Throwable e);
    }

}
