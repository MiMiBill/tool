package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.DeviceInfoBean;
import com.zkys.operationtool.presenter.PlateStatusPresenter;
import com.zkys.operationtool.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 信息详情
 */
public class InfoDetailActivity extends BaseActivity<PlateStatusPresenter> {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_plate_did)
    TextView tvPlateDid;
    @BindView(R.id.tv_bid)
    TextView tvBid;
    @BindView(R.id.tv_iccid)
    TextView tvIccid;
    @BindView(R.id.tv_cabinet_did)
    TextView tvCabinetDid;
    @BindView(R.id.tv_sid)
    TextView tvSid;
    @BindView(R.id.tv_zid)
    TextView tvZid;
    @BindView(R.id.tv_plate_last_heart_time)
    TextView tvPlateLastHeartTime;
    @BindView(R.id.tv_cabinet_last_heart_time)
    TextView tvCabinetLastHeartTime;
    @BindView(R.id.tv_plate_current_signal_value)
    TextView tvPlateCurrentSignalValue;
    @BindView(R.id.tv_cabinet_current_signal_value)
    TextView tvCabinetCurrentSignalValue;
    @BindView(R.id.tv_lock_state)
    TextView tvLockState;
    @BindView(R.id.tv_plate_state)
    TextView tvPlateState;
    @BindView(R.id.tv_cabinet_state)
    TextView tvCabinetState;
    @BindView(R.id.tv_order_create_time)
    TextView tvOrderCreateTime;
    @BindView(R.id.tv_order_expire_time)
    TextView tvOrderExpireTime;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_active_time)
    TextView tvActiveTime;
    @BindView(R.id.tv_active_person)
    TextView tvActivePerson;
    @BindView(R.id.sv_content)
    NestedScrollView svContent;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_user)
    TextView tvUser;
    private String hid;
    private String cid;
    private String bedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("信息详情");
        hid = getIntent().getStringExtra("hid");
        cid = getIntent().getStringExtra("cid");
        bedNumber = getIntent().getStringExtra("bedNumber");
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", hid);
        map.put("deptId", cid);
        map.put("bedNumber", bedNumber);
        presenter.getDeviceDetailInfo(map);
    }

    @Override
    public PlateStatusPresenter initPresenter() {
        return new PlateStatusPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_info_detail;
    }

    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @Override
    public void setData(HttpResponse result) {
        if (result != null) {
            if (result.getData() != null) {
                DeviceInfoBean data = (DeviceInfoBean) result.getData();
                initData(data);
            } else {
                ToastUtil.showShort(result.getMsg());
            }
        } else {
            ToastUtil.showShort("数据获取失败");
        }
    }

    @Override
    public void onError_(Throwable e) {

    }

    private void initData(DeviceInfoBean data) {
        tvPlateDid.setText(data.getPadDid());
        tvBid.setText(data.getPadBid());
        tvIccid.setText(data.getIccid());
        tvCabinetDid.setText(data.getCabinetDid());
        tvSid.setText(data.getPowerSid());
        tvZid.setText(data.getHolderZid());
        tvPlateLastHeartTime.setText(data.getPadLastHeart());
        tvCabinetLastHeartTime.setText(data.getCabinetHeart());
        tvPlateCurrentSignalValue.setText(data.getPadSignal());
        tvCabinetCurrentSignalValue.setText(data.getCabinetSignal());
        tvLockState.setText(data.getLockStatus());
        tvPlateState.setText(data.getPadStatus());
        tvCabinetState.setText(data.getLockStatus());
        tvOrderCreateTime.setText(data.getOrderCreateDate());
        tvOrderExpireTime.setText(data.getOrderExpireDate());
        tvOrderNumber.setText(data.getOrderId());
        tvUser.setText(data.getNickname());
        tvOrderMoney.setText(data.getOrderMoney());
        tvActiveTime.setText(data.getActiveDate());
        tvActivePerson.setText(data.getActiveUser());
    }


}
