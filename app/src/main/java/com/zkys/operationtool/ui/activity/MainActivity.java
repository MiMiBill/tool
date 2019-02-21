package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.gyf.barlibrary.OnKeyboardListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.baseImpl.BasePresenter;

/**
 * 主页面
 */
public class MainActivity extends BaseActivityOld {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar
//                .titleBar(R.id.ll_root)
                .setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup) {
                    Toast.makeText(context, "弹出框", Toast.LENGTH_SHORT).show();
                } else {
                }
            }
        });
        setTvTitleText("信息提交");
        setTvRightText("菜单");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_info_commit;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponseOld result) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
