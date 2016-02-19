package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.OrderAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.RechargeAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.RechargeModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.OrderDetailActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值记录界面
 */
public class RechargeLogActivity extends BaseActivity implements View.OnClickListener, Handler.Callback{

    public Handler mHandler;
    public WindowManager wManager;
    public
    AssetManager am;
    public Resources resources;
    public BaseApplication application;
    @Bind(R.id.rechargeLogList)
    PullToRefreshListView rechargeLogList;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<RechargeModel> recharges;
    public RechargeAdapter adapter;
    public LayoutInflater inflate;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_log);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets();
        inflate = LayoutInflater.from(RechargeLogActivity.this);
        resources = this.getResources();
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        emptyView = inflate.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无充值记录信息");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initTitle();
        initList();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        Drawable rightDraw = resources.getDrawable(R.mipmap.recharge_icon);
        SystemTools.loadBackground(titleRightImage, rightDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("充值记录");
    }

    private void initList()
    {
        rechargeLogList.setMode(PullToRefreshBase.Mode.BOTH);
        rechargeLogList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                loadData();
            }
        });
        recharges = new ArrayList<RechargeModel>();
        adapter = new RechargeAdapter(recharges, RechargeLogActivity.this);
        rechargeLogList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        //rechargeLogList.setEmptyView(emptyView);
        rechargeLogList.onRefreshComplete();
        RechargeModel recharge = new RechargeModel();
        recharge.setMoney(2);
        recharge.setPayChannel(0);
        recharge.setPayStatus(3);
        recharge.setPayTime("1455702902672");
        recharges.add(recharge);
        RechargeModel recharge1 = new RechargeModel();
        recharge1.setMoney(3);
        recharge1.setPayChannel(1);
        recharge1.setPayStatus(1);
        recharge1.setPayTime("1455702902672");
        recharges.add(recharge1);
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RechargeLogActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                rechargeLogList.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(RechargeLogActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}
