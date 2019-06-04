package com.zkys.operationtool.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkys.operationtool.R;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.baseImpl.BaseView;
import com.zkys.operationtool.util.ActivityManager;
import com.zkys.operationtool.util.DialogHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 由于每一个view都对应不同的presenter。当然对应的每个presenter也同样对应一个view。所有我们使用接口和泛型来封装了。
 * Created by DGDL-08 on ${DATA}
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView, View.OnClickListener {
//public abstract class BaseActivityOld extends AppCompatActivity implements View.OnClickListener {
    //不加载标题栏
    public static final int NO_TITLE_VIEW = -1;
    //默认的标题栏
    public static final int DEFAULT_TITLE_VIEW = 0;
    protected P presenter;
    public Context context;
    private static List<BaseActivity> activityStack = new ArrayList<>();
    protected FrameLayout contentMain;
    ImageView ivBack;
    TextView tvTitle;
    TextView tvRight;

    private Unbinder binder;
    protected ImmersionBar mImmersionBar;
    protected final RxPermissions mRxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        context = this;
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_right).setOnClickListener(this);
        findViewById(R.id.fl_back).setOnClickListener(this);
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
//
        presenter = initPresenter();

//        titleBar = findViewById(R.id.titleBar);
        contentMain = findViewById(R.id.fl_content_main);
        tvTitle  = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        findViewById(R.id.titleBar).setVisibility(getTitleViewType() == DEFAULT_TITLE_VIEW ? View.VISIBLE : View.GONE);

        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        View contentView = View.inflate(context, getViewLayout(), null);
        contentMain.addView(contentView);
        binder = ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        ActivityManager.getAppInstance().removeActivity(this);//将当前activity移除管理栈
        if (presenter != null) {
            presenter.detach();//在presenter中解绑释放view
            presenter = null;
        }
        if (binder != null) {
            binder.unbind();
        }
        if (mImmersionBar != null) mImmersionBar.destroy();  //在BaseActivity里销毁
        super.onDestroy();
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();

    /**
     * 默认不加载标题栏
     *
     * @return
     */
    protected int getTitleViewType() {
        return NO_TITLE_VIEW;
    }

    @Override
    public void dismissLoadingDialog() {
        DialogHelper.getInstance().close();
    }

    @Override
    public void showLoadingDialog(String msg) {
        DialogHelper.getInstance().show(this, msg);
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onError_(Throwable e) {

    }

    /**
     * 是否使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        if (getTitleViewType() == DEFAULT_TITLE_VIEW) {
            mImmersionBar.titleBar(R.id.titleBar);
        }
        mImmersionBar.statusBarAlpha(0.0f)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式
                .init();
    }


    protected void onClickBack(View view) {
        finish();
    }
    protected void onClickTvRight(View view) {

    }
    protected void setTvRightText(String text) {
        tvRight.setText(text);
    }
    protected void setTvTitleText(String title) {
        tvTitle.setText(title);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.fl_back:
                onClickBack(v);
                break;
            case R.id.tv_right:
                onClickTvRight(v);
                break;
        }
    }

}
