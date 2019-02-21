package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.DeviceParameterBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.canstant.TypeCodeCanstant;
import com.zkys.operationtool.dialog.BottomDialog;
import com.zkys.operationtool.presenter.ActivePlatePresenter;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;
import com.zkys.operationtool.widget.AfterTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 激活平板
 */
public class ActivePlateActivity extends BaseActivity<ActivePlatePresenter> implements BottomDialog.ItemSelectedInterface {

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int DEVICE_REQUEST_CODE = 111;
    public static final int PLATE_REQUEST_CODE = 112;
    public static final int SIM_REQUEST_CODE = 113;
    public static final int ADAPTER_REQUEST_CODE = 114;
    public static final int BRACKET_REQUEST_CODE = 115;
    public static final int CABINET_REQUEST_CODE = 116;
    @BindView(R.id.rl_network_exception)
    RelativeLayout rlNetworkException;
    @BindView(R.id.et_bed_number)
    EditText etBedNumber;
    @BindView(R.id.tv_device_code)
    EditText tvDeviceCode;
    @BindView(R.id.iv_scan_device_code)
    ImageView ivScanDeviceCode;
    @BindView(R.id.tv_plate_bar_code)
    EditText tvPlateBarCode;
    @BindView(R.id.iv_scan_plate_bar_code)
    ImageView ivScanPlateBarCode;
    @BindView(R.id.tv_sim_bar_code)
    EditText tvSimBarCode;
    @BindView(R.id.iv_scan_sim_code)
    ImageView ivScanSimCode;
    @BindView(R.id.tv_adapter_bar_code)
    EditText tvAdapterBarCode;
    @BindView(R.id.iv_scan_adapter_code)
    ImageView ivScanAdapterCode;
    @BindView(R.id.tv_bracket_bar_code)
    EditText tvBracketBarCode;
    @BindView(R.id.iv_scan_bracket_code)
    ImageView ivScanBracketCode;
    @BindView(R.id.tv_active)
    TextView tvActive;
    @BindView(R.id.tv_selected_hospital)
    TextView tvSelectedHospital;
    @BindView(R.id.tv_selected_core)
    TextView tvSelectedCore;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.rg_select)
    RadioGroup rgSelect;
    @BindView(R.id.tv_cabinet_bar_code)
    EditText tvCabinetBarCode;
    private BottomDialog bottomDialog;
    private List<HospitalBean> hospitalBeanList;
    private List<CoreBean> coreBeanList;
    private List<String> hospitalNames = new ArrayList<>();
    private List<String> coreNames = new ArrayList<>();
    private int hid;
    private int cid;
    private int run = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTvTitleText("激活设备");
        etBedNumber.addTextChangedListener(new MyTextWatcher());
        tvSelectedHospital.addTextChangedListener(new MyTextWatcher());
        tvSelectedCore.addTextChangedListener(new MyTextWatcher());
        tvDeviceCode.addTextChangedListener(new MyTextWatcher());
        tvPlateBarCode.addTextChangedListener(new MyTextWatcher());
        tvSimBarCode.addTextChangedListener(new MyTextWatcher());
        tvCabinetBarCode.addTextChangedListener(new MyTextWatcher());
        tvAdapterBarCode.addTextChangedListener(new MyTextWatcher());
        tvBracketBarCode.addTextChangedListener(new MyTextWatcher());
        rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_yes) {
                    run = 1;
                } else if (checkedId == R.id.rb_no) {
                    run = 2;
                }
                changeBtnState();
            }
        });
        initDialog();
    }

    @Override
    public ActivePlatePresenter initPresenter() {
        return new ActivePlatePresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_active_plate;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }


    @Override
    public void setData(HttpResponse result) {
        if (result.getData() != null) {
            if (result.getData() instanceof List) {
                List list = (List) result.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0) instanceof HospitalBean) {
                        hospitalBeanList = (List<HospitalBean>) result.getData();
                        List<String> names = new ArrayList<>();
                        for (HospitalBean hospitalBean : hospitalBeanList) {
                            names.add(hospitalBean.getName());
                        }
                        initDialogDataAndShow(names, 1);// 1代表选择医院的数据
                    } else if (list.get(0) instanceof CoreBean) {
                        coreBeanList = (List<CoreBean>) result.getData();
                        List<String> names = new ArrayList<>();
                        for (CoreBean coreBean : coreBeanList) {
                            names.add(coreBean.getName());
                        }
                        initDialogDataAndShow(names, 2);// 2代表选择科室的数据
                    }
                }
            }
        } else if (result.getCode() == 200) {
            ToastUtil.showShort("激活成功");
            finish();
        } else {
            ToastUtil.showShort(result.getInfo());
        }
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
                if (UIUtils.isNumeric(barCode) || UIUtils.isUrl(barCode)) {
                    tvDeviceCode.setText(barCode);
                } else {
                    String[] split = new String(Base64.decode(barCode.getBytes(), Base64.DEFAULT)).split(",");
                    tvSimBarCode.setText(split[0]);
                    tvDeviceCode.setText(split[split.length - 1]);
                }
            } else if (requestCode == PLATE_REQUEST_CODE) {
                tvPlateBarCode.setText(barCode);
            } else if (requestCode == SIM_REQUEST_CODE) {
                tvSimBarCode.setText(barCode);
            } else if (requestCode == ADAPTER_REQUEST_CODE) {
                tvAdapterBarCode.setText(barCode);
            } else if (requestCode == BRACKET_REQUEST_CODE) {
                tvBracketBarCode.setText(barCode);
            } else if (requestCode == CABINET_REQUEST_CODE) {
                tvCabinetBarCode.setText(barCode);
            }
        }
    }

    @OnClick({R.id.rl_select_hospital, R.id.rl_select_core, R.id.iv_scan_device_code, R.id.iv_scan_plate_bar_code, R.id.iv_scan_sim_code, R.id.iv_scan_adapter_code, R.id.iv_scan_bracket_code, R.id.tv_active, R.id.iv_scan_cabinet_code})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        switch (view.getId()) {
            case R.id.rl_select_hospital:// 选择医院
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                presenter.getHospitalList();
                break;
            case R.id.rl_select_core:// 选择科室
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                if (hid > 0) {
                    presenter.getCoreList(hid);
                } else {
                    ToastUtil.showShort("请先选择医院");
                }
                break;
            case R.id.iv_scan_device_code:// 扫描设备二维码
                scanCode(R.id.iv_scan_device_code, intent, DEVICE_REQUEST_CODE);
                break;
            case R.id.iv_scan_plate_bar_code:// 扫描平板二维码
                scanCode(R.id.iv_scan_plate_bar_code, intent, PLATE_REQUEST_CODE);
                break;
            case R.id.iv_scan_sim_code:// 扫描SIM卡条形码
                scanCode(R.id.iv_scan_sim_code, intent, SIM_REQUEST_CODE);
                break;
            case R.id.iv_scan_adapter_code:// 扫描适配器条形码
                scanCode(R.id.iv_scan_adapter_code, intent, ADAPTER_REQUEST_CODE);
                break;
            case R.id.iv_scan_bracket_code:// 扫描支架条形码
                scanCode(R.id.iv_scan_bracket_code, intent, BRACKET_REQUEST_CODE);
                break;
            case R.id.iv_scan_cabinet_code:// 扫描柜子二维码
                scanCode(R.id.iv_scan_cabinet_code, intent, CABINET_REQUEST_CODE);
                break;
            case R.id.tv_active:// 激活
                activatePlate();
                break;
        }
    }

    private void initDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomDialog();
            bottomDialog.setItemSelectedInterface(this);
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
                            Log.d(ActivePlateActivity.this.getClass().getSimpleName(), "没有授予相机权限");
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 -> 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    private void activatePlate() {
        /**
         * type --> 1：联通卡  2：二G锁  4：屏  8：适配器  16：支架
         */
        String bedNumber = etBedNumber.getText().toString().trim();
        String selectHospitalName = tvSelectedHospital.getText().toString().trim();
        String selectCoreName = tvSelectedCore.getText().toString().trim();

        String deviceCode = tvDeviceCode.getText().toString().trim();
        String simBarCode = tvSimBarCode.getText().toString().trim();
        String plateBarCode = tvPlateBarCode.getText().toString().trim();
        String cabinetBarCode = tvCabinetBarCode.getText().toString().trim();
        String adapterBarCode = tvAdapterBarCode.getText().toString().trim();
        String bracketBarCode = tvBracketBarCode.getText().toString().trim();

        List<DeviceParameterBean> list = new ArrayList<>();
        list.add(new DeviceParameterBean(deviceCode, plateBarCode, TypeCodeCanstant.TYPE_PLATE));
        list.add(new DeviceParameterBean(simBarCode, "", TypeCodeCanstant.TYPE_SIM_KAR));
        list.add(new DeviceParameterBean(cabinetBarCode, "", TypeCodeCanstant.TYPE_CABINET));
        list.add(new DeviceParameterBean(adapterBarCode, "", TypeCodeCanstant.TYPE_ADAPTER));
        list.add(new DeviceParameterBean(bracketBarCode, "", TypeCodeCanstant.TYPE_BRACKET));

        presenter.activate(bedNumber,cid, list, hid, run);
    }


    @Override
    public void itemSelected(int position, int type) {
        if (type == 1 && hospitalNames.size() > 0) {
            hid = hospitalBeanList.get(position).getId();
            tvSelectedHospital.setText(hospitalNames.get(position));
            tvSelectedCore.setText("");
            coreNames.clear();
            cid = -1;
        } else if (type == 2 && coreNames.size() > 0) {
            cid = coreBeanList.get(position).getId();
            tvSelectedCore.setText(coreNames.get(position));
        }
    }

    void initDialogDataAndShow(List<String> names, int type) {

        if (bottomDialog != null) {
            if (type == 1) {
                hospitalNames.clear();
                hospitalNames.addAll(names);
                if (hospitalNames.size() > 0) {
                    if (!bottomDialog.isVisible()) {
                        bottomDialog.setData(hospitalNames, type);
                        bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                    }
                } else {
                    if (bottomDialog.isVisible()) {
                        bottomDialog.dismiss();
                    }
                    ToastUtil.showShort("没有可选的医院");
                }
            } else if (type == 2) {
                coreNames.clear();
                coreNames.addAll(names);
                if (coreNames.size() > 0) {
                    if (!bottomDialog.isVisible()) {
                        bottomDialog.setData(coreNames, type);
                        bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                    }
                } else {
                    if (bottomDialog.isVisible()) {
                        bottomDialog.dismiss();
                    }
                    ToastUtil.showShort("没有可选的科室");
                }
            }
        }
    }

    class MyTextWatcher extends AfterTextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            changeBtnState();
        }
    }

    void changeBtnState() {
        String bedNumber = etBedNumber.getText().toString().trim();
        String selectHospitalName = tvSelectedHospital.getText().toString().trim();
        String selectCoreName = tvSelectedCore.getText().toString().trim();
        String deviceCode = tvDeviceCode.getText().toString().trim();
        String plateBarCode = tvPlateBarCode.getText().toString().trim();
        String simBarCode = tvSimBarCode.getText().toString().trim();
        String cabinetBarCode = tvCabinetBarCode.getText().toString().trim();
        String adapterBarCode = tvAdapterBarCode.getText().toString().trim();
        String bracketBarCode = tvBracketBarCode.getText().toString().trim();
        int checkedRadioButtonId = rgSelect.getCheckedRadioButtonId();
        boolean enable = !TextUtils.isEmpty(bedNumber) && !TextUtils.isEmpty(selectHospitalName)
                && !TextUtils.isEmpty(selectCoreName) && !TextUtils.isEmpty(deviceCode)
                && !TextUtils.isEmpty(plateBarCode) && !TextUtils.isEmpty(simBarCode)
                && !TextUtils.isEmpty(cabinetBarCode)
                && !TextUtils.isEmpty(adapterBarCode)
                && !TextUtils.isEmpty(bracketBarCode)
                && checkedRadioButtonId != 0;

        tvActive.setEnabled(enable);
    }
}
