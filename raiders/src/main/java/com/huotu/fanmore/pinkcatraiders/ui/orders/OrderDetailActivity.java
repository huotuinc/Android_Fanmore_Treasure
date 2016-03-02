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
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.OrderDetailOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView ( R.layout.order_detail );
        ButterKnife.bind ( this );
        mHandler = new Handler ( this );
        am = this.getAssets();
        resources = this.getResources ( );
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        bundle = this.getIntent ().getExtras ();
        initTitle();
        initScroll();
    }

    private void initScroll()
    {
        initData();
        orderDetailRefresh.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener<ScrollView>() {

                    @Override
                    public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {

                        initData();
                    }
                }
        );
    }

    private void initData()
    {



        if( false == OrderDetailActivity.this.canConnect ( ) ){
            mHandler.post(new Runnable() {
                              @Override
                              public void run() {
                                  orderDetailRefresh.onRefreshComplete();
                              }
                          });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_SHARE_ORDER_DETAIL;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), OrderDetailActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        maps.put ( "id",  bundle.getLong ( "pid"  ) );
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                orderDetailRefresh.onRefreshComplete();
                if (OrderDetailActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<OrderDetailOutputModel> jsonUtil = new JSONUtil<OrderDetailOutputModel>();
                OrderDetailOutputModel OrderDetailOutputs = new OrderDetailOutputModel();
                OrderDetailOutputs = jsonUtil.toBean(response.toString(), OrderDetailOutputs);
                if (null != OrderDetailOutputs && null != OrderDetailOutputs.getResultData() && (1 == OrderDetailOutputs.getResultCode())) {
                    if (null != OrderDetailOutputs.getResultData().getData()) {
                        orderImgs.removeAllViews();
                        OrderModel order = OrderDetailOutputs.getResultData().getData();
                        orderTitle.setText(order.getShareOrderTitle());
                        shareUserName.setText(order.getNickName());
                        shareTime.setText(DateUtils.transformDataformat6(
                                order.getTime()));
                        productName.setText("获奖商品：" + order.getCharacters());
                        productIusse.setText("商品期号：" + order.getIssueNo());
                        partners.setText("本期参与：" + order.getAttendAmount());
                        luckyNo.setText("幸运号码：" + order.getLuckNumber());
                        if (order.getLotteryTime() != null) {
                            announcedTime.setText("揭晓时间：" + DateUtils.transformDataformat6(order.getLotteryTime()));
                        } else {
                            announcedTime.setText("揭晓时间：");
                        }

                        orderCon.setText(order.getContent());
                        //动态加载图片
                        int size = order.getPictureUrls().size();
                        size = size >= 4 ? 4 : size;
                        for(int i = 0 ; i < size ; i++)
                        {
                            int width = wManager.getDefaultDisplay().getWidth() - BitmapUtils.dip2px(OrderDetailActivity.this, 10);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, 100);
                            ImageView orderImg = (ImageView) LayoutInflater.from(OrderDetailActivity.this).inflate ( R.layout.order_img, null );
                            BitmapLoader.create().displayUrl(OrderDetailActivity.this, orderImg,
                                    order.getPictureUrls().get(i), R.mipmap.error);
                            orderImg.setLayoutParams(lp);
                            orderImgs.addView(orderImg);
                        }

                    } else {
                    }
                } else {
                    //异常处理，自动切换成无数据
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                orderDetailRefresh.onRefreshComplete();
                if (OrderDetailActivity.this.isFinishing()) {
                    return;
                }
            }
        });
    }

    @OnClick (R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( OrderDetailActivity.this );
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
