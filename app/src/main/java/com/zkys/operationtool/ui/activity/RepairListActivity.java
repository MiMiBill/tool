package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.RepairListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.RepairListBean;
import com.zkys.operationtool.event.RefreshRepairEvent;
import com.zkys.operationtool.presenter.RepairListPresenter;
import com.zkys.operationtool.util.LogFactory;
import com.zkys.operationtool.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//维修列表
public class RepairListActivity extends BaseActivity<RepairListPresenter> implements PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.lly_status)
    LinearLayout llyStatus;
    private List<RepairListBean> repairListBeanList = new ArrayList<>();
    private List<RepairListBean> temprepairListBeanList = new ArrayList<>();
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int pageNum = 1;
    private RepairListAdapter repairListAdapter;
    private int status = -1;
    private PopupMenu popupMenu;

    @Override
    public RepairListPresenter initPresenter() {
        return new RepairListPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_repair_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        setTvTitleText("维修列表");

        repairListAdapter = new RepairListAdapter(this, repairListBeanList);
        initView();
        presenter.repairList(1, status);
    }


    private void initView() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                presenter.repairList(1, status);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                LogFactory.l().e("pageNum===" + pageNum);
                presenter.repairList(pageNum, status);
            }
        });
    }

    @Override
    public void setData(HttpResponse result) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                if (pageNum == 1) {
                    if (temprepairListBeanList != null) {
                        temprepairListBeanList.clear();
                    }
                    repairListBeanList = (List<RepairListBean>) result.getData();
                    if (repairListBeanList.size() < 10) {
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        refreshLayout.setEnableLoadMore(true);
                    }
                } else {
                    temprepairListBeanList = (List<RepairListBean>) result.getData();
                    if (temprepairListBeanList.size() < 10) {
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        refreshLayout.setEnableLoadMore(true);
                    }
                    repairListBeanList.addAll(temprepairListBeanList);
                }

                repairListAdapter.setNewData(repairListBeanList);
                rcvList.setAdapter(repairListAdapter);
                initData();
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshRepairEvent event) {
        presenter.repairList(1, status);
    }


    private void initData() {
        repairListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RepairListBean repairListBean = repairListBeanList.get(position);
                Intent intent = new Intent(RepairListActivity.this, RepairDetailActivity.class);
                intent.putExtra("repairListBean", repairListBean);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onError_(Throwable e) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick(R.id.lly_status)
    public void onViewClicked() {
        ContextThemeWrapper contextThemeWrapper =
                new ContextThemeWrapper(context, R.style.CustomPopMenuStyle);
        //创建弹出式菜单对象（最低版本11）
        //第二个参数是绑定的那个view
        popupMenu = new PopupMenu(contextThemeWrapper, llyStatus);
        //获取菜单填充器
        MenuInflater inflater = popupMenu.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.list_menu, popupMenu.getMenu());
        //绑定菜单项的点击事件
        popupMenu.setOnMenuItemClickListener(this);
        //显示(这一行代码不要忘记了)
        popupMenu.show();
    }


    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.re_none:
                status=0;
                tvStatus.setText("未处理");
                break;
            case R.id.re_confirm:
                status=1;
                tvStatus.setText("已确认");
                break;
            case R.id.re_doing:
                status=2;
                tvStatus.setText("处理中");
                break;
            case R.id.re_done:
                status=3;
                tvStatus.setText("已完成");
                break;
            default:
                break;
        }
        presenter.repairList(pageNum, status);
        return false;
    }
}
