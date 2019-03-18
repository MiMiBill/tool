package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.AuditListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.AuditItemBean;
import com.zkys.operationtool.presenter.TeamAuditPresenter;
import com.zkys.operationtool.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class TeamAuditActivity extends BaseActivity<TeamAuditPresenter> {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private AuditListAdapter adapter;
    private List<AuditItemBean> auditItemBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("团队审核");
        presenter.getTeamAuditData();
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public TeamAuditPresenter initPresenter() {
        return new TeamAuditPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_team_audit;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null) {
            if (result.getData() instanceof List) {
                if (((List) result.getData()).size() > 0 && ((List) result.getData()).get(0) instanceof AuditItemBean) {
                    bindData(result);
                } else {
                    bindData(result);
                    ToastUtil.showShort("暂无数据");
                }
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
    }

    private void bindData(HttpResponse result) {
        auditItemBeans = (List<AuditItemBean>) result.getData();
        adapter = new AuditListAdapter(auditItemBeans);
        rcvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivityForResult(new Intent(TeamAuditActivity.this, AuditDetailActivity.class)
                        .putExtra("AuditItemBean", auditItemBeans.get(position)), 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            presenter.getTeamAuditData();
        }
    }
}
