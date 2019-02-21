package com.zkys.operationtool.base;

import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by DGDL-08 on ${DATA}
 */
public abstract class HttpResultObserver<T> implements Observer<HttpResponse<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResponse<T> result) {
        int code = result.getCode();
        if (code == 201) {
            ToastUtil.showShort("登陆过期，请重新登陆！");
            MyApplication.getInstance().clearUserInfo();
            MyApplication.getInstance().restartApplication();
            /*ActivityManager.getAppInstance().finishAllActivity();
            Intent intent = new Intent();
            intent.setClass(MyApplication.getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);*/
        } else {
            onSuccess(result);
        }
    }

    @Override
    public void onError(Throwable e) {

        /*if (!UIUtils.isNetWorkConnected()) {

        }*/

        if (e instanceof SocketTimeoutException) {
            //判断超时异常
            ToastUtil.showShort("访问数据超时");
        }
        if (e instanceof ConnectException) {
            ////判断连接异常，
            ToastUtil.showShort("网络连接异常");
        }

        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            int code = exception.code();
            if (code >= 500) {
                ToastUtil.showShort("服务器异常：" + code);
            } else {
                System.out.println("http state code ----------------------" + code);
            }
        }
        System.out.println(e.getMessage());
        _onError(e);
        /*
        * if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                mOnSuccessAndFaultListener.onFault("网络连接超时");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                mOnSuccessAndFaultListener.onFault("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    mOnSuccessAndFaultListener.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    mOnSuccessAndFaultListener.onFault("请求的地址不存在");
                } else {
                    mOnSuccessAndFaultListener.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                mOnSuccessAndFaultListener.onFault("域名解析失败");
            } else {
                mOnSuccessAndFaultListener.onFault("error:" + e.getMessage());
            }
        *
        * */
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(HttpResponse<T> result);

    public abstract void _onError(Throwable e);
}
