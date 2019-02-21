package com.zkys.operationtool.ui.activity;

import android.os.Bundle;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.baseImpl.BasePresenter;

/**
 * 扫一扫开时长
 */
public class ScanOnTimeActivity extends BaseActivityOld {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("扫一扫开时长");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_scan_on_time;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponseOld result) {

    }

}
