package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.BedBean;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.DeviceBinderInfo;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.canstant.TypeCodeCanstant;
import com.zkys.operationtool.dialog.BottomDialog;
import com.zkys.operationtool.presenter.ReplaceDevicePresenterOld;
import com.zkys.operationtool.util.LogOutUtil;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;
import com.zkys.operationtool.widget.AfterTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 更换设备
 */
public class ReplaceDeviceActivity extends BaseActivity<ReplaceDevicePresenterOld> implements BottomDialog.ItemSelectedInterface {

    @BindView(R.id.tv_selected_hospital)
    TextView tvSelectedHospital;
    @BindView(R.id.tv_selected_core)
    TextView tvSelectedCore;
    @BindView(R.id.tv_selected_bed)
    TextView tvSelectedBed;
    @BindView(R.id.rl_replace_cabinet)
    RelativeLayout rlReplaceCabinet;
    @BindView(R.id.rl_replace_sim)
    RelativeLayout rlReplaceSim;
    @BindView(R.id.ll_replace_menu)
    LinearLayout llReplaceMenu;
    private int hid;// 医院ID
    private int cid;// 科室ID
    private int bid;// 床位ID
    private BottomDialog bottomDialog;
    private List<HospitalBean> hospitalBeanList;
    private List<CoreBean> coreBeanList;
    private List<BedBean> bedBeanList;
    private List<String> hospitalNames = new ArrayList<>();
    private List<String> coreNames = new ArrayList<>();
    private List<String> bedNames = new ArrayList<>();
    public static final int CABINET_REQUEST_CODE = 116;
    private String bedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("更换设备");
        initDialog();
        tvSelectedHospital.addTextChangedListener(new MyTextWatcher());
        tvSelectedCore.addTextChangedListener(new MyTextWatcher());
        tvSelectedBed.addTextChangedListener(new MyTextWatcher());
    }

    private void initDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomDialog();
            bottomDialog.setItemSelectedInterface(this);
        }
    }


    @Override
    public ReplaceDevicePresenterOld initPresenter() {
        return new ReplaceDevicePresenterOld(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_replace_device;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.rl_replace_plate, R.id.rl_replace_cabinet,
            R.id.rl_replace_sim, R.id.rl_replace_adapter,
            R.id.rl_replace_bracket, R.id.rl_repair_device,
            R.id.rl_select_hospital, R.id.rl_select_core,
            R.id.rl_select_bed, R.id.iv_scan_cabinet_code})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("hid", hid)
                .putExtra("cid", cid)
                .putExtra("bedNumber", bedNumber);
        switch (view.getId()) {
            case R.id.rl_replace_plate:
                startToActivity(intent.setClass(this, ReplacePlateActivity.class).putExtra("type", TypeCodeCanstant.TYPE_PLATE));
                break;
            case R.id.rl_replace_cabinet:
                startToActivity(intent.setClass(this, ReplaceCabinetActivity.class).putExtra("type", TypeCodeCanstant.TYPE_CABINET));
                break;
            case R.id.rl_replace_sim:
                startToActivity(intent.setClass(this, ReplaceSIMActivity.class).putExtra("type", TypeCodeCanstant.TYPE_SIM_KAR));
                break;
            case R.id.rl_replace_adapter:
                startToActivity(intent.setClass(this, ReplaceAdapterActivity.class).putExtra("type", TypeCodeCanstant.TYPE_BED));
                break;
            case R.id.rl_replace_bracket:
                startToActivity(intent.setClass(this, ReplaceBracketActivity.class).putExtra("type", TypeCodeCanstant.TYPE_BRACKET));
                break;
            case R.id.rl_repair_device:// 返修（暂不用做）
                break;
            case R.id.rl_select_hospital:
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                presenter.getHospitalList();
                break;
            case R.id.rl_select_core:
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                if (hid > 0) {
                    presenter.getCoreList(hid);
                } else {
                    ToastUtil.showShort("请先选择医院");
                }
                break;
            case R.id.rl_select_bed:
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                if (hid > 0 && cid > 0) {
                    presenter.getBedList(hid, cid);
                } else {
                    ToastUtil.showShort("请先选择医院与科室");
                }
                break;
            case R.id.iv_scan_cabinet_code:
                Intent scanCodeIntent = new Intent(this, CaptureActivity.class);
                scanCode(R.id.iv_scan_cabinet_code, scanCodeIntent, CABINET_REQUEST_CODE);
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
                            Log.d(ReplaceDeviceActivity.this.getClass().getSimpleName(), "没有授予相机权限");
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 -> 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    private void startToActivity(Intent intent) {
        if (isAllSelect()) {
            startActivity(intent);
        } else {
            ToastUtil.showShort("请选择医院、科室、床位");
        }
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getData() != null) {
            if(result.getCode()==1001){ //token失效,退出登录
                LogOutUtil.LogOut();
            }
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
                    } else if (list.get(0) instanceof BedBean) {
                        bedBeanList = (List<BedBean>) result.getData();
                        List<String> names = new ArrayList<>();
                        for (BedBean bedBean : bedBeanList) {
                            names.add(bedBean.getNumber());
                        }
                        initDialogDataAndShow(names, 3);// 3代表选择床位的数据
                    }
                } else {
                    ToastUtil.showShort("暂无数据");
                }
            } else if (result.getData() instanceof DeviceBinderInfo) {
                DeviceBinderInfo binderInfo = (DeviceBinderInfo) result.getData();
                hid = binderInfo.getHospitalId();
                cid = binderInfo.getDeptId();
                bedNumber = binderInfo.getBedNumber();
                tvSelectedHospital.setText(binderInfo.getHospitalName());
                tvSelectedCore.setText(binderInfo.getDeptName());
                tvSelectedBed.setText(binderInfo.getBedNumber());
            }
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void onError_(Throwable e) {

    }


    @Override
    public void itemSelected(int position, int type) {
        if (type == 1 && hospitalNames.size() > 0) {
            hid = hospitalBeanList.get(position).getId();
            tvSelectedHospital.setText(hospitalNames.get(position));
            tvSelectedCore.setText("");
            tvSelectedBed.setText("");
            coreNames.clear();
            bedNames.clear();
            cid = -1;
            bid = -1;
            bedNumber = "";
        } else if (type == 2 && coreNames.size() > 0) {
            cid = coreBeanList.get(position).getId();
            tvSelectedCore.setText(coreNames.get(position));
            tvSelectedBed.setText("");
            bedNames.clear();
            bid = -1;
            bedNumber = "";
        } else if (type == 3 && bedNames.size() > 0) {
            bid = bedBeanList.get(position).getId();
            bedNumber = bedBeanList.get(position).getNumber();
            tvSelectedBed.setText(bedNames.get(position));
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
            } else if (type == 3) {
                bedNames.clear();
                bedNames.addAll(names);
                if (bedNames.size() > 0) {
                    if (!bottomDialog.isVisible()) {
                        bottomDialog.setData(bedNames, type);
                        bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                    }
                } else {
                    if (bottomDialog.isVisible()) {
                        bottomDialog.dismiss();
                    }
                    ToastUtil.showShort("没有可选的床位");
                }
            }
        }
    }

    boolean isAllSelect() {
        String hospital = tvSelectedHospital.getText().toString().trim();
        String core = tvSelectedCore.getText().toString().trim();
        String bed = tvSelectedBed.getText().toString().trim();
        return !TextUtils.isEmpty(hospital) && !TextUtils.isEmpty(core) && !TextUtils.isEmpty(bed);
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
            if (requestCode == CABINET_REQUEST_CODE) {
                // 根据扫码结果调用请求获取医院、科室、床位列表
                final String finalBarCode = barCode;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getDeviceBinderInfo(finalBarCode);
                    }
                }, 500);

            }
        }
    }

    class MyTextWatcher extends AfterTextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            changeMenuLayout();
        }
    }

    void changeMenuLayout() {
        llReplaceMenu.setVisibility(isAllSelect()? View.VISIBLE : View.GONE);
    }

}
