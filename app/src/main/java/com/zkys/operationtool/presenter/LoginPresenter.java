package com.zkys.operationtool.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.UserInfoBean;
import com.zkys.operationtool.http.HttpUtils;
import com.zkys.operationtool.util.ToastUtil;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by DGDL-08 on ${DATA} //新登录接口
 */
public class LoginPresenter extends BasePresenterImpl<BaseView> {

    public LoginPresenter(BaseView view) {
        super(view);
    }

    public void login(String account,String password) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        HttpUtils.getRetrofit().newlogin(map)
                .compose(((RxAppCompatActivity) view).<HttpResponse<UserInfoBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在登陆中...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<UserInfoBean>() {
                    @Override
                    public void onSuccess(HttpResponse<UserInfoBean> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }


    public void wechatLoginTest(String code) {// 0819jVnc01MGhA1kLBoc0Xc1oc09jVnx
//        HttpUtils.getRetrofit().wechatLogin(code)
        Log.e("mpk","code"+code);
        HttpUtils.getRetrofit().thirdLogin(code,1)
                .compose(((RxAppCompatActivity) view).<HttpResponse<UserInfoBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDialog("正在登陆中...");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.dismissLoadingDialog();
                    }
                })
                .subscribe(new HttpResultObserver<UserInfoBean>() {
                    @Override
                    public void onSuccess(HttpResponse<UserInfoBean> result) {
                        String json = new Gson().toJson(result);
                        view.setData(result);
//                         Log.e("mpk", new Gson().toJson(result));
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });


    }


    public void getHospitalList() {
        /*HttpUtils.getRetrofit().getHospitalList("")
                .compose(((RxAppCompatActivity) view).<HttpResponse<List<HospitalBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultObserver<List<HospitalBean>>() {
                    @Override
                    public void onSuccess(HttpResponse<List<HospitalBean>> result) {
                        ToastUtil.showShort("" + result.getData().get(0).getName());

                    }

                    @Override
                    public void _onError(Throwable e) {

                    }
                });*/
    }


    /**
     * 绑定账号 --> 参数值由微信授权登陆接口返回的（数据模型在LoginResult）
     * @param openid
     * @param mobile
     * @param password
     */
    public void bindingAccount(String openid, String mobile, String password,int type) {
//        HttpUtils.getRetrofit().bindingAccount(openid,mobile,password)
        HttpUtils.getRetrofit().bindingWechat(openid,mobile,password,type)
                .compose(((RxAppCompatActivity) view).<HttpResponse<UserInfoBean>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<UserInfoBean>() {
                    @Override
                    public void onSuccess(HttpResponse<UserInfoBean> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }


}
