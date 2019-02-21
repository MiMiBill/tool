package com.zkys.operationtool.presenter;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.base.HttpResultObserver;
import com.zkys.operationtool.baseImpl.BasePresenterImpl;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.LoginResult;
import com.zkys.operationtool.http.HttpUtils;
import com.zkys.operationtool.util.ToastUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by DGDL-08 on ${DATA}
 */
public class LoginPresenter extends BasePresenterImpl<BaseView> {

    public LoginPresenter(BaseView view) {
        super(view);
    }

    public void login(String username, String password) {
        HttpUtils.getRetrofit().login(username, password)
                .compose(((RxAppCompatActivity) view).<HttpResponse<LoginResult>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<LoginResult>() {
                    @Override
                    public void onSuccess(HttpResponse<LoginResult> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }


    public void wechatLoginTest(String code) {
        HttpUtils.getRetrofit().wechatLogin(code)
                .compose(((RxAppCompatActivity) view).<HttpResponse<LoginResult>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<LoginResult>() {
                    @Override
                    public void onSuccess(HttpResponse<LoginResult> result) {
                        view.setData(result);
                        // Log.d("LoginPresenter", new Gson().toJson(result));
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });


    }


    public void getHospitalList() {
        HttpUtils.getRetrofit().getHospitalList("")
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
                });
    }


    /**
     * 绑定账号 --> 参数值由微信授权登陆接口返回的（数据模型在LoginResult）
     * @param openid
     * @param unionid
     * @param access_token
     * @param refresh_token
     * @param username
     * @param password
     */
    public void bindingAccount(String openid, String unionid, String access_token, String refresh_token, String username, String password) {
        HttpUtils.getRetrofit().bindingAccount(openid,unionid,access_token,refresh_token,username,password)
                .compose(((RxAppCompatActivity) view).<HttpResponse<LoginResult>>bindToLifecycle())
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
                .subscribe(new HttpResultObserver<LoginResult>() {
                    @Override
                    public void onSuccess(HttpResponse<LoginResult> result) {
                        view.setData(result);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }


}
