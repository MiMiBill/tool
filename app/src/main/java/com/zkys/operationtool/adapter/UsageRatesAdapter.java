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
        }else {
            helper.setText(R.id.tv_hospital, item.getDeptName());
        }
        helper.setText(R.id.tv_date, item.getCreateTime());
        if(item.getOnRate()!=null){
            helper.setText(R.id.tv_on_rates, item.getOnRate() + "%");
        }else {
            helper.setText(R.id.tv_on_rates, "0%");
        }
        if(item.getPadUseRate()!=null){
            helper.setText(R.id.tv_usage_rates, item.getPadUseRate() + "%");
        }else {
            helper.setText(R.id.tv_usage_rates, "0%");
        }
        if(item.getDeviceRentRate()!=null){
            helper.setText(R.id.tv_rent_rates, item.getDeviceRentRate() + "%");
        }else {
            helper.setText(R.id.tv_rent_rates, "0%");
        }
        helper.setText(R.id.tv_on_count, item.getOnCount());
        helper.setText(R.id.tv_usage_count, item.getPadUseCount());
        helper.setText(R.id.tv_rent_count, item.getDeviceRentCount());
        helper.setText(R.id.tv_active_count, String.valueOf(item.getActivationCount()));
        helper.setText(R.id.tv_offline_count, String.valueOf(item.getOffLineCount()));

        if(TextUtils.isEmpty(item.getLockOnCount())){
            helper.setGone(R.id.lly_cabnet_count,false);
            helper.setGone(R.id.lly_cabniet,false);

        }else {
            helper.setGone(R.id.lly_cabnet_count,true);
            helper.setGone(R.id.lly_cabniet,true);
            helper.setText(R.id.tv_cab_on_rates, item.getLockOnRate()+ "%");
            helper.setText(R.id.tv_cab_usage_rates, item.getDeviceRentRate()+ "%");
            helper.setText(R.id.tv_cab_rent_rates, item.getDeviceRentRate()+ "%");
            helper.setText(R.id.tv_cab_active_count, String.valueOf(item.getLockActivationCount()));
            helper.setText(R.id.tv_cab_offline_count, String.valueOf(item.getLockOffLineCount()));
        }
    }
}
