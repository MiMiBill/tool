package com.zkys.operationtool.ui.activity;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zkys.operationtool.R;
import com.zkys.operationtool.application.MyApplication;
import com.zkys.operationtool.base.BaseActivity;
import com.zkys.operationtool.base.HttpResponse;
import com.zkys.operationtool.bean.RepairListBean;
import com.zkys.operationtool.bean.UserInfoBean;
import com.zkys.operationtool.event.RefreshRepairEvent;
import com.zkys.operationtool.presenter.RepairDetailPresenter;
import com.zkys.operationtool.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RepairDetailActivity extends BaseActivity<RepairDetailPresenter> implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.tv_bednums)
    TextView tvBednums;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_sta)
    TextView tvSta;
    @BindView(R.id.edt_ques)
    EditText edtQues;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.lly_sta)
    LinearLayout llySta;
    private RepairListBean repairListBean;
    private int status=0;
    private String localResult="";
    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTvTitleText("报修详情");
        Bundle extras = getIntent().getExtras();
        repairListBean = (RepairListBean) extras.getSerializable("repairListBean");

        tvBednums.setText(repairListBean.getBedNumber() + "床");
        tvReason.setText(repairListBean.getType());
        localResult=repairListBean.getResult();
        edtQues.setText(localResult);
        status=repairListBean.getStatus();
        switch (repairListBean.getStatus()) {
            case 0:
                tvStatus.setText("未处理");
                tvSta.setText("未处理");
                break;
            case 1:
                tvStatus.setText("已确认");
                tvSta.setText("已确认");
                break;
            case 2:
                tvStatus.setText("处理中");
                tvSta.setText("处理中");
                break;
            case 3:
                tvStatus.setText("已完成");
                tvSta.setText("已完成");
                edtQues.setEnabled(false);
                tvCommit.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public RepairDetailPresenter initPresenter() {
        return new RepairDetailPresenter(this);
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_repair_detail;
    }

    @Override
    public void setData(HttpResponse result) {
        if(result.getCode()==200){
            EventBus.getDefault().post(new RefreshRepairEvent());
            finish();
        }
    }


    @Override
    protected int getTitleViewType() {
        return BaseActivity.DEFAULT_TITLE_VIEW;
    }

    @OnClick({R.id.tv_commit,R.id.lly_sta})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                commit();
                break;
            case R.id.lly_sta:
                if(isSoftInput()){
                    hideSoftInput();
                }
                if (repairListBean.getStatus() != 3) {
                    if (popupMenu != null) {
                        popupMenu.dismiss();
                        popupMenu = null;
                    } else {
                        showPop();
                    }
                }
                break;
        }
    }


    //提交
    private void commit() {
        String result=edtQues.getText().toString().trim();
        if(status==repairListBean.getStatus() && result.equals(localResult)){
            ToastUtil.showShort("请修改内容再提交");
            return;
        }
        UserInfoBean userInfo = MyApplication.getInstance().getUserInfo();
        presenter.repairUpdate(userInfo.getId(),status,repairListBean.getRepairBedId(),result);
    }


    //处理状态
    private void showPop() {
        ContextThemeWrapper contextThemeWrapper =
                new ContextThemeWrapper(context, R.style.CustomPopMenuStyle);
        //创建弹出式菜单对象（最低版本11）
        //第二个参数是绑定的那个view
        popupMenu = new PopupMenu(contextThemeWrapper, llySta);
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
                tvSta.setText("未处理");
                break;
            case R.id.re_confirm:
                status=1;
                tvSta.setText("已确认");
                break;
            case R.id.re_doing:
                status=2;
                tvSta.setText("处理中");
                break;
            case R.id.re_done:
                status=3;
                tvSta.setText("已完成");
                break;
            default:
                break;
        }
        return false;
    }
}
