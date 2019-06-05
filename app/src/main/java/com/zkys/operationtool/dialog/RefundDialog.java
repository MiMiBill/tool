package com.zkys.operationtool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.util.ToastUtil;

import butterknife.Unbinder;

public class RefundDialog extends Dialog {
    private View rootView;
    private String bedNumber;
    private Unbinder unbinder;
    private int status=4;
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private RadioGroup rgSelect;
    private TextView tv_refun;
    private TextView tv_remark;
    private TextView tvRefunPrice;
    private RelativeLayout rel_close;

    public RefundDialog(Context context, boolean isCancelable) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.iscancelable = isCancelable;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refun);
        setCancelable(iscancelable);//点击外部不可dismiss
//        setCanceledOnTouchOutside(isBackCanCelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        rgSelect = window.findViewById(R.id.rg_select);
        tv_refun = window.findViewById(R.id.tv_refun);
        tv_remark = window.findViewById(R.id.tv_remark);
        tvRefunPrice = window.findViewById(R.id.tv_refun_price);
        rel_close = window.findViewById(R.id.rel_close);

        rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_yes) {
                    status= 4;
                } else if (checkedId == R.id.rb_no) {
                    status = 5;
                }
            }
        });


        tv_refun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refunPrice= tvRefunPrice.getText().toString().trim();
                String remark= tv_remark.getText().toString().trim();
                if(refunPrice.equals("")){
                    ToastUtil.showShort("请输入退款金额");
                    return;
                }
                if(remark.equals("")){
                    ToastUtil.showShort("请输入退款说明");
                    return;
                }

                if(onClickUpdateListener!=null)
                    onClickUpdateListener.onClickRefun(refunPrice,remark,status);
            }
        });

        rel_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 选择监听接口回调
     */
    private OnClickRefunListener onClickUpdateListener;

    public interface OnClickRefunListener {
        void onClickRefun(String number,String remark,int status);
    }

    public void setOnClickUpdateListener(OnClickRefunListener listener) {
        this.onClickUpdateListener = listener;
    }
}
