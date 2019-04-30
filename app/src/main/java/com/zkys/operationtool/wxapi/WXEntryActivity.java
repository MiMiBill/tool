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
//                if (resp instanceof SendAuth.Resp) {  //登陆成功返回类型SendAuth.Resp
//                    //发送成功
//                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
//                    if (sendResp != null) {
//                        //请求个人信息
//                        String code = sendResp.code;
//                        getAccess_token(code);
//                    }
//                }
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

   /* private void getAccess_token(String code) {
        final String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.WeChat_APP_ID
                + "&secret="
                + Constants.WeChat_APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";


        OkHttpUtils.get().url(path).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject =JSON.parseObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    getUserInfo(access,openId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getUserInfo(String accessToken, final String wxopenId){
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken
                + "&openid="+wxopenId;
        OkHttpUtils.get().url(path).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                finish();
            }

            @Override
            public void onResponse(String response, int id) {
                WXUserInfo wxResponse = JSON.parseObject(response,WXUserInfo.class);
                avatar = wxResponse.getHeadimgurl();
                memberName=wxResponse.getNickname();
                gender=wxResponse.getSex();
                openId=wxResponse.getOpenid();


            }
        });
    }*/
}
