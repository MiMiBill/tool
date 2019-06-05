package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.OrderTailBean;
import com.zkys.operationtool.dialog.RefundDialog;
import com.zkys.operationtool.presenter.OrderDetailPresenter;
import com.zkys.operationtool.util.DateUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
//逾期订单详情
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> {
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_pay_num)
    TextView tvPayNum;
    @BindView(R.id.tv_rent_count)
    TextView tvRentCount;
    @BindView(R.id.tv_wechatName)
    TextView tvWechatName;
    @BindView(R.id.btn_refund)
    Button btnRefund;
    private int orderId=0;
    private OrderTailBean orderTailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("逾期订单");

        orderId=getIntent().getIntExtra("orderId",0);
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",orderId);
        presenter.findById(map);
    }


    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public OrderDetailPresenter initPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void setData(HttpResponse result) {
        if(result!=null && result.getCode()==200){
            if(result.getData() instanceof OrderTailBean){
                orderTailBean = (OrderTailBean) result.getData();
                tvHospital.setText(orderTailBean.getHospitalName()+"-"+orderTailBean.getDeptName()+"-"+orderTailBean.getBedNumber());
                tvOrderNum.setText(orderTailBean.getId()+"");
                tvStartTime.setText(DateUtil.timeStamp2Date(orderTailBean.getCreateTime() + "", "yyyy-MM-dd HH:mm:ss"));
                tvEndTime.setText(DateUtil.timeStamp2Date(orderTailBean.getLeaseTime() + "", "yyyy-MM-dd HH:mm:ss"));
                tvPhone.setText(orderTailBean.getMobile()+"");
                tvPayNum.setText(orderTailBean.getRefundFee()+"元");
                tvRentCount.setText(orderTailBean.getPayPrice()+"元");
                tvWechatName.setText(orderTailBean.getNickname()+"");
            }
        }
    }

    @OnClick(R.id.btn_refund)
    public void onViewClicked() {
        RefundDialog dialog=new RefundDialog(OrderDetailActivity.this,false);
        dialog.show();


//        EventBus.getDefault().post(new RefreshEvent());
    }
}
