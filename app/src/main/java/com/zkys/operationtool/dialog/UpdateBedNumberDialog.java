package com.zkys.operationtool.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpdateBedNumberDialog extends DialogFragment {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    Unbinder unbinder;
    @BindView(R.id.tv_current_number)
    TextView tvCurrentNumber;
    @BindView(R.id.et_bed_number)
    EditText etBedNumber;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private View rootView;
    private String bedNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_update_bed_number_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCurrentNumber.setText("当前的床位号：" + bedNumber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                String replace = etBedNumber.getText().toString().replace(" ", "");
                if (!TextUtils.isEmpty(replace)) {
                    if (onClickUpdateListener != null) {
                        onClickUpdateListener.onClickUpdate(replace);
                    }
                } else {
                    ToastUtil.showShort("床位号输不能为空");
                }
                break;
        }
    }

    /**
     * 显示当前的床位号
     * @param number
     */
    public void setTvCurrentNumber(String number) {
        bedNumber = number;
    }

    /**
     * 选择监听接口回调
     */
    private OnClickUpdateListener onClickUpdateListener;

    public interface OnClickUpdateListener {
        void onClickUpdate(String number);
    }

    public void setOnClickUpdateListener(OnClickUpdateListener listener) {
        this.onClickUpdateListener = listener;
    }
}
