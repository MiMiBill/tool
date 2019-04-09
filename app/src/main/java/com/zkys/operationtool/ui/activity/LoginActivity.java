package com.zkys.operationtool.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgyersdk.update.PgyUpdateManager;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zkys.operationtool.BuildConfig;
import com.zkys.operationtool.R;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.EventBusBean;
import com.zkys.operationtool.bean.UserInfoBean;
import com.zkys.operationtool.canstant.SharedConstant;
import com.zkys.operationtool.presenter.LoginPresenterOld;
import com.zkys.operationtool.util.DialogHelper;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.widget.AfterTextWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity<LoginPresenterOld> {


    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyApplication.getInstance().isLogin()) {
            Intent intent = new Intent(this, HomeActivity.class);
            if(getIntent().getBundleExtra(SharedConstant.EXTRA_BUNDLE) != null){
                intent.putExtra(SharedConstant.EXTRA_BUNDLE,
                        getIntent().getBundleExtra(SharedConstant.EXTRA_BUNDLE));
            }
            startActivity(intent);
            finish();
            return;
        }
        /** 新版本 **/
        new PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制更新
                .setUserCanRetry(false)         //失败后是否提示重新下载
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                .register();
        EventBus.getDefault().register(this);
        etAccount.addTextChangedListener(new MyTextWatcher());
        etPassword.addTextChangedListener(new MyTextWatcher());
    }


    @Override
    public LoginPresenterOld initPresenter() {
        return new LoginPresenterOld(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tv_login, R.id.fl_wechat, R.id.iv_delete, R.id.iv_off, R.id.iv_on})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String account = etAccount.getText().toString().trim().replace(" ", "");
                String password = etPassword.getText().toString().trim().replace(" ", "");
                presenter.login(account, password);
                break;
            case R.id.fl_wechat:
                wechatLogin();
                break;
            case R.id.iv_delete:
                etAccount.setText("");
                break;
            case R.id.iv_off:
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etPassword.setSelection(etPassword.getText().toString().length());
                break;
            case R.id.iv_on:
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etPassword.setSelection(etPassword.getText().toString().length());
                break;
        }
    }

    private void wechatLogin() {
        IWXAPI iwxapi = MyApplication.getInstance().getmWxApi();
        if (MyApplication.getInstance().getmWxApi().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            iwxapi.sendReq(req);
        } else {
            ToastUtil.showShort("您手机尚未安装微信，请安装后再登录");
        }
    }


    @Override
    public void setData(HttpResponse result) {
        if (result.getData() instanceof UserInfoBean) {
            if (result.getCode() == 200) {
                MyApplication.getInstance().saveUserInfo((UserInfoBean) result.getData());
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else if (result.getCode() == 406) {
                startActivity(new Intent(this, BinderAccountActivity.class).putExtra("LoginResult", (UserInfoBean) result.getData()));
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void showLoadingDialog(String msg) {
        DialogHelper.getInstance().show(this, msg);
    }

    @Override
    public void dismissLoadingDialog() {
        DialogHelper.getInstance().close();
    }

    @Override
    public void onError_(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(final EventBusBean code) {
        Observable
                .create(new ObservableOnSubscribe<Long>() {
                    @Override
                    public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                        emitter.onNext(1L);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (!TextUtils.isEmpty(code.code)) {
                            presenter.wechatLoginTest(code.code);
                        } else {
                            ToastUtil.showShort("授权失败");
                        }
                    }
                });

    }

    class MyTextWatcher extends AfterTextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            String account = etAccount.getText().toString().trim().replace(" ", "");
            String password = etPassword.getText().toString().trim().replace(" ", "");
            if (BuildConfig.DEBUG) {
                tvLogin.setEnabled(true);
            } else {
                tvLogin.setEnabled(account.length() == 11 && password.length() >= 6);
            }
            ivDelete.setVisibility(account.length() > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }


}
