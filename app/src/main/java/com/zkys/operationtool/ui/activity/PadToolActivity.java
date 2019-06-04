package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.dialog.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
//生产工具
public class PadToolActivity extends BaseActivity implements BottomDialog.ItemSelectedInterface{

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
    private BottomDialog bottomDialog;
    private List<String> toolNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("生产工具");
        toolNames.add("物网通 - 3");
        toolNames.add("物联锁 - 2");
        toolNames.add("连旅 - 1");
        toolNames.add("预留 - 4");

        initDialog();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_pad_tool;
    }

    @Override
    public void setData(HttpResponse result) {

    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.tv_busy, R.id.iv_scan_device_code, R.id.iv_scan_lock_code, R.id.btn_bind, R.id
            .btn_unbind, R.id.btn_unlock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_busy:
                initDialogDataAndShow(toolNames,0);
                break;
            case R.id.iv_scan_device_code:
                break;
            case R.id.iv_scan_lock_code:
                break;
            case R.id.btn_bind:
                break;
            case R.id.btn_unbind:
                break;
            case R.id.btn_unlock:
                break;
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
            toolNames.clear();
            toolNames.addAll(names);
                if (toolNames.size() > 0) {
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
        }
    }
}
