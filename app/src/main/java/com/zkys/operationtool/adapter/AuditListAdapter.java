package com.zkys.operationtool.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.AuditItemBean;

import java.util.List;

public class AuditListAdapter extends BaseQuickAdapter<AuditItemBean, BaseViewHolder> {

    public AuditListAdapter(@Nullable List<AuditItemBean> data) {
        super(R.layout.item_audit, data);

    }

    @Override
    protected void convert(final BaseViewHolder helper, AuditItemBean item) {
        int isAuthenticate = item.getIsAuthenticate();
        if (isAuthenticate == 2) {// (认证状态，0：未认证；1：认证通过；2：认证中)
            helper.setText(R.id.tv_state,  "审核中");
        }
        helper.setText(R.id.tv_time, item.getApplyDate());
        helper.setText(R.id.tv_team_name, item.getName());
        helper.setText(R.id.tv_hospital, item.getHospitalName());
        helper.setText(R.id.tv_core, item.getDeptName());

    }

}
