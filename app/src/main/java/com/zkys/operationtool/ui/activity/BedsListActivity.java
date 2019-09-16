package com.zkys.operationtool.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.BedStatusListAdapter;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.BedOrderStateBean;
import com.zkys.operationtool.dialog.UpdateBedNumberDialog;
import com.zkys.operationtool.presenter.PlateStatusPresenter;
import com.zkys.operationtool.util.LogFactory;
import com.zkys.operationtool.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 某科室的平板状态列表
 */
public class BedsListActivity extends BaseActivity<PlateStatusPresenter> {

    private static final String TAG = BedsListActivity.class.getSimpleName();
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int hid=-1;
    private int cid=-1;
    private String deptName;
    private List<BedOrderStateBean> orderStateBeans=new ArrayList<>();
    private List<BedOrderStateBean> temporderStateBeans=new ArrayList<>();
    private int pageNum=1;
    private BedStatusListAdapter bedStatusListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hid = getIntent().getIntExtra("hid",-1);
        cid = getIntent().getIntExtra("cid",-1);
        deptName = getIntent().getStringExtra("DeptName");
        getPadOrderStatusData(1);
        bedStatusListAdapter = new BedStatusListAdapter(orderStateBeans);
        initListener();
        setTvTitleText(deptName);
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
                getPadOrderStatusData(1);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                getPadOrderStatusData(pageNum);
            }
        });
    }

    private void getPadOrderStatusData(int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", hid);
        map.put("deptId", cid);
        map.put("pageNum",pageNum);
        map.put("pageSize",10);
        presenter.getPadOrderStatusData(map);
    }

    @Override
    public PlateStatusPresenter initPresenter() {
        return new PlateStatusPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_beds_list;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }


    @Override
    public void setData(HttpResponse result) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (result != null && result.getCode() == 200) {
            if (result.getData() instanceof List) {
                LogFactory.l().i("pageNum==="+pageNum);
                if(pageNum==1){
                    if(temporderStateBeans!=null){
                        temporderStateBeans.clear();
                    }
                    orderStateBeans = (List<BedOrderStateBean>) result.getData();
                    if(orderStateBeans.size()<10){
                        refreshLayout.setEnableLoadMore(false);
                    }else {
                        refreshLayout.setEnableLoadMore(true);
                    }

                    bedStatusListAdapter.setNewData(orderStateBeans);
                    rcvList.setAdapter(bedStatusListAdapter);



                }else {
                    LinearLayoutManager linearLayoutManager  = (LinearLayoutManager)rcvList.getLayoutManager();
                    int  preLastPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    temporderStateBeans=(List<BedOrderStateBean>) result.getData();
                    if(temporderStateBeans.size()<10){
                        refreshLayout.setEnableLoadMore(false);
                    }else {
                        refreshLayout.setEnableLoadMore(true);
                    }
                   // orderStateBeans.addAll(temporderStateBeans);

                    //bedStatusListAdapter.setNewData(orderStateBeans);
                    bedStatusListAdapter.addData(temporderStateBeans);
                    rcvList.setAdapter(bedStatusListAdapter);
                    rcvList.scrollToPosition(preLastPosition );//new add

                }
                initBedStatusListener();

            } else if (result.getCode() == 200) {
                ToastUtil.showShort("修改成功");
                pageNum=1;
                getPadOrderStatusData(pageNum);
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
    }

    @Override
    public void onError_(Throwable e) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    private void initBedStatusListener() {

        bedStatusListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.ll_item_normal:
                    case R.id.ll_item_exception:
                        startActivity(new Intent(BedsListActivity.this, InfoDetailActivity.class)
                                .putExtra("cid", orderStateBeans.get(position).getDeptId())
                                .putExtra("hid", orderStateBeans.get(position).getHospitalId())
                                .putExtra("bedNumber", orderStateBeans.get(position).getBedNumber()));
                        break;
                    case R.id.tv_normal_right_update:
                    case R.id.tv_exception_right_update:

                        final UpdateBedNumberDialog dialog = new UpdateBedNumberDialog();
                        dialog.setTvCurrentNumber(orderStateBeans.get(position).getBedNumber());
                        dialog.show(getSupportFragmentManager(), "UpdateBedNumberDialog");
                        dialog.setOnClickUpdateListener(new UpdateBedNumberDialog.OnClickUpdateListener() {
                            @Override
                            public void onClickUpdate(String number) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", orderStateBeans.get(position).getId());
                                map.put("bedNumber", number);
                                map.put("randomNumber", orderStateBeans.get(position).getRandomNumber());
                                presenter.updateBedNumber(map);
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });


    }

}