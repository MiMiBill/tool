package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.UsageRatesAdapter;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.ItemUsageRatesBean;
import com.zkys.operationtool.presenter.PlateStatusPresenter;
import com.zkys.operationtool.util.LogOutUtil;
import com.zkys.operationtool.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 平板状态界面
 */
public class PlateStatusActivity extends BaseActivity<PlateStatusPresenter> {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_network_exception)
    RelativeLayout rlNetworkException;
    private List<ItemUsageRatesBean> usageRatesBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("平板状态");
        final Map<String, Object> map = new HashMap<>();
        map.put("id", MyApplication.getInstance().getUserInfo().getId());
//        map.put("sydicId", MyApplication.getInstance().getUserInfo().getCorrelationId());
        presenter.getAllPadUsageRates(map);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getAllPadUsageRates(map);
            }
        });


    }

    @Override
    public PlateStatusPresenter initPresenter() {
        return new PlateStatusPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_plate_status;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.ll_item_hospital})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_item_hospital:
//                startActivity(new Intent(this, PlateCoreListActivity.class));
                break;

        }
    }

    @Override
    public void setData(HttpResponse result) {
        if(result.getCode()==1001){ //token失效,退出登录
            LogOutUtil.LogOut();
        }
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                usageRatesBeans = (List<ItemUsageRatesBean>) result.getData();
                if (usageRatesBeans != null && usageRatesBeans.size() > 0) {
                    initData();
                } else {
                    rlNetworkException.setVisibility(View.VISIBLE);
                }
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
        refreshLayout.finishRefresh();
        refreshLayout.resetNoMoreData();
    }

    @Override
    public void onError_(Throwable e) {
        refreshLayout.finishRefresh();
        refreshLayout.resetNoMoreData();
    }

    private void initData() {
        UsageRatesAdapter adapter = new UsageRatesAdapter(usageRatesBeans);
        rcvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(PlateStatusActivity.this, PlateCoreListActivity.class)
                        .putExtra("hid", usageRatesBeans.get(position).getHospitalId())
                        .putExtra("HospitalName", usageRatesBeans.get(position).getHospitalName()));
            }
        });
    }

}
