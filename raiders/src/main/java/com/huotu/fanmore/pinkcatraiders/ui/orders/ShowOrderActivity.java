package com.huotu.fanmore.pinkcatraiders.ui.orders;

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
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.OrderAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 晒单界面
 */
public class ShowOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;
    public WindowManager wManager;
    public
    AssetManager am;
    public Resources res;
    public BaseApplication application;
    @Bind(R.id.orderList)
    PullToRefreshListView orderList;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<OrderModel> orders;
    public OrderAdapter adapter;
    public LayoutInflater inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets();
        inflate = LayoutInflater.from(ShowOrderActivity.this);
        res = this.getResources();
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        emptyView = inflate.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无晒单信息");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initTitle();
        initList();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = res.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = res.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("晒单分享");
    }

    private void initList()
    {
        orderList.setMode(PullToRefreshBase.Mode.BOTH);
        orderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        orders = new ArrayList<OrderModel>();
        adapter = new OrderAdapter(orders, ShowOrderActivity.this);
        orderList.setAdapter(adapter);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long pid = orders.get(position).getPid();
                Bundle bundle = new Bundle();
                bundle.putLong("pid", pid);
                ActivityUtils.getInstance().showActivity(ShowOrderActivity.this, OrderDetailActivity.class, bundle);
            }
        });
        firstGetData();
    }

    private void loadData()
    {
        orderList.onRefreshComplete();
        OrderModel order1 = new OrderModel();
        order1.setIssue(10098);
        order1.setOrderDetail("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        order1.setOrderTime("1455702902672");
        order1.setOrderTitle("中了中了哈哈");
        order1.setProductDetail("iphone6s 4.7英寸64G");
        order1.setUserHead("http://imgk.zol.com.cn/dcbbs/2342/a2341460.jpg");
        order1.setUsername("小小");
        orders.add(order1);
        OrderModel order2 = new OrderModel();
        order2.setIssue(10098);
        order2.setOrderDetail("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        order2.setOrderTime("1455702902672");
        order2.setOrderTitle("中了中了哈哈");
        order2.setProductDetail("iphone6s 4.7英寸64G");
        order2.setUserHead("http://imgk.zol.com.cn/dcbbs/2342/a2341460.jpg");
        order2.setUsername("小小");
        orders.add(order2);
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ShowOrderActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                orderList.setRefreshing(true);
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
            this.closeSelf(ShowOrderActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
    }
}
