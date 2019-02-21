package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

import butterknife.OnClick;

/**
 * 休眠设备
 */
public class SelectDormancyDeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("休眠设备");
        setTvRightText("全选");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_select_dormancy_devices;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        startActivity(new Intent(this, DormancyDeviceActivity.class));
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
