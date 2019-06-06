package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.OrderTailBean;
import com.zkys.operationtool.bean.RefreshEvent;
import com.zkys.operationtool.dialog.RefundDialog;
import com.zkys.operationtool.presenter.OrderDetailPresenter;
import com.zkys.operationtool.util.CommonUtil;
import com.zkys.operationtool.util.DateUtil;
import com.zkys.operationtool.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

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
                tvPayNum.setText(CommonUtil.convertPriceWithTwoPercent(orderTailBean.getRefundFee()*100)+"元");
                tvRentCount.setText(orderTailBean.getPayPrice()+"元");
                tvWechatName.setText(orderTailBean.getNickname()+"");
            }else {
                ToastUtil.showShort("退款成功");
                EventBus.getDefault().post(new RefreshEvent());
            }
        }else if(result!=null && result.getCode()==-1){
            ToastUtil.showShort(result.getMsg());
        }
    }

    @OnClick(R.id.btn_refund)
    public void onViewClicked() {
        final RefundDialog dialog=new RefundDialog(OrderDetailActivity.this,false,orderTailBean.getRefundFee(),orderTailBean.getPayPrice());
        dialog.setOnClickUpdateListener(new RefundDialog.OnClickRefunListener() {
            @Override
            public void onClickRefun(String number, String remark, int status) {
                presenter.refund(getJsonMap(number,remark,status));
                dialog.dismiss();
            }
        });
        dialog.show();
//        EventBus.getDefault().post(new RefreshEvent());
    }

    private HashMap<String,Object> getJsonMap(String number, String remark, int status){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",orderId);
        map.put("refundFee",number);
        map.put("status",status);
        map.put("description",remark);
        return map;
    }

}
