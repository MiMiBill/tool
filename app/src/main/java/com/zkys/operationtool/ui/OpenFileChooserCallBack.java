package com.zkys.operationtool.ui;

import android.net.Uri;
import android.webkit.ValueCallback;

public interface OpenFileChooserCallBack {
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
        void cancelFileChooserCallBack();
    }