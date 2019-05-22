package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.AlertInfoListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.AlertBean;
import com.zkys.operationtool.presenter.AlertInfoPresenter;
import com.zkys.operationtool.util.LogOutUtil;
import com.zkys.operationtool.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class AlertInfoActivity extends BaseActivity<AlertInfoPresenter> {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rel_nodata)
    RelativeLayout RelNodata;
    private List<AlertBean> alertBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("警报信息");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getAlertInfo();
            }
        });
        presenter.getAlertInfo();
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public AlertInfoPresenter initPresenter() {
        return new AlertInfoPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_alert_info;
    }

    @Override
    public void setData(HttpResponse result) {
        if(result.getCode()==1001){ //token失效,退出登录
            LogOutUtil.LogOut();
        }
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                alertBeans = (List<AlertBean>) result.getData();
                if (alertBeans != null && alertBeans.size() > 0) {
                    initData();
                } else {
                    RelNodata.setVisibility(View.VISIBLE);
                }
            } else {
                RelNodata.setVisibility(View.VISIBLE);
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            RelNodata.setVisibility(View.VISIBLE);
            ToastUtil.showShort("数据获取失败");
        }
        refreshLayout.finishRefresh();
        refreshLayout.resetNoMoreData();
    }

    private void initData() {
        AlertInfoListAdapter adapter = new AlertInfoListAdapter(alertBeans);
        rcvList.setAdapter(adapter);
    }

    @Override
    public void onError_(Throwable e) {
        refreshLayout.finishRefresh();
        refreshLayout.resetNoMoreData();
    }

}
