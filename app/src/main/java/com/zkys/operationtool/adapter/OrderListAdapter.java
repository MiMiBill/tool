package com.zkys.operationtool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.OrderDataBean;
import com.zkys.operationtool.util.DateUtil;

import java.util.List;

public class OrderListAdapter extends BaseQuickAdapter<OrderDataBean.ArrayBean, BaseViewHolder> {

    public OrderListAdapter(@Nullable List<OrderDataBean.ArrayBean> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDataBean.ArrayBean item) {
        helper.setText(R.id.tv_term_desc, item.getTermDesc());
        helper.setText(R.id.tv_date, DateUtil.timeStamp2Date(item.getLeaseTime() + "", "yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_address, item.getHospitalName() + " " + item.getDeptName() + " " + item.getBedNumber());
        helper.setText(R.id.tv_money, "+" + item.getPayPrice());
    }
}
