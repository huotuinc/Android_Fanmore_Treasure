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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 晒单详情
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;
    public WindowManager wManager;
    public
    AssetManager am;
    public Resources resources;
    public BaseApplication application;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.orderDetailRefresh)
    PullToRefreshScrollView orderDetailRefresh;
    @Bind(R.id.orderTitle)
    TextView orderTitle;
    @Bind(R.id.shareUserName)
    TextView shareUserName;
    @Bind(R.id.shareTime)
    TextView shareTime;
    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.productIusse)
    TextView productIusse;
    @Bind(R.id.partners)
    TextView partners;
    @Bind(R.id.luckyNo)
    TextView luckyNo;
    @Bind(R.id.announcedTime)
    TextView announcedTime;
    @Bind(R.id.orderCon)
    TextView orderCon;
    @Bind(R.id.orderImgs)
    LinearLayout orderImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets();
        resources = this.getResources();
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        initTitle();
        initScroll();
    }

    private void initScroll()
    {
        initData();
        orderDetailRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                initData();
            }
        });
    }

    private void initData()
    {
        orderTitle.setText("我是晒单标题");
        shareUserName.setText("我是晒单用户");
        shareTime.setText("2016-02-18 12:23:23");
        productName.setText("获奖商品：ddddddddddd");
        productIusse.setText("商品期号：2300908786");
        partners.setText("本期参与：2000人次");
        luckyNo.setText("幸运号码：232142342342");
        announcedTime.setText("揭晓时间：2016-02-18 12:12:23");
        orderCon.setText("dadasdsadsadsadasdsadsadsadsadsadsadsadsa");
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("晒单分享");
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
            this.closeSelf(OrderDetailActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
