package com.zkys.operationtool.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.RepairListBean;

import java.util.List;

public class RepairListAdapter extends BaseQuickAdapter<RepairListBean, BaseViewHolder> {
    private Context context;
    public RepairListAdapter(Context context, @Nullable List<RepairListBean> data) {
        super(R.layout.item_repair, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairListBean item) {
        helper.setText(R.id.tv_hos, item.getHospName()+"-"+item.getDeptName()+"-"+item.getBedNumber()+"床");
        helper.setText(R.id.tv_dept, item.getType());
        ImageView img = (ImageView)helper.getView(R.id.iv_status);
        switch (item.getStatus()){
            case 0:
                Glide.with(context).load(R.mipmap.processe_ing).into(img);
                break;
            case 1:
                Glide.with(context).load(R.mipmap.processe_ing).into(img);
                break;
            case 2:
                Glide.with(context).load(R.mipmap.processe_ing).into(img);
                break;
            case 3:
                Glide.with(context).load(R.mipmap.processed).into(img) ;
                break;
        }
    }
}
