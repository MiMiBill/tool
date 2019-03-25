package com.zkys.operationtool.adapter;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.BedOrderStateBean;

import java.util.List;

public class BedStatusListAdapter extends BaseMultiItemQuickAdapter<BedOrderStateBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BedStatusListAdapter(List<BedOrderStateBean> data) {
        super(data);
        addItemType(1, R.layout.item_beds_normal_order);
        addItemType(2, R.layout.item_beds_exception_order);
    }



    @Override
    protected void convert(BaseViewHolder helper, BedOrderStateBean item) {
        if (helper.getItemViewType() == 1) {
            helper.setText(R.id.tv_normal_bed_number, "床位号：" + item.getBedNumber());
            helper.setText(R.id.tv_normal_usage_time, item.getCreateTimeFormat());
            helper.setText(R.id.tv_normal_heart_time, item.getCreateTimeFormat());
            helper.setText(R.id.tv_normal_device_status, item.getDeviceStatus());
        } else {
            helper.setText(R.id.tv_exception_bed_number, "床位号：" + item.getBedNumber());
            helper.setText(R.id.tv_exception_usage_time, item.getCreateTimeFormat());
            helper.setText(R.id.tv_exception_heart_time, item.getCreateTimeFormat());
            helper.setText(R.id.tv_exception_device_status, item.getDeviceStatus());
        }
        helper.addOnClickListener(R.id.ll_item_normal, R.id.ll_item_exception, R.id.tv_normal_right_update,R.id.tv_exception_right_update);
    }

}

