package com.zkys.operationtool.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.jakewharton.rxbinding3.view.RxView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.LockInfo;
import com.zkys.operationtool.presenter.UnLockPresenter;
import com.zkys.operationtool.util.ToastUtil;
import com.zkys.operationtool.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 工具
 */
public class UnLockActivity extends BaseActivity<UnLockPresenter> {

    public static final int CABINET_REQUEST_CODE = 116;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("工具");
    }

    @Override
    public UnLockPresenter initPresenter() {
        return new UnLockPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_unlock;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getCode() == 200) {
            if (result.getData() instanceof LockInfo) {
                LockInfo lockInfo = (LockInfo) result.getData();
                tvResult.setText(lockInfo.toString());
            } else {
//                lock(etCode.getText().toString(), 1);
                tvResult.setText(getResources().getString(R.string.get_lock_code_status));
            }
        } else {
            tvResult.setText(result.getMsg());
        }
    }

    @Override
    public void onError_(Throwable e) {

    }

    @OnClick({R.id.ivScan, R.id.btn_unlock, R.id.btn_lock})
    public void onViewClicked(View view) {
        String code = etCode.getText().toString();
        switch (view.getId()) {
            case R.id.ivScan:
                Intent scanCodeIntent = new Intent(this, CaptureActivity.class);
                scanCode(R.id.ivScan, scanCodeIntent, CABINET_REQUEST_CODE);
                break;
            case R.id.btn_unlock:
                lock(code, 0);
                break;
            case R.id.btn_lock:
                lock(code, 1);
                break;
        }
    }

    private void lock(String code, int type) {
        if (!TextUtils.isEmpty(code)) {
            if (code.length() == 10) {
                if (type == 0) {
                    presenter.unlock(code);
                } else {
                    presenter.checkLock(code);
                }
            } else {
                ToastUtil.showShort("柜子码必须为10位");
            }
        } else {
            ToastUtil.showShort("柜子码不能为空");
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
                            Log.d(UnLockActivity.this.getClass().getSimpleName(), "没有授予相机权限");
                            ToastUtil.showLong("部分权限未正常授予, 当前位置需要访问 “拍照” 权限，为了该功能正常使用，请到 “应用信息 -> 权限管理” 中授予！");
                            XXPermissions.gotoPermissionSettings(context);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String barCode = "";
        if (data != null) {
            if (data.getExtras() != null) {
                barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
                if (UIUtils.isUrl(barCode) && barCode.contains("=") && barCode.lastIndexOf("=") != barCode.length() - 1) {
                    barCode = barCode.substring(barCode.lastIndexOf("=") + 1);
                    etCode.setText(barCode);
                } else if (UIUtils.isNumeric(barCode) && barCode.length() == 10) {
                    barCode = data.getExtras().getString(CodeUtils.RESULT_STRING, "");
                    etCode.setText(barCode);
                } else {
                    ToastUtil.showShort("请扫描正确的二维码");
                }
            }


        }
    }
}
