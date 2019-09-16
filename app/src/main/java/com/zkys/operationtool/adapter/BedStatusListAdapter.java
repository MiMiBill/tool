package com.zkys.operationtool.adapter;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.BedOrderStateBean;

import java.util.List;

public class BedStatusListAdapter extends BaseMultiItemQuickAdapter<BedOrderStateBean,
        BaseViewHolder> {

    public BedStatusListAdapter(List<BedOrderStateBean> data) {
        super(data);
        addItemType(1, R.layout.item_beds_normal_order);
        addItemType(2, R.layout.item_beds_exception_order);
    }


    @Override
    protected void convert(BaseViewHolder helper, BedOrderStateBean item) {
        if (helper.getItemViewType() == 1) {
            helper.setText(R.id.tv_normal_bed_number, "床位号：" + item.getBedNumber());
            helper.setText(R.id.tv_normal_usage_time, item.getLockTime());
            BedOrderStateBean.OrderBean order = item.getOrder();
            if (order != null) {
                helper.setText(R.id.tv_normal_heart_time, order.getReturnTimeFormat());
                switch (order.getStatus()) {
                    case 2:
                        helper.setText(R.id.tv_normal_device_status, "使用中");
                        break;
                    case 3:
                        helper.setText(R.id.tv_normal_device_status, "使用超时");
                        break;
                    case 4:
                        helper.setText(R.id.tv_normal_device_status, "已完成");
                        break;
                    default:
                        helper.setText(R.id.tv_normal_device_status, "已完成");
                        break;
                }
            }else {
                helper.setText(R.id.tv_normal_device_status, "暂无订单");
                helper.setText(R.id.tv_normal_heart_time, "暂无订单");
            }
        } else {
            helper.setText(R.id.tv_exception_bed_number, "床位号：" + item.getBedNumber());
            helper.setText(R.id.tv_exception_usage_time, item.getCreateTimeFormat());
            helper.setText(R.id.tv_exception_heart_status,item.getHeartStatusName());
            BedOrderStateBean.OrderBean order = item.getOrder();
            if (order != null) {
                helper.setText(R.id.tv_exception_heart_time, order.getReturnTimeFormat());
                switch (order.getStatus()) {
                    case 2:
                        helper.setText(R.id.tv_exception_device_status, "使用中");
                        break;
                    case 3:
                        helper.setText(R.id.tv_exception_device_status, "使用超时");
                        break;
                    case 4:
                        helper.setText(R.id.tv_exception_device_status, "已完成");
                        break;
                    default:
                        helper.setText(R.id.tv_exception_device_status, "已完成");
                        break;
                }
            }else {
                helper.setText(R.id.tv_exception_device_status, "暂无订单");
                helper.setText(R.id.tv_exception_heart_time, "暂无订单");
            }
        }
        helper.addOnClickListener(R.id.ll_item_normal, R.id.ll_item_exception, R.id
                .tv_normal_right_update, R.id.tv_exception_right_update);
    }

}

