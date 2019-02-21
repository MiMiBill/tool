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
import com.zkys.operationtool.base.BaseActivityOld;
import com.zkys.operationtool.base.HttpResponseOld;
import com.zkys.operationtool.baseImpl.BasePresenter;
import com.zkys.operationtool.bean.TabEntity;
import com.zkys.operationtool.util.ChartUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看订单页面
 */
public class CheckOrderActivity extends BaseActivityOld {

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
    private int selectType = 1;// 1 开始时间 2结束时间
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"全部", "统计图表"};
    private DatePickerDialog datePickerDialog;
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    DateFormat fmtDate = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("订单查看");
        initView();
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
                flList.setVisibility(position == 0? View.VISIBLE : View.GONE);
                flTable.setVisibility(position == 1? View.VISIBLE : View.GONE);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        List<Integer> list = new ArrayList<>();
        list.add(500);
        list.add(350);
        list.add(400);
        list.add(550);
        list.add(250);
        ChartUtils.initLineChart(lineChart, list, context);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_check_order;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivityOld.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponseOld result) {

    }

    @OnClick({R.id.rl_select_start_time, R.id.rl_select_end_time})
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
        }
    }


    private void showDateDialog() {
        if (null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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
            } else {
                tvEndTime.setText(fmtDate.format(dateAndTime.getTime()));
            }
        }
    };
}
