package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
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
    @BindView(R.id.lly_plate_did)
    RelativeLayout llyPlateDid;
    @BindView(R.id.rel_bid)
    RelativeLayout relBid;
    @BindView(R.id.rel_iccid)
    RelativeLayout relIccid;
    @BindView(R.id.rel_did)
    RelativeLayout relDid;
    @BindView(R.id.rel_sid)
    RelativeLayout relSid;
    @BindView(R.id.rel_zid)
    RelativeLayout relZid;
    @BindView(R.id.rel_plate_last_heart_time)
    RelativeLayout relPlateLastHeartTime;
    @BindView(R.id.rel_cabinet_last_heart_time)
    RelativeLayout relCabinetLastHeartTime;
    @BindView(R.id.rel_plate_current_signal_value)
    RelativeLayout relPlateCurrentSignalValue;
    @BindView(R.id.rel_plate_state)
    RelativeLayout relPlateState;
    @BindView(R.id.rel_cabinet_current_signal_value)
    RelativeLayout relCabinetCurrentSignalValue;
    @BindView(R.id.rel_lock_state)
    RelativeLayout relLockState;
    @BindView(R.id.rel_cabinet_state)
    RelativeLayout relCabinetState;
    @BindView(R.id.rel_order_create_time)
    RelativeLayout relOrderCreateTime;
    @BindView(R.id.rel_order_expire_time)
    RelativeLayout relOrderExpireTime;
    @BindView(R.id.rel_order_money)
    RelativeLayout relOrderMoney;
    @BindView(R.id.rel_order_number)
    RelativeLayout relOrderNumber;
    @BindView(R.id.rel_user)
    RelativeLayout relUser;
    @BindView(R.id.rel_active_time)
    RelativeLayout relActiveTime;
    @BindView(R.id.rel_active_person)
    RelativeLayout relActivePerson;
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
        if (!data.getPadDid().equals("")) {
            tvPlateDid.setText(data.getPadDid());
        } else {
            llyPlateDid.setVisibility(View.GONE);
        }

        if (!data.getPadBid().equals("")) {
            tvBid.setText(data.getPadBid());
        } else {
            relBid.setVisibility(View.GONE);
        }

        if (!data.getIccid().equals("")) {
            tvIccid.setText(data.getIccid());
        } else {
            relIccid.setVisibility(View.GONE);
        }

        if (!data.getCabinetDid().equals("")) {
            tvCabinetDid.setText(data.getCabinetDid());
        } else {
            relDid.setVisibility(View.GONE);
        }

        if (!data.getPowerSid().equals("")) {
            tvSid.setText(data.getPowerSid());
        } else {
            relSid.setVisibility(View.GONE);
        }

        if (!data.getHolderZid().equals("")) {
            tvZid.setText(data.getHolderZid());
        } else {
            relZid.setVisibility(View.GONE);
        }

        if (!data.getPadLastHeart().equals("")) {
            tvPlateLastHeartTime.setText(data.getPadLastHeart());
        } else {
            relPlateLastHeartTime.setVisibility(View.GONE);
        }

        if (!data.getPadLastHeart().equals("")) {
            tvCabinetLastHeartTime.setText(data.getPadLastHeart());
        } else {
            relCabinetLastHeartTime.setVisibility(View.GONE);
        }

        if (!data.getPadSignal().equals("")) {
            tvPlateCurrentSignalValue.setText(data.getPadSignal());
        } else {
            relPlateCurrentSignalValue.setVisibility(View.GONE);
        }

        if (!data.getCabinetSignal().equals("")) {
            tvCabinetCurrentSignalValue.setText(data.getCabinetSignal());
        } else {
            relCabinetCurrentSignalValue.setVisibility(View.GONE);
        }

        if (!data.getLockStatus().equals("")) {
            tvLockState.setText(data.getLockStatus());
        } else {
            relLockState.setVisibility(View.GONE);
        }

        if (!data.getLockStatus().equals("")) {
            tvPlateState.setText(data.getCabinetHeart());
        } else {
            relPlateState.setVisibility(View.GONE);
        }

        if (!data.getLockStatus().equals("")) {
            tvCabinetState.setText(data.getLockStatus());
        } else {
            relCabinetState.setVisibility(View.GONE);
        }

        if (!data.getOrderCreateDate().equals("")) {
            tvOrderCreateTime.setText(data.getOrderCreateDate());
        } else {
            relOrderCreateTime.setVisibility(View.GONE);
        }

        if (!data.getOrderExpireDate().equals("")) {
            tvOrderExpireTime.setText(data.getOrderExpireDate());
        } else {
            relOrderExpireTime.setVisibility(View.GONE);
        }

        if (!data.getOrderId().equals("")) {
            tvOrderNumber.setText(data.getOrderId());
        } else {
            relOrderNumber.setVisibility(View.GONE);
        }

        if (!data.getNickname().equals("")) {
            tvUser.setText(data.getNickname());
        } else {
            relUser.setVisibility(View.GONE);
        }

        if (!data.getOrderMoney().equals("")) {
            tvOrderMoney.setText(data.getOrderMoney());
        } else {
            relOrderMoney.setVisibility(View.GONE);
        }

        if (!data.getActiveDate().equals("")) {
            tvActiveTime.setText(data.getActiveDate());
        } else {
            relActiveTime.setVisibility(View.GONE);
        }

        if (!data.getActiveUser().equals("")) {
            tvActivePerson.setText(data.getActiveUser());
        } else {
            relActivePerson.setVisibility(View.GONE);
        }
    }
}
