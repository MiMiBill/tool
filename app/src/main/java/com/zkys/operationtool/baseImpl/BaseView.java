package com.zkys.operationtool.baseImpl;

import com.zkys.operationtool.base.HttpResponse;

/**
 * Created by DGDL-08 on ${DATA}
 */
public interface BaseView {
    /**
     * 设置数据
     *
     * @param result
     */
    void setData(HttpResponse result);

    //显示dialog
    void showLoadingDialog(String msg);

    //取消dialog
    void dismissLoadingDialog();

    void onError_(Throwable e);

}
