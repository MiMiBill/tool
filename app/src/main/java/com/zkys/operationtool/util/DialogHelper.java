package com.zkys.operationtool.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Desc:
 * Author:
 * Date: 2018/4/26
 * Copyright (c) 2016, dianlibian.com All Rights Reserved
 */

public class DialogHelper {

    /**
     * progressDialog
     */
    private ProgressDialog progressDialog;

    public static DialogHelper getInstance() {
        return LoadDialogHolder.instance;
    }

    private static class LoadDialogHolder {
        static DialogHelper instance = new DialogHelper();
    }

    public void show(Context context, String msg){
        close();
        createDialog(context, msg);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 关闭加载
     */
    public void close() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 创建加载框
     * @param context
     * @param msg
     */
    private void createDialog(Context context, String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // 释放引用内存
                progressDialog = null;
            }
        });

    }

}
