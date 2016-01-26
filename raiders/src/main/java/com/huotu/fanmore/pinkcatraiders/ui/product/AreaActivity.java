package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 专区界面
 */
public class AreaActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    @Bind(R.id.stubTitleText1)
    ViewStub stubTitleText1;
    @Bind(R.id.areaList)
    PullToRefreshListView areaList;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<ProductModel> products;
    public AreaProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_area);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        emptyView = new View(AreaActivity.this);
        emptyView.setBackgroundResource(R.mipmap.empty);
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
        Drawable rightDraw = resources.getDrawable(R.mipmap.more_gray);
        SystemTools.loadBackground(titleRightImage, rightDraw);
        stubTitleText1.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("奖品详情");
        TextView titleCount = (TextView) this.findViewById(R.id.titleCount);
        titleCount.setText("（24）");
    }

    private void initList()
    {
        areaList.setMode(PullToRefreshBase.Mode.BOTH);
        areaList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        products = new ArrayList<ProductModel>();
        adapter = new AreaProductAdapter(products, AreaActivity.this);
        areaList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        /*ProductModel product1 = new ProductModel();
        product1.setAreaAmount(0);
        product1.setTitle("九阳（Joyoung）DJ13B-D08EC 双磨全钢多功能豆浆机");
        product1.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=1700202158,1687325532&fm=206&gp=0.jpg");
        product1.setLotterySchedule(0.8);
        product1.setSurplus(234);
        product1.setTotal(1000);
        products.add(product1);
        ProductModel product2 = new ProductModel();
        product2.setProductTag(0);
        product2.setProductName("苏泊尔32cm火红点无油烟不粘炒锅PC32M2");
        product2.setProductIcon("http://img0.imgtn.bdimg.com/it/u=3317574596,528516568&fm=21&gp=0.jpg");
        product2.setLotterySchedule(0.4);
        product2.setSurplus(134);
        product2.setTotal(600);
        products.add(product2);*/
        areaList.onRefreshComplete();
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AreaActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                areaList.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(AreaActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
