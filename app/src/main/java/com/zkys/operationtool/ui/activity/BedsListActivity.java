package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.baseImpl.BasePresenter;

import butterknife.OnClick;

/**
 * 某科室的平板状态列表
 */
public class BedsListActivity extends BaseActivityOld {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("第二住院部1楼呼吸科三号");
        setTvRightText("休眠");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_beds_list;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @Override
    protected void onClickTvRight(View view) {
        super.onClickTvRight(view);
        startActivity(new Intent(this, SelectDormancyDeviceActivity.class));
    }

    @OnClick({R.id.ll_item_normal, R.id.ll_item_exception})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_item_normal:
            case R.id.ll_item_exception:
                startActivity(new Intent(this, InfoDetailActivity.class));
                break;
        }
    }

    @Override
    public void setData(HttpResponseOld result) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
