package com.zkys.operationtool.ui.activity;

import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.baseImpl.BasePresenter;

/**
 * 信息提交
 */
public class InfoCommitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("信息提交");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_info_commit;
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
