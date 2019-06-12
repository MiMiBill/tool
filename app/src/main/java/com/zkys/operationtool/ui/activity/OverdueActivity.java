package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.OverDueListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.OverDueBean;
import com.zkys.operationtool.bean.RefreshEvent;
import com.zkys.operationtool.presenter.OverduePresenter;
import com.zkys.operationtool.util.LogFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class OverdueActivity extends BaseActivity<OverduePresenter> {

    @BindView(R.id.rel_nodata)
    RelativeLayout relNodata;
    @BindView(R.id.iv_nodata)
    ImageView ivNodata;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int pageNum=1;
    private List<OverDueBean.ArrayBean> ovdueDataBeanArray = new ArrayList<>();
    private List<OverDueBean.ArrayBean> tempovdueDataBeanArray = new ArrayList<>();
    private OverDueBean overDueBean;
    private OverDueListAdapter overDueListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("逾期订单");
        EventBus.getDefault().register(this);
        overDueListAdapter = new OverDueListAdapter(ovdueDataBeanArray);
        getStringObjectMap(pageNum);
        initListener();
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                getStringObjectMap(pageNum);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                getStringObjectMap(pageNum);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event) {
        pageNum=1;
        getStringObjectMap(pageNum);
    }


    private Map<String, Object> getStringObjectMap(int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("status",3);
        map.put("pageNumber",pageNum);
        map.put("pageSize",10);
        presenter.wxOrderList(map);
        return map;
    }

    @Override
    public OverduePresenter initPresenter() {
        return new OverduePresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_overdue;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getData() != null && result.getCode() == 200) {
            relNodata.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            if (result.getData() instanceof OverDueBean){
                overDueBean= (OverDueBean) result.getData();
                if(overDueBean!=null && overDueBean.getArray()!=null){
                    if(pageNum==1){
                        LogFactory.l().i("size==="+overDueBean.getArray().size());
                        ovdueDataBeanArray = overDueBean.getArray();
                        if(ovdueDataBeanArray!=null && ovdueDataBeanArray.size()>0){
                            if(ovdueDataBeanArray.size()<10){
                                refreshLayout.setEnableLoadMore(false);
                            }else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                        }
                    }else {
                        tempovdueDataBeanArray=overDueBean.getArray();
                        LogFactory.l().i("size==="+tempovdueDataBeanArray.size());
                        if(tempovdueDataBeanArray!=null && tempovdueDataBeanArray.size()>0){
                            if(tempovdueDataBeanArray.size()<10){
                                refreshLayout.setEnableLoadMore(false);
                            }else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                            ovdueDataBeanArray.addAll(tempovdueDataBeanArray);
                        }
                    }
                    overDueListAdapter.setNewData(ovdueDataBeanArray);
                    rcvList.setAdapter(overDueListAdapter);
                    initItenClick();
                }else {
                    relNodata.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            }
        } else {
            relNodata.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
    }


    private void initItenClick() {
        overDueListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OverDueBean.ArrayBean arrayBean = ovdueDataBeanArray.get(position);
                int id = arrayBean.getId();
                Intent intent=new Intent(OverdueActivity.this,OrderDetailActivity.class);
                intent.putExtra("orderId",id);
                startActivity(intent);
            }
        });
    }
}
