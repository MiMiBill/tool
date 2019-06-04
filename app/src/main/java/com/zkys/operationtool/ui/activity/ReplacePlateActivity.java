package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.DeviceParameterBean;
import com.zkys.operationtool.canstant.TypeCodeCanstant;
import com.zkys.operationtool.presenter.ReplaceDevicePresenterOld;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 更换平板
 */
public class ReplacePlateActivity extends BaseActivity<ReplaceDevicePresenterOld> {

    @BindView(R.id.tv_device_code)
    EditText tvDeviceCode;
    @BindView(R.id.tv_plate_bar_code)
    EditText tvPlateBarCode;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_replace)
    TextView tvReplace;
    @BindView(R.id.tv_sim_bar_code)
    EditText tvSimBarCode;

    public static final int DEVICE_REQUEST_CODE = 111;
    public static final int PLATE_REQUEST_CODE = 112;
    private int hid;
    private int cid;
    private String bedNumber;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("更换平板");

        Intent intent = getIntent();
        if (intent != null) {
            hid = intent.getIntExtra("hid", 0);
            cid = intent.getIntExtra("cid", 0);
            bedNumber = intent.getStringExtra("bedNumber");
            type = intent.getIntExtra("type", 0);
        }
    }

    @Override
    public ReplaceDevicePresenterOld initPresenter() {
        return new ReplaceDevicePresenterOld(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_replace_plate;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null) {
            if (result.getCode() == 200) {
                ToastUtil.showShort("更换成功");
                finish();
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        }
    }

    @Override
    public void onError_(Throwable e) {

    }

    @OnClick({R.id.iv_scan_device_code, R.id.iv_scan_plate_bar_code, R.id.tv_replace})
    public void onViewClicked(View view) {
        Intent scanCodeIntent = new Intent(this, CaptureActivity.class);
        switch (view.getId()) {
            case R.id.iv_scan_device_code:
                scanCode(R.id.iv_scan_device_code, scanCodeIntent, DEVICE_REQUEST_CODE);
                break;
            case R.id.iv_scan_plate_bar_code:
                scanCode(R.id.iv_scan_plate_bar_code, scanCodeIntent, PLATE_REQUEST_CODE);
                break;
            case R.id.tv_replace:
                replaceDevice();
                break;
        }
    }

    private void replaceDevice() {
        String deviceCode = tvDeviceCode.getText().toString().trim();
        String plateBarCode = tvPlateBarCode.getText().toString().trim();
        String simBarCode = tvSimBarCode.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();
        if("".equals(deviceCode)){
            ToastUtil.showShort("请输入平板DID");
            return;
        }
        if("".equals(simBarCode)){
            ToastUtil.showShort("请输入SIM卡条形码");
            return;
        }
        if("".equals(plateBarCode)){
            ToastUtil.showShort("请输入平板BID");
            return;
        }
        if("".equals(remark)){
            ToastUtil.showShort("请填写备注");
            return;
        }

        List<DeviceParameterBean> list = new ArrayList<>();
        list.add(new DeviceParameterBean(deviceCode, plateBarCode, type));
        list.add(new DeviceParameterBean(simBarCode, "", TypeCodeCanstant.TYPE_SIM_KAR));
        presenter.replaceDevice(bedNumber, hid, cid, list, remark);
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
                            Log.d(ReplacePlateActivity.this.getClass().getSimpleName(), "没有授予相机权限");
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
        if (data != null) {
            if (data.getExtras() != null) {
                barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
                if (UIUtils.isUrl(barCode) && barCode.contains("=") && barCode.lastIndexOf("=") != barCode.length() - 1) {
                    barCode = barCode.substring(barCode.lastIndexOf("=") + 1);
                }
            }
            if (requestCode == DEVICE_REQUEST_CODE) {
                /*if (UIUtils.isNumeric(barCode) || UIUtils.isUrl(barCode)) {
                    tvDeviceCode.setText(barCode);
                } else {
                    String[] split = new String(Base64.decode(barCode.getBytes(), Base64.DEFAULT)).split(",");
                    tvDeviceCode.setText(split[split.length - 1]);

                }*/
                if (UIUtils.isNumeric(barCode) || UIUtils.isUrl(barCode)) {
                    tvDeviceCode.setText(barCode);
                } else {
                    if (isBase64(barCode.replaceAll("\n", ""))) {
                        String codeResult = new String(Base64.decode(barCode.getBytes(), Base64.DEFAULT));
                        if (codeResult.contains(",") && codeResult.length() == 35) {
                            String[] split = codeResult.split(",");
                            tvSimBarCode.setText(split[0]);
                            tvDeviceCode.setText(split[split.length - 1]);
                        } else {
                            ToastUtil.showShort("请扫描正确的平板二维码");
                        }
                    } else {
                        ToastUtil.showShort("请扫描正确的平板二维码");
                    }
                }
            } else if (requestCode == PLATE_REQUEST_CODE) {
                tvPlateBarCode.setText(barCode);
            }
        }
    }

    private static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }

}
