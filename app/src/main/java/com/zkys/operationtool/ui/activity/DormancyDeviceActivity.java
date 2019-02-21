package com.zkys.operationtool.ui.activity;

import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

/**
 * 休眠设备
 */
public class DormancyDeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("休眠设备");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_dormancy_device;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

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
