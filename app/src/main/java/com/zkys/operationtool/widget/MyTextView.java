package com.zkys.operationtool.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zkys.operationtool.R;


/**
 * Desc:
 * Author:
 * Date: 2017/9/19
 * Copyright (c) 2016, dianlibian.com All Rights Reserved
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 取得自定义属性值
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyDrawableTextView);
        int drawableWidth = ta.getDimensionPixelSize(R.styleable.MyDrawableTextView_drawableWidth, -1);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.MyDrawableTextView_drawableHeight, -1);
        /**
         * 取得TextView的Drawable(左上右下四个组成的数组值)
         */
        Drawable[] drawables = getCompoundDrawables();
        Drawable textDrawable = null;
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                textDrawable = drawable;
            }
        }
        /**
         * 设置宽高
         */
        if (textDrawable != null && drawableWidth != -1 && drawableHeight != -1) {
            textDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        /**
         * 设置给TextView
         */
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        /**
         * 回收ta
         */
        ta.recycle();
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }
}
