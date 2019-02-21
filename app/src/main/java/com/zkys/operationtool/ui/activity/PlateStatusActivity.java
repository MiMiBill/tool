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
 * 平板状态界面
 */
public class PlateStatusActivity extends BaseActivityOld {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("平板状态");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_plate_status;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.ll_item_hospital})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_item_hospital:
                startActivity(new Intent(this, PlateListActivity.class));
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
