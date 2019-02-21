package com.zkys.operationtool.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

public class SimpleDialogFragment extends DialogFragment {

    private DialogInterface.OnClickListener positiveCallback;

    private DialogInterface.OnClickListener negativeCallback;

    private String title;

    private String message;
    private String positiveText = "确定";
    private String negativeText = "取消";

    public void show(String title, String message, String positiveText, String negativeText, DialogInterface.OnClickListener positiveCallback,
                     DialogInterface.OnClickListener negativeCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.message = message;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        show(fragmentManager, "ButtonDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveCallback);
        builder.setNegativeButton(negativeText, negativeCallback);
        return builder.create();
    }





}