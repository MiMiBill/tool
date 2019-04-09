package com.zkys.operationtool.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.ui.OpenFileChooserCallBack;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;
import com.zkys.operationtool.util.WebViewLifecycleUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity implements OpenFileChooserCallBack {
    @BindView(R.id.wv)
    WebView wv;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.ly_error)
    LinearLayout lyError;
    @BindView(R.id.pb_web_load)
    ProgressBar pbWebLoad;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    public static final int FILECHOOSER_RESULTCODE = 1;
    private static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;
    private static final int SCAN_CODE = REQ_CHOOSE + 1;
    public String HOST;
    public String redirected = HOST;  //保存访问的地址。失败后重新访问

    @Override
    public int getViewLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("疑问解答");
        HOST = getIntent().getStringExtra("url");

        // 不显示滚动条
        wv.setHorizontalScrollBarEnabled(false); // 隐藏水平滚动条
        wv.setVerticalScrollBarEnabled(false); // 隐藏垂直滚动条
        if (UIUtils.isNetWorkConnected()) {
            wv.loadUrl(HOST); //加载地址
        } else {
            lyError.setVisibility(View.VISIBLE);
        }

        wv.getSettings().setDomStorageEnabled(true);//设置可以使用localStorage// 开启 DOM storage API 功能
//        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//默认使用缓存
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//默认使用缓存
        wv.getSettings().setAllowFileAccess(true);//可以读取文件缓存(manifest生效)
        wv.getSettings().setAppCacheEnabled(false);//应用可以有缓存
        wv.getSettings().setJavaScriptEnabled(true); // webview必须设置支持JavaScript
        wv.getSettings().setSupportMultipleWindows(true); // 支持多窗口
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        wv.getSettings().setDefaultTextEncodingName("utf-8"); // 设置编码格式
        wv.getSettings().setUseWideViewPort(true); // 是否将图片调整到适合webview的大小
        wv.getSettings().setSaveFormData(false);//记住表单内容
        wv.getSettings().setSavePassword(false); //记住密码
        wv.getSettings().setSupportZoom(false);//支持缩放
        wv.getSettings().setGeolocationEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //方法就是告诉WebView先不要自动加载图片，等页面finish后再发起图片加载。
        if (Build.VERSION.SDK_INT >= 19) {
            wv.getSettings().setLoadsImagesAutomatically(true);
        } else {
            wv.getSettings().setLoadsImagesAutomatically(false);
        }

        wv.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                showProgress();
                if (null != pbWebLoad) {
                    pbWebLoad.setVisibility(View.VISIBLE);
                    pbWebLoad.setProgress(0);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!"about:blank" .equals(url)) {
                    redirected = url;
                }
                try {
                    if (!wv.getSettings().getLoadsImagesAutomatically()) {
                        wv.getSettings().setLoadsImagesAutomatically(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                hideProgress();
                if (null != pbWebLoad)
                    pbWebLoad.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //页面发生错误
                wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                lyError.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("WebViewActivity", "javascript url " + url);
                if (url.startsWith("tel:")) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } /*else if (url.equalsIgnoreCase(HOST + "/mobile/car_index/list")) {
                    //http://www.youjiuhe.com/mobile/car_index/list
                    Handler handler = new MyHandler();
                    Logger.i("javascript找到地址");
                    new BaiduGPS(getApplicationContext(), handler).main();
                    return false;
                }*/
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        wv.setWebChromeClient(new ReWebChomeClient(this));
    }




    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SCAN_CODE && resultCode == RESULT_OK) {
            if (null != intent.getExtras()) {
                String content = intent.getExtras().getString("content");
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showLong(this, "未能查找到内容");
                    return;
                }
                Log.i("WebViewActivity", "======content" + " " + content);
                if (content.startsWith(HOST)) {
                    wv.loadUrl(content);
                } else {
                    ToastUtil.showLong(this, "请使用零售易餐饮商品二维码进行扫描");
                    return;
                }
            }
        }
        if (resultCode == RESULT_CANCELED) {
            cancelFileChooserCallBack();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        hideProgress();
    }

    @Override
    public void setData(HttpResponse result) {

    }

    @Override
    public void onError_(Throwable e) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.tv_refresh)
    public void onViewClicked() {
        lyError.setVisibility(View.GONE);
        Log.i("WebViewActivity", "javascript redirected " + redirected);
        wv.loadUrl(redirected); //加载地址
    }


    /**
     * ReWebChomeClient
     *
     * @Author KenChung
     */
    public class ReWebChomeClient extends WebChromeClient {

        private OpenFileChooserCallBack mOpenFileChooserCallBack;

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView newWebView = new WebView(WebViewActivity.this);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();
            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    wv.loadUrl(url);
                    return true;
                }
            });
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (null != pbWebLoad)
                pbWebLoad.setProgress(newProgress);
        }

        public ReWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
            mOpenFileChooserCallBack = openFileChooserCallBack;
        }

        //For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {

        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            openFileChooser(null, "");
            return true;
        }
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
//        showChoosePictureDialog();
    }

    @Override
    public void cancelFileChooserCallBack() {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        WebViewLifecycleUtils.onResume(wv);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebViewLifecycleUtils.onPause(wv);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebViewLifecycleUtils.onDestroy(wv);
        super.onDestroy();
    }


}
