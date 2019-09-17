package com.zkys.operationtool.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkys.operationtool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */

public class EffectAdapter extends BaseQuickAdapter<String, EffectAdapter.SpinerHolder> {
    private List<String> spinerList ;
    public int lastPressIndex = -1;
    private Context context;
    public OnItemClickListener mItemClickListener;

    public EffectAdapter(Context context,@Nullable List<String> data) {
        super(R.layout.effect_window_layout_item, data);
        this.context=context;
        spinerList=new ArrayList<>();
        if(data!=null){
            spinerList.addAll(data);
        }
    }



    public void nodfiyData(List<String> list) {
        if (list != null) {
            this.spinerList.clear();
            this.spinerList.addAll(list);
        }
        lastPressIndex = -1;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(SpinerHolder helper, String item) {
        if (item == null)
            return;

        helper.tv.setText(item);
       /* if (item.isSelected()) {
            helper.tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            helper.iv_selected.setImageResource(R.mipmap.login_circle_selected);
        } else {
            helper.tv.setTextColor(context.getResources().getColor(R.color.color_666666));
            helper.iv_selected.setImageResource(R.mipmap.login_default);
        }*/
    }

    @Override
    public int getItemCount() {
        return spinerList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class SpinerHolder extends BaseViewHolder {
        ImageView iv_selected;
        TextView tv;

        public SpinerHolder(View view) {
            super(view);
            iv_selected = itemView.findViewById(R.id.iv_selected);
            tv = itemView.findViewById(R.id.item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();
                    mItemClickListener.onItemClick(position);
                }
            });
        }
    }
}
