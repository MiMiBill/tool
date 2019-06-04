package com.zkys.operationtool.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;
import com.zkys.operationtool.bean.HomeListBean;
import com.zkys.operationtool.canstant.Constant;

import java.util.List;

public class HomeListAdapter extends BaseQuickAdapter<HomeListBean, BaseViewHolder> {

    private Context context;

    public HomeListAdapter(Context context, List<HomeListBean> data) {
        super(R.layout.item_list_home, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeListBean item) {
        helper.setText(R.id.tv_title, item.getName());
        ImageView imageView = helper.getView(R.id.iv_img);
        switch (item.getCode()) {
            case Constant.ACTIVE_CODE:
                Glide.with(context).load(R.mipmap.icon_activation).into(imageView);
                break;
            case Constant.ALERT_CODE:
                Glide.with(context).load(R.mipmap.notification).into(imageView);
                break;
            case Constant.AUDIT_CODE:
                Glide.with(context).load(R.mipmap.icon_examine).into(imageView);
                break;
            case Constant.CHANGE_CODE:
                Glide.with(context).load(R.mipmap.icon_switch).into(imageView);
                break;
            case Constant.ORDER_CODE:
                Glide.with(context).load(R.mipmap.icon_order).into(imageView);
                break;
            case Constant.STATE_CODE:
                Glide.with(context).load(R.mipmap.icon_state).into(imageView);
                break;
            case Constant.VOICE_CODE:
                Glide.with(context).load(R.mipmap.icon_volume).into(imageView);
                break;
            case Constant.TOOLS_CODE:
                Glide.with(context).load(R.mipmap.icon_tool).into(imageView);
                break;
                default:
                    Glide.with(context).load(R.mipmap.icon_activation).into(imageView);
                    break;
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnItemChildClickListener().onItemChildClick(HomeListAdapter.this, v, getData().indexOf(item));
            }
        });
    }
}

