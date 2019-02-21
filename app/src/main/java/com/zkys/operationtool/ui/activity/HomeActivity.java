package com.zkys.operationtool.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.ui.dialog.SimpleDialogFragment;
import com.zkys.operationtool.util.ActivityManager;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主菜单页面
 */
public class HomeActivity extends BaseActivityOld {

    @BindView(R.id.tv_name)
    TextView tvName;
    private boolean isBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar.titleBar(R.id.ll_root)
                .init();
        tvName.setText(UIUtils.getRegards() + "-" + MyApplication.getInstance().getUserInfo().getName());
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.iv_active_plate, R.id.iv_replace_device, R.id.iv_check_order, R.id.iv_plate_status,
            R.id.iv_volume_control, R.id.iv_free_time, R.id.iv_binder_bar_code,
            R.id.iv_inspection_feedback, R.id.iv_info_commit, R.id.iv_login_out, R.id.iv_alert_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_active_plate:
                startActivity(new Intent(this, ActivePlateActivity.class));// 激活设备
                break;
            case R.id.iv_replace_device:
                startActivity(new Intent(this, ReplaceDeviceActivity.class));// 更换设备
                break;
            case R.id.iv_check_order:
                startActivity(new Intent(this, CheckOrderActivity.class));// 查看订单
                break;
            case R.id.iv_plate_status:
                startActivity(new Intent(this, PlateStatusActivity.class));// 设备状态
                break;
            case R.id.iv_volume_control:
                startActivity(new Intent(this, VolumeControlActivity.class));// 音量控制
                break;
            case R.id.iv_free_time:
                startActivity(new Intent(this, ScanOnTimeActivity.class));// 免费时长（隐藏）
                break;
            case R.id.iv_binder_bar_code:
                startActivity(new Intent(this, BinderBarCodeActivity.class));// 绑定条形码
                break;
            case R.id.iv_inspection_feedback:
                startActivity(new Intent(this, InspectionFeedbackActivity.class));// 巡检反馈（隐藏）
                break;
            case R.id.iv_info_commit:
                startActivity(new Intent(this, InfoCommitActivity.class));// 信息提交（隐藏）
                break;
            case R.id.iv_login_out:// 退出登录

                new SimpleDialogFragment().show("", "是否退出登录", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyApplication.getInstance().clearUserInfo();
                        MyApplication.getInstance().restartApplication();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                },getSupportFragmentManager());

                break;
            case R.id.iv_alert_info:// 警报信息
                startActivity(new Intent(this, AlertInfoActivity.class));
                break;
        }
    }

    @Override
    public void setData(HttpResponseOld result) {

    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            ActivityManager.getAppInstance().finishActivity(LoginActivity.class);
            ActivityManager.getAppInstance().finishAllActivity();
            return;
        }
        isBackPressed = true;
        ToastUtil.showShort("再按一次退出程序");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }
}
