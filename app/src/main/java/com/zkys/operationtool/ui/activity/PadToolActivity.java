package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.ToolBean;
import com.zkys.operationtool.dialog.BottomDialog;
import com.zkys.operationtool.presenter.PadToolPresenter;
import com.zkys.operationtool.util.LogFactory;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

//生产工具
public class PadToolActivity extends BaseActivity<PadToolPresenter> implements BottomDialog
        .ItemSelectedInterface,
        PadToolPresenter.OnUnbindListener, PadToolPresenter.OnThridUnbindListener {

    @BindView(R.id.tv_busy)
    TextView tvBusy;
    @BindView(R.id.iv_scan_device_code)
    ImageView ivScanDeviceCode;
    @BindView(R.id.iv_scan_lock_code)
    ImageView ivScanLockCode;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.btn_unbind)
    Button btnUnbind;
    @BindView(R.id.btn_unlock)
    Button btnUnlock;
    @BindView(R.id.tv_plate)
    TextView tvPlate;
    @BindView(R.id.tv_lock)
    TextView tvLock;
    private BottomDialog bottomDialog;
    private List<String> toolNames = new ArrayList<>();
    private int CABINET_REQUEST_CODE = 120;
    private int LOCK_REQUEST_CODE = 121;
    private int brand = -1;   //供应商id
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    ToastUtil.showShort("开锁成功");
                    break;
                case 2:
                    ToastUtil.showShort("开锁失败");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("生产工具");
        presenter.setOnUnbindListener(this);
        presenter.setOnThridUnbindListener(this);
        initDialog();
    }

    @Override
    public PadToolPresenter initPresenter() {
        return new PadToolPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
        handler.removeMessages(2);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_pad_tool;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null && result.getCode() == 200) {
            ToastUtil.showShort("绑定成功");
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.tv_busy, R.id.iv_scan_device_code, R.id.iv_scan_lock_code, R.id.btn_bind, R.id
            .btn_unbind, R.id.btn_unlock})
    public void onViewClicked(View view) {
        Intent scanCodeIntent = new Intent(this, CaptureActivity.class);
        switch (view.getId()) {
            case R.id.tv_busy:
                toolNames.clear();
                toolNames.add("物网通 - 3");
                toolNames.add("物联锁 - 2");
                toolNames.add("连旅 - 1");
                toolNames.add("预留 - 4");
                initDialogDataAndShow(toolNames, 0);
                break;
            case R.id.iv_scan_device_code:
                scanCode(R.id.iv_scan_device_code, scanCodeIntent, CABINET_REQUEST_CODE);
                break;
            case R.id.iv_scan_lock_code:
                scanCode(R.id.iv_scan_lock_code, scanCodeIntent, LOCK_REQUEST_CODE);
                break;
            case R.id.btn_bind:
                checkBrand(0);
                break;
            case R.id.btn_unbind:
                checkBrand(1);
                break;
            case R.id.btn_unlock:
                checkBrand(2);
                break;
        }
    }

    private void checkBrand(int type) {
        String busyText = tvBusy.getText().toString();
        String plateText = tvPlate.getText().toString();
        String lockText = tvLock.getText().toString();
        if (busyText.equals(getResources().getString(R.string.please_check_busy))) {
            ToastUtil.showShort("请选择供应商");
            return;
        }
        if (type == 2) {
            if (brand != 3) {
                ToastUtil.showShort("第三方解锁请选择物网通供应商");
                return;
            }
        }
        if (plateText.equals(getResources().getString(R.string.get_plate_code))) {
            ToastUtil.showShort("请扫描柜子二维码");
            return;
        }
        if (lockText.equals(getResources().getString(R.string.get_lock_code))) {
            ToastUtil.showShort("请扫描锁二维码");
            return;
        }
        if (type == 0) {
            presenter.bindLock(plateText, lockText, brand);
        } else if (type == 1) {
            presenter.unbindLock(plateText, lockText, brand);
        } else if (type == 2) {
            presenter.thridunLock(lockText);
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
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 ->" +
                                    " 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String barCode = "";
        if (data != null) {
            if (data.getExtras() != null) {
                barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
                if (UIUtils.isUrl(barCode) && barCode.contains("=") && barCode.lastIndexOf("=")
                        != barCode.length() - 1) {
                    barCode = barCode.substring(barCode.lastIndexOf("=") + 1);
                }
            }
            if (requestCode == CABINET_REQUEST_CODE) {
                tvPlate.setText(barCode);
            } else if (requestCode == LOCK_REQUEST_CODE) {
                if (barCode.length() > 12) {
                    tvLock.setText(barCode.substring(2, 14));
                } else {
                    tvLock.setText(barCode);
                }
            }
        }
    }


    private void initDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomDialog();
            bottomDialog.setItemSelectedInterface(this);
        }
    }


    private void initDialogDataAndShow(List<String> names, int type) {

        if (bottomDialog != null) {
            if (names.size() > 0) {
                if (!bottomDialog.isVisible()) {
                    bottomDialog.setData(toolNames, type);
                    bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                }
            } else {
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
            }
        }
    }

    @Override
    public void itemSelected(int position, int type) {
        if (type == 0 && toolNames.size() > 0) {
            tvBusy.setText(toolNames.get(position));
            switch (position) {
                case 0:
                    brand = 3;
                    break;
                case 1:
                    brand = 2;
                    break;
                case 2:
                    brand = 1;
                    break;
                case 3:
                    brand = 4;
                    break;
            }
        }
    }

    @Override
    public void onSuccess(HttpResponse result) {
        if (result != null && result.getCode() == 200) {
            ToastUtil.showShort("解绑成功");
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void onThridUnlock(String result) {
        LogFactory.l().i("result===" + result);
        if (result != null && (!result.equals(""))) {
            ToolBean toolBean = new Gson().fromJson(result, ToolBean.class);
            LogFactory.l().i(toolBean.getResult().toLowerCase());
            if (toolBean.getResult().toLowerCase().equals("ok")) {
                handler.sendEmptyMessage(1);
            } else {
                handler.sendEmptyMessage(2);
            }
        } else {
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    public void onError(Throwable e) {
        ToastUtil.showShort("" + e.getMessage());
    }

    @Override
    public void onError() {
        ToastUtil.showShort("解绑失败");
    }
}
