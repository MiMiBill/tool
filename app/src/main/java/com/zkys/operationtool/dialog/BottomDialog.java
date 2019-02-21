package com.zkys.operationtool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.zkys.operationtool.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomDialog extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.wheelview)
    WheelView wheelview;
    Unbinder unbinder;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private Context mContext;
    private View rootView;
    /**
     * 数据集合
     */
    private List<String> mList = new ArrayList<>();
    /**
     * 选择监听接口回调
     */
    private ItemSelectedInterface mItemSelectedInterface;

    public interface ItemSelectedInterface {
        void itemSelected(int position, int type);
    }

    public void setItemSelectedInterface(ItemSelectedInterface i) {
        this.mItemSelectedInterface = i;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_dialog_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showData();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //弹出对话框
        Dialog dialog = getDialog();//获取Dialog
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.zxing_transparent);
        WindowManager.LayoutParams attr = window.getAttributes();//获取Dialog属性
        WindowManager wm = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetric);
        attr.gravity = Gravity.BOTTOM;
        attr.width = WindowManager.LayoutParams.MATCH_PARENT;
        attr.height = (int) (outMetric.heightPixels * 0.3594f);
        dialog.getWindow().setAttributes(attr);
        dialog.setCancelable(true);
    }

    /**
     * 设置数据源
     * @param list
     */
    public void setData(List<String> list, int type){
        this.mType = type;
        mList.clear();
        mList.addAll(list);
    }

    private int mType = 0;
    private int mIndex = -1;
    /**
     * 显示数据
     */
    public void showData() {
        wheelview.setCyclic(false);
        wheelview.setAdapter(new ArrayWheelAdapter(mList));
        wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mIndex = index;
            }
        });
        if(mList.size() > 0){
            mIndex = 0;
        }
    }


    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //取消
            case R.id.tv_cancel:
                dismiss();
                break;
            //确定
            case R.id.tv_ok:
                if (mItemSelectedInterface != null) {
                    if(mIndex >= 0 && mIndex < mList.size()){
                        mItemSelectedInterface.itemSelected(mIndex, mType);
                    }
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}