package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.WinAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AddressOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.AppDeliveryModel;
import com.huotu.fanmore.pinkcatraiders.model.AppUserBuyFlowModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.DeliveryOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PayFunc;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.JumpToolsPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 中奖详情界面
 */
public class WinLogDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;
    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;
    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    public Bundle bundle;
    private AppUserBuyFlowModel winner;
    @Bind(R.id.windetailPullRefresh)
    PullToRefreshScrollView windetailPullRefresh;
    @Bind(R.id.prizeStatusL)
    LinearLayout prizeStatusL;
    @Bind(R.id.addressL)
    LinearLayout addressL;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.userPhone)
    TextView userPhone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.productL)
    LinearLayout productL;
    @Bind(R.id.pictureUrl)
    ImageView pictureUrl;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.issueId)
    TextView issueId;
    @Bind(R.id.toAmount)
    TextView toAmount;
    @Bind(R.id.lunkyNumber)
    TextView lunkyNumber;
    @Bind(R.id.attendAmount)
    TextView attendAmount;
    @Bind(R.id.awardingDate)
    TextView awardingDate;
    @Bind(R.id.status1Icon)
    ImageView status1Icon;
    @Bind(R.id.status1Tag)
    TextView status1Tag;
    @Bind(R.id.status1Time)
    TextView status1Time;
    @Bind(R.id.status2Icon)
    ImageView status2Icon;
    @Bind(R.id.status2Tag)
    TextView status2Tag;
    @Bind(R.id.status2Time)
    TextView status2Time;
    @Bind(R.id.status3Icon)
    ImageView status3Icon;
    @Bind(R.id.status3Tag)
    TextView status3Tag;
    @Bind(R.id.status3Time)
    TextView status3Time;
    @Bind(R.id.status4Icon)
    ImageView status4Icon;
    @Bind(R.id.status4Tag)
    TextView status4Tag;
    @Bind(R.id.status4Time)
    TextView status4Time;
    @Bind(R.id.status5Icon)
    ImageView status5Icon;
    @Bind(R.id.status5Tag)
    TextView status5Tag;
    @Bind(R.id.status5Time)
    TextView status5Time;
    public
    ProgressPopupWindow progress;
    //引导图片资源
    //地址列表
    public List< MyAddressListModel > lists = new ArrayList<MyAddressListModel>();
    public JumpToolsPopWin jumpTools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_win_log_detail);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources();
        wManager = this.getWindowManager();
        mHandler = new Handler ( this );
        bundle = this.getIntent().getExtras();
        winner = (AppUserBuyFlowModel) bundle.getSerializable("winner");
        jumpTools = new JumpToolsPopWin(WinLogDetailActivity.this, WinLogDetailActivity.this, wManager);
        initTitle();
        initScroll();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("中奖确认");
    }

    private void initScroll()
    {
        initData();
        windetailPullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                initData();
            }
        });
    }

    private void initData()
    {
        //加载奖品状态

        String url    = Contant.REQUEST_URL + Contant.GET_ONE_LOTTERY_INFO;
        AuthParamUtils params = new AuthParamUtils ( application, System.currentTimeMillis
                ( ), WinLogDetailActivity.this );
        Map< String, Object > maps   = new HashMap< String, Object >( );
        maps.put ( "issueId", winner.getIssueId() );
        String                suffix = params.obtainGetParam ( maps );
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils ( );
        httpUtils.doVolleyGet(
                url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        windetailPullRefresh.onRefreshComplete();
                        if (WinLogDetailActivity.this.isFinishing()) {
                            return;
                        }
                        JSONUtil<DeliveryOutputModel> jsonUtil = new JSONUtil<
                                DeliveryOutputModel>();
                        DeliveryOutputModel deliveryOutput = new DeliveryOutputModel
                                ();
                        deliveryOutput = jsonUtil.toBean(response.toString(), deliveryOutput);
                        if (null != deliveryOutput && null != deliveryOutput.getResultData()
                                && (1 == deliveryOutput.getResultCode())) {
                            final AppDeliveryModel deliveryModel = deliveryOutput.getResultData().getData();
                            if (0 == deliveryModel.getDeliveryStatus()) {
                                //获得奖品
                                SystemTools.loadBackground(status1Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status1Tag.setText("获得奖品");
                                status1Time.setText(DateUtils.transformDataformat11(deliveryModel.getAwardingDate()));
                                SystemTools.loadBackground(status2Icon, resources.getDrawable(R.mipmap.prize_selected));
                                status2Tag.setText("确认收货地址");
                                status2Time.setText("新增地址");
                                status2Time.setTextColor(resources.getColor(R.color.color_white));
                                status2Time.setPadding(5, 5, 5, 5);
                                status2Time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //新增地址接口
                                        Message message = mHandler.obtainMessage();
                                        message.what = Contant.WINNER_STATUS;
                                        message.obj = deliveryModel.getPid();
                                        message.arg1 = 0;
                                        mHandler.sendMessage(message);
                                    }
                                });
                                SystemTools.loadBackground(status2Time, resources.getDrawable(R.drawable.button_common_1));
                                SystemTools.loadBackground(status3Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status3Tag.setText("等待奖品派发");
                                status3Time.setText("");
                                SystemTools.loadBackground(status4Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status4Tag.setText("确认收货");
                                status4Time.setText("");
                                SystemTools.loadBackground(status5Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status5Tag.setText("已签收");
                                status5Time.setText("");
                                addressL.setVisibility(View.GONE);
                            } else if (1 == deliveryModel.getDeliveryStatus()) {
                                //确认收货地址
                                SystemTools.loadBackground(status1Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status1Tag.setText("获得奖品");
                                status1Time.setText(DateUtils.transformDataformat11(deliveryModel.getAwardingDate()));
                                SystemTools.loadBackground(status2Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status2Tag.setText("确认收货地址");
                                status2Time.setText(DateUtils.transformDataformat11(deliveryModel.getConfirmAddressTime()));
                                SystemTools.loadBackground(status3Icon, resources.getDrawable(R.mipmap.prize_selected));
                                status3Tag.setText("等待奖品派发");
                                status3Time.setText("请稍候...");
                                status3Time.setTextColor(resources.getColor(R.color.title_bg));
                                SystemTools.loadBackground(status4Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status4Tag.setText("确认收货");
                                status4Time.setText("");
                                SystemTools.loadBackground(status5Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status5Tag.setText("已签收");
                                status5Time.setText("");
                                addressL.setVisibility(View.VISIBLE);
                                //加载地址信息
                                userName.setText(deliveryModel.getReceiver());
                                userPhone.setText(deliveryModel.getMobile());
                                address.setText(deliveryModel.getDetails());
                            } else if (2 == deliveryModel.getDeliveryStatus()) {
                                //等待收货
                                SystemTools.loadBackground(status1Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status1Tag.setText("获得奖品");
                                status1Time.setText(DateUtils.transformDataformat11(deliveryModel.getAwardingDate()));
                                SystemTools.loadBackground(status2Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status2Tag.setText("确认收货地址");
                                status2Time.setText(DateUtils.transformDataformat11(deliveryModel.getConfirmAddressTime()));
                                SystemTools.loadBackground(status3Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status3Tag.setText("等待奖品派发");
                                status3Time.setText(DateUtils.transformDataformat11(deliveryModel.getDeliveryTime()));
                                SystemTools.loadBackground(status4Icon, resources.getDrawable(R.mipmap.prize_selected));
                                status4Tag.setText("确认收货");
                                status4Time.setText("确认");
                                status4Time.setTextColor(resources.getColor(R.color.color_white));
                                SystemTools.loadBackground(status4Time, resources.getDrawable(R.drawable.button_common_1));
                                status4Time.setPadding(5, 5, 5, 5);
                                status4Time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //确认收货接口
                                        Message message = mHandler.obtainMessage();
                                        message.what = Contant.WINNER_STATUS;
                                        message.obj = deliveryModel.getPid();
                                        message.arg1 = 1;
                                        mHandler.sendMessage(message);
                                    }
                                });
                                SystemTools.loadBackground(status5Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status5Tag.setText("已签收");
                                status5Time.setText("");
                                addressL.setVisibility(View.VISIBLE);
                                //加载地址信息
                                userName.setText(deliveryModel.getReceiver());
                                userPhone.setText(deliveryModel.getMobile());
                                address.setText(deliveryModel.getDetails());
                            } else if (5 == deliveryModel.getDeliveryStatus()) {
                                //确认收货
                                SystemTools.loadBackground(status1Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status1Tag.setText("获得奖品");
                                status1Time.setText(DateUtils.transformDataformat11(deliveryModel.getAwardingDate()));
                                SystemTools.loadBackground(status2Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status2Tag.setText("确认收货地址");
                                status2Time.setText(DateUtils.transformDataformat11(deliveryModel.getConfirmAddressTime()));
                                SystemTools.loadBackground(status3Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status3Tag.setText("等待奖品派发");
                                status3Time.setText(DateUtils.transformDataformat11(deliveryModel.getDeliveryTime()));
                                SystemTools.loadBackground(status4Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status4Tag.setText("确认收货");
                                status4Time.setText(DateUtils.transformDataformat11(deliveryModel.getRecieveGoodsTime()));
                                SystemTools.loadBackground(status5Icon, resources.getDrawable(R.mipmap.prize_selected));
                                status5Tag.setText("已签收");
                                status5Time.setText("确认并晒单");
                                status5Time.setTextColor(resources.getColor(R.color.color_white));
                                status5Time.setPadding(5, 5, 5, 5);
                                SystemTools.loadBackground(status5Time, resources.getDrawable(R.drawable.button_common_1));
                                status5Time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //确认收货接口
                                        Message message = mHandler.obtainMessage();
                                        message.what = Contant.WINNER_STATUS;
                                        message.arg1 = 2;
                                        mHandler.sendMessage(message);
                                    }
                                });

                                addressL.setVisibility(View.VISIBLE);
                                //加载地址信息
                                userName.setText(deliveryModel.getReceiver());
                                userPhone.setText(deliveryModel.getMobile());
                                address.setText(deliveryModel.getDetails());
                            } else if (6 == deliveryModel.getDeliveryStatus()) {
                                //流程完成
                                SystemTools.loadBackground(status1Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status1Tag.setText("获得奖品");
                                status1Time.setText(DateUtils.transformDataformat11(deliveryModel.getAwardingDate()));
                                SystemTools.loadBackground(status2Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status2Tag.setText("确认收货地址");
                                status2Time.setText(DateUtils.transformDataformat11(deliveryModel.getConfirmAddressTime()));
                                SystemTools.loadBackground(status3Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status3Tag.setText("等待奖品派发");
                                status3Time.setText(DateUtils.transformDataformat11(deliveryModel.getDeliveryTime()));
                                SystemTools.loadBackground(status4Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status4Tag.setText("确认收货");
                                status4Time.setText(DateUtils.transformDataformat11(deliveryModel.getRecieveGoodsTime()));
                                SystemTools.loadBackground(status5Icon, resources.getDrawable(R.mipmap.prize_unselect));
                                status5Tag.setText("已签收");
                                status5Tag.setText("再次晒单");
                                status5Tag.setTextColor(resources.getColor(R.color.color_white));
                                status5Tag.setPadding(5, 5, 5, 5);
                                status5Tag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //确认收货接口
                                        Message message = mHandler.obtainMessage();
                                        message.what = Contant.WINNER_STATUS;
                                        message.arg1 = 2;
                                        mHandler.sendMessage(message);
                                    }
                                });
                                addressL.setVisibility(View.VISIBLE);
                                //加载地址信息
                                userName.setText(deliveryModel.getReceiver());
                                userPhone.setText(deliveryModel.getMobile());
                                address.setText(deliveryModel.getDetails());
                            }
                            //加载奖品信息
                            BitmapLoader.create().displayUrl(WinLogDetailActivity.this, pictureUrl, winner.getDefaultPictureUrl(), R.mipmap.error);
                            title.setText(winner.getTitle());
                            issueId.setText("参与期号：" + winner.getIssueId());
                            toAmount.setText("总需：" + winner.getToAmount());
                            lunkyNumber.setText("" + winner.getLuckyNumber());
                            attendAmount.setText("本期参与：" + winner.getAmount());
                            awardingDate.setText("揭晓时间:" + DateUtils.transformDataformat1(winner.getAwardingDate()));
                        } else {
                            //异常处理，自动切换成无数据
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        windetailPullRefresh.onRefreshComplete();
                        if (WinLogDetailActivity.this.isFinishing()) {
                            return;
                        }
                    }
                }
        );

    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what)
        {
            case Contant.WINNER_STATUS:
            {
                int tag = msg.arg1;
                if(0==tag)
                {
                    final long deliveryId = (long) msg.obj;
                    //选择地址
                    //获取地址列表
                    String url = Contant.REQUEST_URL + Contant.GET_MY_ADDRESS_LIST;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), WinLogDetailActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject >() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONUtil<AddressOutputModel> jsonUtil = new JSONUtil<AddressOutputModel>();
                            AddressOutputModel addressOutput = new AddressOutputModel();
                            addressOutput = jsonUtil.toBean(response.toString(), addressOutput);
                            if(null != addressOutput && null != addressOutput.getResultData() && (1==addressOutput.getResultCode())&&null!=addressOutput.getResultData().getList())
                            {
                                List<MyAddressListModel> list = addressOutput.getResultData().getList();
                                if(null!=list&&!list.isEmpty())
                                {
                                    lists.clear();
                                    lists.addAll(addressOutput.getResultData().getList());
                                    //默认选第一个地址

                                    MyAddressListModel model = lists.get(0);
                                    String url = Contant.REQUEST_URL + Contant.ADD_LOTTERY_RECEIVER_INFO;
                                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), WinLogDetailActivity.this);
                                    //1 拼装参数
                                    Map<String, Object> maps = new HashMap<String, Object> ();
                                    maps.put("deliveryId", deliveryId);
                                    maps.put("receiver", model.getReceiver());
                                    maps.put("mobile", model.getMobile());
                                    maps.put("details", model.getDetails());
                                    maps = params.obtainAllParamUTF8 ( maps );
                                    //获取sign
                                    String signStr = params.obtainSignUTF8 ( maps );
                                    maps.put("receiver", URLEncoder.encode(model.getReceiver()));
                                    maps.put("details", URLEncoder.encode(model.getDetails()));
                                    maps.put ( "sign", signStr);
                                    //拼装URL
                                    String suffix = params.obtainGetParamUTF8 (maps);
                                    url = url + suffix;
                                    HttpUtils httpUtils = new HttpUtils();
                                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                                            BaseModel base = new BaseModel();
                                            base = jsonUtil.toBean(response.toString(), base);
                                            if(1==base.getResultCode())
                                            {
                                                //刷新列表
                                                firstGetData();
                                            }
                                            else
                                            {
                                                ToastUtils.showMomentToast(WinLogDetailActivity.this, WinLogDetailActivity.this, "添加地址失败");
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            ToastUtils.showMomentToast(WinLogDetailActivity.this, WinLogDetailActivity.this, "添加地址失败");
                                        }
                                    });
                                }
                                else
                                {
                                    //异常处理，自动切换成无数据
                                    //提示未设置地址
                                    jumpTools.showWin("你未设置收货地址", AddressListActivity.class);
                                    jumpTools.showAtLocation(
                                            findViewById(R.id.titleLayout),
                                            Gravity.CENTER, 0, 0
                                    );
                                }

                            }
                            else
                            {
                                //异常处理，自动切换成无数据
                                //提示未设置地址
                                jumpTools.showWin("你未设置收货地址", AddressListActivity.class);
                                jumpTools.showAtLocation(
                                        findViewById(R.id.titleLayout),
                                        Gravity.CENTER, 0, 0
                                );

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //提示未设置地址
                            jumpTools.showWin("你未设置收货地址", AddressListActivity.class);
                            jumpTools.showAtLocation(
                                    findViewById(R.id.titleLayout),
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    });
                }
                if(1==tag)
                {
                    //确认收货
                    final long deliveryId = (long) msg.obj;
                    String url = Contant.REQUEST_URL + Contant.CONFIRM_RECEIPT;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), WinLogDetailActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    maps.put ( "deliveryId", String.valueOf ( deliveryId ) );
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                            BaseModel base = new BaseModel();
                            base = jsonUtil.toBean(response.toString(), base);
                            if(1==base.getResultCode())
                            {
                                //刷新列表
                                firstGetData();
                            }
                            else
                            {
                                ToastUtils.showMomentToast(WinLogDetailActivity.this, WinLogDetailActivity.this, "确认收货失败");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ToastUtils.showMomentToast(WinLogDetailActivity.this, WinLogDetailActivity.this, "确认收货失败");
                        }
                    });
                }
                if(2==tag)
                {
                    //确认并晒单
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("winner", winner);
                    ActivityUtils.getInstance().showActivity(WinLogDetailActivity.this, ShareOrderActivity.class, bundle);
                }
            }
            break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(WinLogDetailActivity.this);
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
            this.closeSelf(WinLogDetailActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected
    void firstGetData ( ) {

        mHandler.postDelayed (
                new Runnable ( ) {

                    @Override
                    public
                    void run ( ) {

                        if ( WinLogDetailActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        windetailPullRefresh.setRefreshing ( true );
                    }
                }, 1000
        );
    }
}
