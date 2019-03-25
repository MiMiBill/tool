package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.BedStatusListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.BedOrderStateBean;
import com.zkys.operationtool.dialog.UpdateBedNumberDialog;
import com.zkys.operationtool.presenter.PlateStatusPresenter;
import com.zkys.operationtool.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 某科室的平板状态列表
 */
public class BedsListActivity extends BaseActivity<PlateStatusPresenter> {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private String hid;
    private String cid;
    private String deptName;
    private List<BedOrderStateBean> orderStateBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hid = getIntent().getStringExtra("hid");
        cid = getIntent().getStringExtra("cid");
        deptName = getIntent().getStringExtra("DeptName");
        getPadOrderStatusData();
        setTvTitleText(deptName);
    }

    private void getPadOrderStatusData() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", hid);
        map.put("deptId", cid);
        presenter.getPadOrderStatusData(map);
    }

    @Override
    public PlateStatusPresenter initPresenter() {
        return new PlateStatusPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_beds_list;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }


    @Override
    public void setData(HttpResponse result) {
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                orderStateBeans = (List<BedOrderStateBean>) result.getData();
                if (orderStateBeans != null && orderStateBeans.size() > 0) {
                    initData();
                } else {
                    ToastUtil.showShort("暂无数据");
                }
            } else if (result.getCode() == 200) {
                ToastUtil.showShort("修改成功");
                getPadOrderStatusData();
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
    }

    private void initData() {
        BedStatusListAdapter adapter = new BedStatusListAdapter(orderStateBeans);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.ll_item_normal:
                    case R.id.ll_item_exception:
                        startActivity(new Intent(BedsListActivity.this, InfoDetailActivity.class)
                                .putExtra("cid", orderStateBeans.get(position).getDeptId())
                                .putExtra("hid", orderStateBeans.get(position).getHospitalId())
                                .putExtra("bedNumber", orderStateBeans.get(position).getBedNumber()));
                        break;
                    case R.id.tv_normal_right_update:
                    case R.id.tv_exception_right_update:

                        final UpdateBedNumberDialog dialog = new UpdateBedNumberDialog();
                        dialog.setTvCurrentNumber(orderStateBeans.get(position).getBedNumber());
                        dialog.show(getSupportFragmentManager(), "UpdateBedNumberDialog");
                        dialog.setOnClickUpdateListener(new UpdateBedNumberDialog.OnClickUpdateListener() {
                            @Override
                            public void onClickUpdate(String number) {
                               Map<String, Object> map = new HashMap<>();
                                map.put("id", orderStateBeans.get(position).getId());
                                map.put("bedNumber", number);
                                map.put("randomNumber", orderStateBeans.get(position).getRandomNumber());
                               presenter.updateBedNumber(map);
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });

        rcvList.setAdapter(adapter);
    }


}
