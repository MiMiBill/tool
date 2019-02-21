package com.zkys.operationtool.ui.activity;

import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

/**
 * 绑定条形码
 */
public class BinderBarCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("绑定条码");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_binder_bar_code;
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
