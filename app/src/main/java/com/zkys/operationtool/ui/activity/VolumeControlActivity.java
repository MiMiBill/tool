package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.VolumeInfoBean;
import com.zkys.operationtool.dialog.BottomDialog;
import com.zkys.operationtool.presenter.VolumeControlPresenterOld;
import com.zkys.operationtool.util.LogOutUtil;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.widget.AfterTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 音量控制
 */
public class VolumeControlActivity extends BaseActivity<VolumeControlPresenterOld> implements BottomDialog.ItemSelectedInterface, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.tv_selected_hospital)
    TextView tvSelectedHospital;
    @BindView(R.id.tv_selected_core)
    TextView tvSelectedCore;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private BottomDialog bottomDialog;
    private List<HospitalBean> hospitalBeanList;
    private List<CoreBean> coreBeanList;
    private List<String> hospitalNames = new ArrayList<>();
    private List<String> coreNames = new ArrayList<>();
    private int hid;
    private int cid;
    private int volume = 0;
    private VolumeInfoBean volumeInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("音量控制");
        initDialog();
        tvVolume.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(this);
        tvSelectedHospital.addTextChangedListener(new MyTextWatcher());
        tvSelectedCore.addTextChangedListener(new MyTextWatcher());
    }

    @Override
    public VolumeControlPresenterOld initPresenter() {
        return new VolumeControlPresenterOld(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_volume_control;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    private void initDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomDialog();
            bottomDialog.setItemSelectedInterface(this);
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
                    } else if (list.get(0) instanceof VolumeInfoBean) {
                        volumeInfoBean = (VolumeInfoBean) list.get(0);
                        seekBar.setProgress(volumeInfoBean.getVolume());
                    }
                } else {
                    ToastUtil.showShort("暂无数据");
                }
            }
        } else if (result.getCode() == 200) {
            ToastUtil.showShort("修改成功");
            finish();
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void onError_(Throwable e) {

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
            presenter.getPadVolume(hid, cid);
        }
    }

    @OnClick({R.id.rl_select_hospital, R.id.rl_select_core, R.id.tv_confirm})
    public void onViewClicked(View view) {
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
            case R.id.tv_confirm:
                if (volumeInfoBean != null) presenter.controlPadVolume(hid, cid, volume, volumeInfoBean.getId());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvVolume.setText(String.valueOf(progress));
        volume = progress;
        changeBtnState();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        changeBtnState();
    }

    class MyTextWatcher extends AfterTextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            changeBtnState();
        }
    }

    void changeBtnState() {
        String selectedHospital = tvSelectedHospital.getText().toString().trim();
        String selectedCore = tvSelectedCore.getText().toString().trim();
        boolean enable = !TextUtils.isEmpty(selectedHospital) && !TextUtils.isEmpty(selectedCore) && volume > 0;
        tvConfirm.setEnabled(enable);
    }

}
