package com.zkys.operationtool.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.AlertBean;
import com.zkys.operationtool.util.DateUtil;

import java.util.List;

public class AlertInfoListAdapter extends BaseQuickAdapter<AlertBean, BaseViewHolder> {


    public AlertInfoListAdapter(@Nullable List<AlertBean> data) {
        super(R.layout.item_alert_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_date, DateUtil.timeStamp2Date(String.valueOf((int)(item.getCreateTime()/1000)), null));
    }
}

