package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

import butterknife.BindView;

/**
 * 二维码及条形码扫面界面
 */
public class CaptureActivity extends BaseActivity {

    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    @BindView(R.id.ll_scan_title)
    RelativeLayout llScanTitle;
    private CaptureFragment captureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImmersionBar
                .flymeOSStatusBarFontColorInt(Color.WHITE)
                .statusBarDarkFont(false)
                .statusBarAlpha(0f)
                .init();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llScanTitle.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        llScanTitle.setLayoutParams(layoutParams);


        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_capture;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.NO_TITLE_VIEW;
    }


    /*
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
//            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };

    @Override
    public void setData(HttpResponse result) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }


}
