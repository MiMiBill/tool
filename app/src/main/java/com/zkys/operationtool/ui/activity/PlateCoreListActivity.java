package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.UsageRatesAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.ItemUsageRatesBean;
import com.zkys.operationtool.presenter.PlateStatusPresenter;
import com.zkys.operationtool.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 某医院的科室的平板列表
 */
public class PlateCoreListActivity extends BaseActivity<PlateStatusPresenter> {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String hid;
    private List<ItemUsageRatesBean> usageRatesBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String hospitalName = getIntent().getStringExtra("HospitalName");
        setTvTitleText(hospitalName);
        hid = getIntent().getStringExtra("hid");
        final Map<String, Object> map = new HashMap<>();
        map.put("id", hid);
        presenter.getAllDeptPadUsageRates(map);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getAllDeptPadUsageRates(map);
            }
        });
    }

    @Override
    public PlateStatusPresenter initPresenter() {
        return new PlateStatusPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_plate_list;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                usageRatesBeans = (List<ItemUsageRatesBean>) result.getData();
                if (usageRatesBeans != null && usageRatesBeans.size() > 0) {
                    initData();
                } else {
                    ToastUtil.showShort("暂无数据");
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
                startActivity(new Intent(PlateCoreListActivity.this, BedsListActivity.class)
                        .putExtra("cid", usageRatesBeans.get(position).getDeptId())
                        .putExtra("hid", usageRatesBeans.get(position).getHospitalId())
                        .putExtra("DeptName", usageRatesBeans.get(position).getDeptName()));
            }
        });
    }

}
