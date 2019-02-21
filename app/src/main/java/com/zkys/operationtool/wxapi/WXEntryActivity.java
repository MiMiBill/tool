package com.zkys.operationtool.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.utils.Log;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.bean.EventBusBean;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    public static String code;
    public static BaseResp resp = null;
    private static final String TAG = "WXEntryActivity";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean handleIntent = MyApplication.getInstance().getmWxApi().handleIntent(getIntent(), this);
        if(!handleIntent){
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.getInstance().getmWxApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq: ");
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            resp = baseResp;
            code = ((SendAuth.Resp) baseResp).code; //即为所需的code
            Log.d(TAG, "code==" + code);

            // ToastUtil.showShort("code==" + code);// 011fymHs1RTfjm0TJXJs1zd5Hs1fymH2
        }
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.d(TAG, "onResp: 成功");
                EventBus.getDefault().post(new EventBusBean(code));
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.d(TAG, "onResp: 用户取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.d(TAG, "onResp: 发送请求被拒绝");
                finish();
                break;
        }
    }
}
