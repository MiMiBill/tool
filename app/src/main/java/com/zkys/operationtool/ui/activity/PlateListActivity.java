package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

import butterknife.OnClick;

/**
 * 某医院的科室的平板列表
 */
public class PlateListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("东莞大朗人民医院");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_plate_list;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick(R.id.ll_item_plate)
    public void onViewClicked() {
        startActivity(new Intent(this, BedsListActivity.class));
    }

    @Override
    public void setData(HttpResponse result) {

    }
}
