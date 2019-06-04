package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.presenter.OverduePresenter;

import butterknife.BindView;

public class OverdueActivity extends BaseActivity<OverduePresenter> {

    @BindView(R.id.rel_nodata)
    RelativeLayout relNodata;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("逾期订单");
        presenter.wxOrderList(3);
    }

    @Override
    public OverduePresenter initPresenter() {
        return new OverduePresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_overdue;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if(result.getData()!=null && result.getData().equals("{}")){
            relNodata.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
        }else {
            relNodata.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }
    }
}
