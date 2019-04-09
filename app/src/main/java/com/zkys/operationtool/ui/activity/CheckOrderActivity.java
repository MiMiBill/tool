package com.zkys.operationtool.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.zkys.operationtool.R;
import com.zkys.operationtool.adapter.OrderListAdapter;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.CoreBean;
import com.zkys.operationtool.bean.HospitalBean;
import com.zkys.operationtool.bean.ItemStatisticBean;
import com.zkys.operationtool.bean.OrderDataBean;
import com.zkys.operationtool.bean.TabEntity;
import com.zkys.operationtool.dialog.BottomDialog;
import com.zkys.operationtool.presenter.OrderListPresenter;
import com.zkys.operationtool.util.ChartUtils;
import com.zkys.operationtool.util.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看订单页面
 */
public class CheckOrderActivity extends BaseActivity<OrderListPresenter> implements BottomDialog.ItemSelectedInterface {

    @BindView(R.id.tl_menu)
    CommonTabLayout tlMenu;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.sv_content)
    NestedScrollView svContent;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.fl_list)
    FrameLayout flList;
    @BindView(R.id.fl_table)
    LinearLayout flTable;
    @BindView(R.id.tv_selected_hospital)
    TextView tvSelectedHospital;
    @BindView(R.id.tv_selected_core)
    TextView tvSelectedCore;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    private int selectType = 1;// 1 开始时间 2结束时间
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"全部", "统计图表"};
    private DatePickerDialog datePickerDialog;
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    DateFormat fmtDate = new SimpleDateFormat("yyyy.MM.dd");
    private BottomDialog bottomDialog;
    private List<HospitalBean> hospitalBeanList;
    private List<CoreBean> coreBeanList;
    private List<String> hospitalNames = new ArrayList<>();
    private List<String> coreNames = new ArrayList<>();
    private long startTime;
    private long endTime;
    private int hid;
    private int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("订单查看");
        initView();
        getOrderListStatistics();
    }

    private void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        tlMenu.setTabData(mTabEntities);
        tlMenu.setCurrentTab(0);
        tlMenu.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                flList.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                flTable.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        initDialog();
    }

    private void initDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomDialog();
            bottomDialog.setItemSelectedInterface(this);
        }
    }

    void initDialogDataAndShow(List<String> names, int type) {

        if (bottomDialog != null) {
            if (type == 1) {
                hospitalNames.clear();
                hospitalNames.addAll(names);
                if (hospitalNames.size() > 0) {
                    if (!bottomDialog.isVisible()) {
                        bottomDialog.setData(hospitalNames, type);
                        bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                    }
                } else {
                    if (bottomDialog.isVisible()) {
                        bottomDialog.dismiss();
                    }
                    ToastUtil.showShort("没有可选的医院");
                }
            } else if (type == 2) {
                coreNames.clear();
                coreNames.addAll(names);
                if (coreNames.size() > 0) {
                    if (!bottomDialog.isVisible()) {
                        bottomDialog.setData(coreNames, type);
                        bottomDialog.show(getSupportFragmentManager(), "bottomDialog");
                    }
                } else {
                    if (bottomDialog.isVisible()) {
                        bottomDialog.dismiss();
                    }
                    ToastUtil.showShort("没有可选的科室");
                }
            }
        }
    }

    @Override
    public OrderListPresenter initPresenter() {
        return new OrderListPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_check_order;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result.getData() != null) {
            if (result.getData() instanceof List) {
                List list = (List) result.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0) instanceof HospitalBean) {
                        hospitalBeanList = (List<HospitalBean>) result.getData();
                        List<String> names = new ArrayList<>();
                        for (HospitalBean hospitalBean : hospitalBeanList) {
                            names.add(hospitalBean.getName());
                        }
                        initDialogDataAndShow(names, 1);// 1代表选择医院的数据
                    } else if (list.get(0) instanceof CoreBean) {
                        coreBeanList = (List<CoreBean>) result.getData();
                        List<String> names = new ArrayList<>();
                        for (CoreBean coreBean : coreBeanList) {
                            names.add(coreBean.getName());
                        }
                        initDialogDataAndShow(names, 2);// 2代表选择科室的数据
                    } else if(list.get(0) instanceof ItemStatisticBean) {
                        List<ItemStatisticBean> statisticBeanList = (List<ItemStatisticBean>) result.getData();
                        ChartUtils.initLineChart(lineChart, statisticBeanList, context);
                    }
                } else {
                    ToastUtil.showShort("暂无数据");
                }
            } else if (result.getData() instanceof OrderDataBean) {
                OrderDataBean orderDataBean = (OrderDataBean) result.getData();
                List<OrderDataBean.ArrayBean> orderDataBeanArray = orderDataBean.getArray();
                tvMoney.setText("" + orderDataBean.getMoneyTotal());
                tvOrderTotal.setText("" + orderDataBean.getOrderCount());
                if (orderDataBeanArray != null && orderDataBeanArray.size() > 0) {
//                    Collections.reverse(orderDataBeanArray);
                } else {
                    lineChart.clear();
                }
                rcvList.setAdapter(new OrderListAdapter(orderDataBeanArray));
            }
        } else if (result.getCode() == 200) {
            ToastUtil.showShort("修改成功");
            finish();
        } else {
            ToastUtil.showShort(result.getMsg());
        }
    }

    @Override
    public void onError_(Throwable e) {

    }

    @OnClick({R.id.rl_select_start_time, R.id.rl_select_end_time, R.id.rl_select_hospital, R.id.rl_select_core})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_select_start_time:
                selectType = 1;
                showDateDialog();
                break;
            case R.id.rl_select_end_time:
                selectType = 2;
                showDateDialog();
                break;
            case R.id.rl_select_hospital:
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                presenter.getHospitalList();
                break;
            case R.id.rl_select_core:
                if (bottomDialog.isVisible()) {
                    bottomDialog.dismiss();
                }
                if (hid > 0) {
                    presenter.getCoreList(hid);
                } else {
                    ToastUtil.showShort("请先选择医院");
                }
                break;

        }
    }


    private void showDateDialog() {
        if (null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        } else {
            datePickerDialog.updateDate(dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH));
        }
        datePickerDialog.show();
    }


    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
//            building_time = dateAndTime.getTime().getTime() / 1000 + "";
            if (selectType == 1) {
                tvStartTime.setText(fmtDate.format(dateAndTime.getTime()));
                startTime = dateAndTime.getTime().getTime() / 1000;
            } else {
                tvEndTime.setText(fmtDate.format(dateAndTime.getTime()));
                endTime = dateAndTime.getTime().getTime() / 1000;
            }

            if (startTime > 0 && endTime > 0) {
                if (endTime - startTime < 0) {
                    ToastUtil.showShort("开始时间不能大于结束时间");
                    return;
                } else {
                    getOrderListStatistics();
                }

            }

        }
    };

    @Override
    public void itemSelected(int position, int type) {

        if (type == 1 && hospitalNames.size() > 0) {
            hid = hospitalBeanList.get(position).getId();
            tvSelectedHospital.setText(hospitalNames.get(position));
            tvSelectedCore.setText("");
            coreNames.clear();
            cid = -1;
        } else if (type == 2 && coreNames.size() > 0) {
            cid = coreBeanList.get(position).getId();
            tvSelectedCore.setText(coreNames.get(position));
        }

        // 调用订单列表与图表统计
        getOrderListStatistics();
    }

    private void getOrderListStatistics() {
        Map<String, Object> map = new HashMap<>();
        map.put("sydicId", MyApplication.getInstance().getUserInfo().getCorrelationId());
        map.put("hospitalId", hid > 0 ? hid : "");
        map.put("deptId", cid > 0 ? cid : "");
        map.put("startTime", startTime > 0? startTime : "");
        map.put("endTime", endTime > 0? endTime + 60*60*24 - 1 : "");
        map.put("pageNumber", 1);
        map.put("pageSize", 1000);
        presenter.getOderData(map);// 订单列表
        presenter.getOrderStatistics(map);
    }
}
