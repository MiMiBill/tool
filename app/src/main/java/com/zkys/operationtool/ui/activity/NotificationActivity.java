package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.presenter.NotificationPresenter;
import com.zkys.operationtool.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity<NotificationPresenter> {
    @BindView(R.id.tv_nofi_num)
    TextView tvNofiNum;
    @BindView(R.id.rel_notification)
    RelativeLayout relNotification;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.rel_order)
    RelativeLayout relOrder;
    @Override
    public NotificationPresenter initPresenter() {
        return new NotificationPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("通知");
        presenter.getExpireTotal();
        presenter.getAlarmTotal();

        presenter.setOnExperTolalListener(new NotificationPresenter.OnExperTolalListener() {
            @Override
            public void onSuccess(HttpResponse result) {
                if (result != null && result.getCode() == 200) {
                    int size = UIUtils.getInt((Double) result.getData());
                    if (size > 0) {
                        tvOrderNum.setVisibility(View.VISIBLE);
                        tvOrderNum.setText(String.valueOf(size));
                    } else {
                        tvOrderNum.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.rel_notification, R.id.rel_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_notification:
                startActivity(new Intent(NotificationActivity.this, AlertInfoActivity.class));
                //警报信息
                break;
            case R.id.rel_order:
                startActivity(new Intent(NotificationActivity.this, OverdueActivity.class));
                break;
        }
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null && result.getCode() == 200) {
            int size = UIUtils.getInt((Double) result.getData());
            if (size > 0) {
                tvNofiNum.setVisibility(View.VISIBLE);
                tvNofiNum.setText(String.valueOf(size));
            } else {
                tvNofiNum.setVisibility(View.GONE);
            }
        }
    }
}
