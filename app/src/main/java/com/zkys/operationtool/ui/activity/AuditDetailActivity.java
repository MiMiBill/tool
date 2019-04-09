package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.AuditItemBean;
import com.zkys.operationtool.presenter.TeamAuditPresenter;
import com.zkys.operationtool.util.GlideUtil;
import com.zkys.operationtool.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AuditDetailActivity extends BaseActivity<TeamAuditPresenter> {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    private AuditItemBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("待审核");
        data = getIntent().getParcelableExtra("AuditItemBean");
        if (data != null) {
            initData();
        } else {
            ToastUtil.showShort("暂无数据");
        }
    }

    private void initData() {
        tvTeamName.setText(data.getName());
        GlideUtil.getInstance(this).showImage(data.getAuthenticateImage(), ivImage);
    }

    @Override
    public TeamAuditPresenter initPresenter() {
        return new TeamAuditPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_audit_detail;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getCode() == 200) {
            ToastUtil.showShort(result.getMsg());
            setResult(100);
            finish();
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void onError_(Throwable e) {

    }

    @OnClick({R.id.tv_no_pass, R.id.tv_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {// 状态：state 1：通过，2：不通过
            case R.id.tv_no_pass:
                presenter.audit(data.getId(), 2);
                break;
            case R.id.tv_pass:
                presenter.audit(data.getId(), 1);
                break;
        }
    }
}
