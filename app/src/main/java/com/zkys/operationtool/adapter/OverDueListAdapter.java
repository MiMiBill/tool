package com.zkys.operationtool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.OverDueBean;
import com.zkys.operationtool.util.DateUtil;

import java.util.List;

public class OverDueListAdapter extends BaseQuickAdapter<OverDueBean.ArrayBean, BaseViewHolder> {

    public OverDueListAdapter(@Nullable List<OverDueBean.ArrayBean> data) {
        super(R.layout.item_overdue, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OverDueBean.ArrayBean item) {
        helper.setText(R.id.tv_date, DateUtil.timeStamp2Date(item.getLeaseTime() + "", "yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_address, item.getHospitalName() + "-" + item.getDeptName()
                + "-" + item.getBedNumber()+"订单已逾期! 请及时处理!");
        helper.addOnClickListener(R.id.ll_overdue);
    }
}
