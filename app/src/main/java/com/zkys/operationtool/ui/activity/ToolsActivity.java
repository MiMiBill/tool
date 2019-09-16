package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.canstant.Constant;
import com.zkys.operationtool.ui.dialog.SimpleDialogFragment;
import com.zkys.operationtool.util.DateUtil;
import com.zkys.operationtool.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 工具
 */
public class ToolsActivity extends BaseActivity {

    private static final String TAG = ToolsActivity.class.getSimpleName();
    public static final String AUTHORITY_LIST = "authorityList";

    @BindView(R.id.rl_unlock)
    RelativeLayout rlUnLock;
    @BindView(R.id.rl_get_pad_pwd)
    RelativeLayout rlPadPwd;
    @BindView(R.id.rl_pad_tool)
    RelativeLayout rlPadTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("工具");
        initView();
    }

    private void initView()
    {
        Gson gson = new Gson();

        ArrayList<String> authorityList = getIntent().getStringArrayListExtra(AUTHORITY_LIST);
        for (int i = 0;i < authorityList.size();i++)
        {

            Object o = authorityList.get(i);
            HashMap<String,Object> map = (HashMap<String,Object>)o;
            String code = (String) map.get("code");
            if (Constant.Tools.PADKEY_CODER.equalsIgnoreCase(code))
            {
                rlPadPwd.setVisibility(View.VISIBLE);
            }else if (Constant.Tools.UNLOCK_CODER.equalsIgnoreCase(code)){
                rlUnLock.setVisibility(View.VISIBLE);
            }else if (Constant.Tools.PRODUCE_CODER.equalsIgnoreCase(code))
            {
                rlPadTool.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_tools;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {

    }

    @Override
    public void onError_(Throwable e) {

    }

    @OnClick({R.id.rl_unlock, R.id.rl_get_pad_pwd,R.id.rl_pad_tool})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_unlock:
                startActivity(new Intent(this, UnLockActivity.class));
                break;
            case R.id.rl_get_pad_pwd:
                scanCode(R.id.rl_get_pad_pwd, new Intent(this, CaptureActivity.class), 1000);
                break;
            case R.id.rl_pad_tool:
                startActivity(new Intent(this, PadToolActivity.class));
                break;
        }
    }

    @SuppressLint("CheckResult")
    void scanCode(int viewId, final Intent intent, final int requestCode) {
        RxView.clicks(findViewById(viewId))
                .compose(mRxPermissions.ensure(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startActivityForResult(intent, requestCode);
                        } else {
                            Log.d(ToolsActivity.this.getClass().getSimpleName(), "没有授予相机权限");
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 -> 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String barCode = "";
        if (data != null && data.getExtras() != null) {
            barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
            if (!TextUtils.isEmpty(barCode)) {
                String currentDate = DateUtil.timeStamp2Date((System.currentTimeMillis() / 1000) + "", "yyyyMMdd");
                barCode = (barCode + currentDate).hashCode() + "";
                barCode = barCode.substring(barCode.length() - 6);
                new SimpleDialogFragment().show("温馨提示", "当前获取到的平板密码：" + barCode, "确定", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, getSupportFragmentManager());
            } else {
                ToastUtil.showShort("没有找到结果");
            }
        }
    }

}
