package com.zkys.operationtool.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.ItemUsageRatesBean;

import java.util.List;

public class UsageRatesAdapter extends BaseQuickAdapter<ItemUsageRatesBean, BaseViewHolder> {

    public UsageRatesAdapter(@Nullable List<ItemUsageRatesBean> data) {
        super(R.layout.item_device_status, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemUsageRatesBean item) {
        if (!TextUtils.isEmpty(item.getHospitalName())) {
            helper.setText(R.id.tv_hospital, item.getHospitalName());
        } else {
            helper.setText(R.id.tv_hospital, item.getDeptName());
        }
        helper.setText(R.id.tv_date, item.getCreateTime());
        helper.setText(R.id.tv_on_rates, item.getOnRate() + "%");
        helper.setText(R.id.tv_on_count, item.getOnCount());
        helper.setText(R.id.tv_usage_rates, item.getPadUseRate() + "%");
        helper.setText(R.id.tv_usage_count, item.getPadUseCount());
        helper.setText(R.id.tv_rent_rates, item.getDeviceRentRate() + "%");
        helper.setText(R.id.tv_rent_count, item.getDeviceRentCount());
        helper.setText(R.id.tv_active_count, item.getActivationCount());
    }
}
