package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.pgyersdk.update.PgyUpdateManager;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.HomeListAdapter;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.HomeListBean;
import com.zkys.operationtool.canstant.Constant;
import com.zkys.operationtool.canstant.SharedConstant;
import com.zkys.operationtool.presenter.HomePresenter;
import com.zkys.operationtool.ui.dialog.SimpleDialogFragment;
import com.zkys.operationtool.util.ActivityManager;
import com.zkys.operationtool.util.DateUtil;
import com.zkys.operationtool.util.DensityUtil;
import com.zkys.operationtool.util.JpushUtil;
import com.zkys.operationtool.util.NotificationsUtils;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;
import com.zkys.operationtool.widget.VerticalLineDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 主菜单页面
 */
public class HomeActivity extends BaseActivity<HomePresenter> {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_binder_desc)
    TextView tvBinderDesc;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_role_name)
    TextView tvRoleName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean isBackPressed;
    private HomeListAdapter homeListAdapter;
    private List<HomeListBean> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRecyclerView();
        presenter.getAuthority();

        mImmersionBar.titleBar(R.id.ll_root).init();
        tvName.setText(UIUtils.getRegards() + "-" + MyApplication.getInstance().getUserInfo()
                .getName());
        tvBinderDesc.setText(tvBinderDesc.getText().toString().replace("0", MyApplication
                .getInstance().getUserInfo().getDeviceActiveCount() + ""));
        tvRoleName.setText(MyApplication.getInstance().getUserInfo().getTag());
        JpushUtil.setJPush();// 设置极光推送
        checkNotificationIsOpen();

        /** 新版本 **/
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新
                .setUserCanRetry(true)         //失败后是否提示重新下载
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                .register();
        try {
            String versionName = "版本：" + getPackageManager().getPackageInfo(getPackageName(), 0)
                    .versionName;
            tvVersionName.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 处理是否含有极光推送内容
        startAction();
    }


    /**
     * 检测应用消息通知是否开启
     */
    private void checkNotificationIsOpen() {
        if (!NotificationsUtils.isNotificationEnabled(this)) {
            // 未开启的情况，提示开启
            new SimpleDialogFragment().show("温馨提示", "您的消息通知未开启，将不能及时收到消息推送", "开启", "下次开启", new
                    DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermission(0b00111);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }, getSupportFragmentManager());
        }
    }

    // 进入系统设置界面
    protected void requestPermission(int requestCode) {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivityForResult(intent, requestCode);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.iv_login_out, R.id.iv_question_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_out:// 退出登录

                new SimpleDialogFragment().show("", "是否退出登录", "确定", "取消", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyApplication.getInstance().clearUserInfo();
                        MyApplication.getInstance().restartApplication();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, getSupportFragmentManager());

                break;
            case R.id.iv_question_answer:// 疑问解答
                startActivity(new Intent(this, WebViewActivity.class)
                        .putExtra("url", "http://taocan.zgzkys.com/#/answer"));
                break;

        }
    }

    @SuppressLint("CheckResult")
    void scanCode(int viewId, final Intent intent, final int requestCode) {
        RxView.clicks(findViewById(viewId))
                .compose(mRxPermissions.ensure(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startActivityForResult(intent, requestCode);
                        } else {
                            Log.d(HomeActivity.this.getClass().getSimpleName(), "没有授予相机权限");
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 ->" +
                                    " 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getData() != null) {
            if (result.getData() instanceof List) {
                list = (List<HomeListBean>) result.getData();
                homeListAdapter.setNewData(list);
            } else {
                ToastUtil.showShort("暂无数据");
            }
        }
    }


    private void initRecyclerView() {

        GridLayoutManager lin = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(lin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFadingEdgeLength(0);
        homeListAdapter = new HomeListAdapter(HomeActivity.this, list);
        recyclerView.setAdapter(homeListAdapter);
        recyclerView.setFocusable(false);
        homeListAdapter.setOnItemChildClickListener(listener);
        recyclerView.addItemDecoration(new VerticalLineDecoration(DensityUtil.dip2px(this, 12),
                true));

    }

    BaseQuickAdapter.OnItemChildClickListener listener = new BaseQuickAdapter
            .OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            HomeListBean listBean = list.get(position);
            switch (listBean.getCode()) {
                case Constant.ACTIVE_CODE:
                    startActivity(new Intent(HomeActivity.this, ActivePlateActivity.class));// 激活设备
                    break;
                case Constant.ALERT_CODE:
                    startActivity(new Intent(HomeActivity.this, NotificationActivity.class));  //通知
                    break;
                case Constant.AUDIT_CODE:
                    startActivity(new Intent(HomeActivity.this, TeamAuditActivity.class));  //团队审核
                    break;
                case Constant.CHANGE_CODE:
                    startActivity(new Intent(HomeActivity.this, ReplaceDeviceActivity.class));// 更换设备
                    break;
                case Constant.ORDER_CODE:
                    startActivity(new Intent(HomeActivity.this, OrderViewActivity.class));// 查看订单
                    break;
                case Constant.STATE_CODE:
                    startActivity(new Intent(HomeActivity.this, PlateStatusActivity.class));// 设备状态
                    break;
                case Constant.VOICE_CODE:
                    startActivity(new Intent(HomeActivity.this, VolumeControlActivity.class));// 音量控制
                    break;
                case Constant.TOOLS_CODE:
                    startActivity(new Intent(HomeActivity.this, ToolsActivity.class));  //工具
                    break;
                case Constant.REPAIR_CODE:
                    startActivity(new Intent(HomeActivity.this, RepairListActivity.class));  //报修
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            ActivityManager.getAppInstance().finishActivity(LoginActivity.class);
            ActivityManager.getAppInstance().finishAllActivity();
            return;
        }
        isBackPressed = true;
        ToastUtil.showShort("再按一次退出程序");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String barCode = "";
        if (data != null && data.getExtras() != null) {
            barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
            if (!TextUtils.isEmpty(barCode)) {
                String currentDate = DateUtil.timeStamp2Date((System.currentTimeMillis() / 1000)
                        + "", "yyyyMMdd");
                barCode = (barCode + currentDate).hashCode() + "";
                barCode = barCode.substring(barCode.length() - 6);
                new SimpleDialogFragment().show("温馨提示", "当前获取到的平板密码：" + barCode, "确定", "", new
                        DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, getSupportFragmentManager());
            } else {
                ToastUtil.showShort("没有找到结果");
            }
        }
    }

    /*@OnClick(R.id.iv_tool)
    public void onViewClicked() {
        scanCode(R.id.iv_tool, new Intent(this, CaptureActivity.class), 1000);
    }*/

    private void startAction() {
        //如果启动app的Intent中带有额外的参数，表明app是从点击通知栏的动作中启动的
        //将参数取出，传递到MainActivity中
        Bundle bundle = getIntent().getBundleExtra(SharedConstant.EXTRA_BUNDLE);
        if (bundle != null) {
            String msgType = bundle.getString(SharedConstant.MSGTYPE, "");
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(msgType)) {
                if (msgType.equals("1")) {
                    intent.setClass(this, CheckOrderActivity.class);
                } else if (msgType.equals("2")) {
                    intent.setClass(this, AlertInfoActivity.class);
                }
                intent.putExtra(SharedConstant.EXTRA_BUNDLE,
                        getIntent().getBundleExtra(SharedConstant.EXTRA_BUNDLE));
                startActivity(intent);
            }
        }
    }

}
