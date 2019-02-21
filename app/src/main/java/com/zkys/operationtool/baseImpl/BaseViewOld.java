package com.zkys.operationtool.baseImpl;

import com.zkys.operationtool.base.HttpResponseOld;

/**
 * Created by DGDL-08 on ${DATA}
 */
@Deprecated
public interface BaseViewOld {
    /**
     * 设置数据
     *
     * @param result
     */
    void setData(HttpResponseOld result);

    //显示dialog
    void showLoadingDialog(String msg);

    //取消dialog
    void dismissLoadingDialog();

}
