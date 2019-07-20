package com.zkys.operationtool.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zkys.operationtool.R;

public class EffectPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private OnItemClickListener itemClickListener;
    public EffectPopupWindow(Context context) {
        this.mContext = context;

        mInflater = LayoutInflater.from(mContext);
        mActivity = (Activity) context;
        initPopup();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopup() {
        View view = mInflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        //设置PopupWindow宽高
        WindowManager windowManager = mActivity.getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
        int width=(int)mContext.getResources().getDimension(R.dimen.padding_124);
        setWidth(width);
        setHeight((int)mContext.getResources().getDimension(R.dimen.padding_160));
        //设置背景
        ColorDrawable dw = new ColorDrawable(0x60000000);
        setBackgroundDrawable(dw);
        setOutsideTouchable(true);

        TextView tv_status_none = view.findViewById(R.id.tv_status_none);
        TextView tv_status_confirm = view.findViewById(R.id.tv_status_confirm);
        TextView tv_status_doing = view.findViewById(R.id.tv_status_doing);
        TextView tv_status_done = view.findViewById(R.id.tv_status_done);
        tv_status_none.setOnClickListener(this);
        tv_status_confirm.setOnClickListener(this);
        tv_status_doing.setOnClickListener(this);
        tv_status_done.setOnClickListener(this);
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public interface OnItemClickListener{
        void onItemCLick(int position);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 设置PopupWindow的位置
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 2);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_status_none:
                itemClickListener.onItemCLick(0);
                break;
            case R.id.tv_status_confirm:
                itemClickListener.onItemCLick(1);
                break;
            case R.id.tv_status_doing:
                itemClickListener.onItemCLick(2);
                break;
            case R.id.tv_status_done:
                itemClickListener.onItemCLick(3);
                break;
        }
    }
}
